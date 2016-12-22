package com.nekretnine.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.LoginDTO;
import com.nekretnine.dto.UserDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Customer;
import com.nekretnine.models.User;
import com.nekretnine.models.UserAuthority;
import com.nekretnine.repository.AuthorityRepository;
import com.nekretnine.repository.UserAuthorityRepository;
import com.nekretnine.security.TokenUtils;
import com.nekretnine.services.MyMailSenderService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private MyMailSenderService mailSender;

	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private UserAuthorityRepository userAuthorityRepository;

	/**
	 * 
	 * @param userType Type of User being registered. Can be 'Customer' or 'Advertiser'
	 * @param userDTO Data for User being registered written in json, example:
	 * 			@{"firstName" : "", "lastName" : "", "email" : "", "username" : "", "password" : ""}
	 * @return If userType is invalid or User already exists, appropriate message is displayed and 
	 * 			HttpStatus is being sent, otherwise email with verification link is sent to the User.
	 * @author Stefan Plazic 
	 */
	@RequestMapping(value = "/register/{userType}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveCustomer(@PathVariable String userType,
			@RequestBody UserDTO userDTO) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		User user;
		UserAuthority authority = new UserAuthority();
		if (userType.equalsIgnoreCase("customer")) {
			user = new Customer();
			authority
					.setAuthority(authorityRepository.findByName(("CUSTOMER")));
			authority.setUser(user);
		}

		else if (userType.equalsIgnoreCase("advertiser")) {
			user = new Advertiser();
			authority.setAuthority(authorityRepository
					.findByName(("ADVERTISER")));
			authority.setUser(user);
		} else {
			return new ResponseEntity<>(
					"Cant create that type of user, ony Customer and Advertiser allowed",
					HttpStatus.BAD_REQUEST);
		}

		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUsername());
		user.setPassword(encoder.encode(userDTO.getPassword()));
		user.setVerifyCode(UUID.randomUUID().toString());

		// check if user with the email exist
		if (service.findByEmail(user.getEmail()) != null
				|| service.findByUsername(user.getUsername()) != null) {
			return new ResponseEntity<>(
					"User with that username, or email already exists",
					HttpStatus.BAD_REQUEST);
		}

		user = service.save(user);
		userAuthorityRepository.save(authority);
		mailSender
				.sendMail(
						user.getEmail(),
						"Registration",
						"Click her to finish registration: <a href='http://localhost:8080/api/users/verify/"
								+ user.getVerifyCode() + "'>Click</a>");
		return new ResponseEntity<>("Customer has been created Go to "
				+ user.getEmail() + " to verify your account",
				HttpStatus.CREATED);
	}

	/* USER LOGIN */
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
		try {
			// Perform the authentication
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					loginDTO.getUsername(), loginDTO.getPassword());
			Authentication authentication = authenticationManager
					.authenticate(token);
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);

			// Reload user details so we can generate token
			UserDetails details = userDetailsService
					.loadUserByUsername(loginDTO.getUsername());
			return new ResponseEntity<String>(
					tokenUtils.generateToken(details), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Invalid login",
					HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * @RequestMapping(value = "/findUser", method = RequestMethod.POST) public
	 * ResponseEntity<UserDTO> findOneByUsernameAndPassword(@RequestBody
	 * LoginDTO loginDTO) { UserDTO user = new
	 * UserDTO(service.findOneByUsernameAndPassword(loginDTO.getUsername(),
	 * loginDTO.getPassword())); return new ResponseEntity<>(user,
	 * HttpStatus.OK); }
	 */

	/**
	 * @author Stefan Plazic
	 * @param verifyCode is verification code sent to user email
	 * @return if user doesn't exist returns NOT_FOUND status, else if user is already verified return message about that
	 */
	@RequestMapping(value = "/verify/{verifyCode}", method = RequestMethod.GET)
	public ResponseEntity<String> verify(@PathVariable String verifyCode) {

		User user = service.findByVerifyCode(verifyCode);
		if (user != null) {
			if(user.isVerified())
				return new ResponseEntity<>("User already verified", HttpStatus.OK);
			user.setVerified(true);
			service.save(user);
			
			return new ResponseEntity<>("Succesfully verified user", HttpStatus.OK);
		}
			
		return new ResponseEntity<>("User doesn't exists", HttpStatus.NOT_FOUND);
		
	}

}
