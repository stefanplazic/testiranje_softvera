package com.nekretnine.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nekretnine.dto.LoginDTO;
import com.nekretnine.dto.UserDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Customer;
import com.nekretnine.models.User;
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
	
	/*REGISTER CUSTOMER*/
	@RequestMapping(value="/customer/register",method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<String> saveCustomer(@RequestBody UserDTO userDTO){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		User user = new Customer();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUsername());
		user.setPassword(encoder.encode(userDTO.getPassword()));
		
		//check if user with the email exist
		if(service.findByEmail(user.getEmail())!=null || service.findByUsername(user.getUsername())!=null)
		{
			return new ResponseEntity<>("User with that username, or email already exists", HttpStatus.BAD_REQUEST);
		}
		
		user = service.save(user);
		mailSender.sendMail(user.getEmail(), "Registration", "Click her to finish registration: <a href='http://localhost:8080/api/users/verify/"+user.getId()+"'>Click</a>");
		return new ResponseEntity<>("Customer has been created Go to "+user.getEmail()+" to verify your account", HttpStatus.CREATED);	
	}
	
	/*REGISTER Advertiser*/
	@RequestMapping(value="/advertiser/register",method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<String> saveAdvertiser(@RequestBody UserDTO userDTO){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		User user = new Advertiser();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUsername());
		user.setPassword(encoder.encode(userDTO.getPassword()));
		
		//check if user with the email exist
		if(service.findByEmail(user.getEmail())!=null || service.findByUsername(user.getUsername())!=null)
		{
			return new ResponseEntity<>("User with that username, or email already exists", HttpStatus.BAD_REQUEST);
		}
		
		user = service.save(user);
		mailSender.sendMail(user.getEmail(), "Registration", "Click her to finish registration: <a href='http://localhost:8080/api/users/verify/"+user.getId()+"'>Click</a>");
		return new ResponseEntity<>("Advertiser has been created. Go to "+user.getEmail()+" to verify your account", HttpStatus.CREATED);	
	}
	
	/*USER LOGIN*/
	@RequestMapping(value = "/login", method = RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try {
        	// Perform the authentication
        	UsernamePasswordAuthenticationToken token = 
        			new UsernamePasswordAuthenticationToken(
					loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload user details so we can generate token
            UserDetails details = userDetailsService.
            		loadUserByUsername(loginDTO.getUsername());
            return new ResponseEntity<String>(
            		tokenUtils.generateToken(details), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>("Invalid login", HttpStatus.BAD_REQUEST);
        }
	}
	
	/*verify email*/
	@RequestMapping(value="/verify/{id}",method=RequestMethod.GET)
	public ResponseEntity<String> verify(@PathVariable Long id){
		
		User user = service.findOne(id);
		if(user!=null){
			user.setVerified(true);
			service.save(user);
		}
		
		return new ResponseEntity<>("Succesfully verified user" ,HttpStatus.OK);
	}
		
}
