package com.nekretnine.controllers;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.CompanyDTO;
import com.nekretnine.dto.UserDTO;
import com.nekretnine.models.Administrator;
import com.nekretnine.models.Moderator;
import com.nekretnine.models.Notification;
import com.nekretnine.models.User;
import com.nekretnine.models.UserAuthority;
import com.nekretnine.repository.AuthorityRepository;
import com.nekretnine.repository.UserAuthorityRepository;
import com.nekretnine.services.CompanyService;
import com.nekretnine.services.MyMailSenderService;
import com.nekretnine.services.NotificationService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value="api/administrator")
public class AdministratorController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private UserAuthorityRepository userAuthorityRepository;
	
	@Autowired
	private MyMailSenderService mailSender;
	
	/**
	 * 
	 * @author Nemanja Zunic
	 * @param userType Type of User being registered. Can be 'Administrator' or 'Moderator'
	 * @param userDTO Data for User being registered written in json, example:
	 * 			{@{"firstName" : "", "lastName" : "", "email" : "", "username" : "", "password" : ""}}
	 * @return If userType is invalid or User already exists, appropriate message is displayed and 
	 * 			HttpStatus is being sent, otherwise email with verification link is sent to the User. 
	 */
	@RequestMapping(value = "/register/{userType}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveAdmin(@PathVariable String userType, @RequestBody UserDTO userDTO) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		User user;
		UserAuthority userAuth = new UserAuthority();
		
		if(userType.equalsIgnoreCase("administrator")) {
			user = new Administrator();
			userAuth.setAuthority(authorityRepository.findByName("ADMINISTRATOR"));
			userAuth.setUser(user);
		}
		else if(userType.equalsIgnoreCase("moderator")) {
			user = new Moderator();
			userAuth.setAuthority(authorityRepository.findByName("MODERATOR"));
			userAuth.setUser(user);
		}
		else {
			return new ResponseEntity<>(
					String.format("Can't create %s type of user, ony Administrator and Moderator allowed.", userType),
					HttpStatus.BAD_REQUEST);
		}
		
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUsername());
		user.setPassword(encoder.encode(userDTO.getPassword()));
		user.setVerifyCode(UUID.randomUUID().toString());
		
		if(userService.findByEmail(user.getEmail())!=null || userService.findByUsername(user.getUsername())!=null)
		{
			return new ResponseEntity<>(String.format("%s with that username, or email already exists.", StringUtils.capitalize(userType)),
					HttpStatus.BAD_REQUEST);
		}
		user = userService.save(user);
		userAuthorityRepository.save(userAuth);
		mailSender.sendMail(user.getEmail(), "Registration", "Click her to finish registration: <a href='http://localhost:8080/api/users/verify/"+user.getVerifyCode()+"'>Click</a>");
		return new ResponseEntity<>(
				String.format("%s has been created. Go to: %s to verify your account.", 
						StringUtils.capitalize(userType), user.getEmail()),
				HttpStatus.CREATED);	
	}
	
	/**
	 * mile
	 * @param adminId
	 * @param companyDTO
	 * @return
	 */
	@RequestMapping(value = "/acceptCompany/{adminId}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> acceptCompany(@PathVariable Long adminId, @RequestBody CompanyDTO companyDTO){
		//modify on_hold to false and save
		User admin = userService.findOne(adminId);
		if(admin == null){
			return new ResponseEntity<>("Admin not found. ", HttpStatus.NOT_FOUND);
		}
		companyDTO.setOn_hold(false);
		companyService.modifyCompany(companyDTO);
		
		//send succes notification to owner
		Notification notification = new Notification();
		notification.setnType("info");
		notification.setToUser(companyDTO.getOwner());
		notification.setMade(new Date());
		notification.setStatus("NEW");
		notification.setSeen(false);
		notification.setText("Administrator confirm registration of "
				+ "company "+companyDTO.getName());
		notification.setFromUser(admin);
		notificationService.saveNotification(notification);
		return new ResponseEntity<>("Succes accept company", HttpStatus.OK);
		
		
	}
	
}
