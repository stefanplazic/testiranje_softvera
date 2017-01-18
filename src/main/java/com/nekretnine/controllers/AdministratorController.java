package com.nekretnine.controllers;

import java.security.Principal;
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

import com.nekretnine.dto.UserDTO;
import com.nekretnine.models.Administrator;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Company;
import com.nekretnine.models.Moderator;
import com.nekretnine.models.Notification;
import com.nekretnine.models.User;
import com.nekretnine.models.UserAuthority;
import com.nekretnine.repository.AuthorityRepository;
import com.nekretnine.repository.UserAuthorityRepository;
import com.nekretnine.services.AdvertiserService;
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
	private AdvertiserService advertiserService;
	
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
	 * @param 	userType Type of User being registered. Can be 'Administrator' or 'Moderator'
	 * @param	userDTO Data for User being registered written in json, example:
	 * 			@{"firstName" : "", "lastName" : "", "email" : "", "username" : "", "password" : ""}
	 * @return 	userType is invalid or User already exists, appropriate message is displayed and 
	 * 			HttpStatus is being sent, otherwise email with verification link is sent to the User.
	 * @author	Nemanja Zunic, Stefan Plazic
	 */
	@RequestMapping(value = "/register/{userType}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveAdmin(@PathVariable String userType, @RequestBody UserDTO userDTO) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		User user;
		UserAuthority userAuth = new UserAuthority();
		
		if("administrator".equalsIgnoreCase(userType)) {
			user = new Administrator();
			userAuth.setAuthority(authorityRepository.findByName("ADMINISTRATOR"));
			userAuth.setUser(user);
		}
		else if("moderator".equalsIgnoreCase(userType)) {
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
					HttpStatus.CONFLICT);
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
	 * Method allows administrators to approve registration of company and send
	 * success message to owner of company.
	 * 
	 * @param 	principal  Current logged user (administrator), created by Spring Security.
	 * @param  	companyId  Id(Long) of company.
	 * @return	If company doesn't exists or company already was registered, 
	 * 			return error message and appropriate HttpStatus code.
	 * 			Otherwise, return string "Company is accepted." and HttpStatus OK.
	 * @author 	Miodrag Vilotijević
	 */
	@RequestMapping(value = "/acceptCompany/{companyId}", method = RequestMethod.POST)
	public ResponseEntity<String> acceptCompany(Principal principal, @PathVariable Long companyId){
		
		Administrator admin = (Administrator) userService.findByUsername(principal.getName());
		Company company = companyService.findOne(companyId);
		if(company == null){
			return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
		}
		if(!company.isonHold()){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		// modify onHold to false and save in database
		companyService.setOnHold(false, companyId);
		
		Advertiser owner = (Advertiser) userService.findByEmail(company.getOwner().getEmail());
		
		// send success notification to owner
		Notification notification = new Notification();
		notification.setnType("info");
		notification.setToUser(owner);
		notification.setMade(new Date());
		notification.setStatus("NEW");
		notification.setSeen(false);
		notification.setText("Administrator confirm registration of "
				+ "company "+company.getName());
		notification.setFromUser(admin);
		notificationService.saveNotification(notification);
		return new ResponseEntity<>("Company is accepted.", HttpStatus.OK);
		
		
	}
	
	/**
	 * Method allows administrators to decline registration of company and send
	 * message about that to owner of company.
	 * 
	 * @param  principal Current logged user (administrator), created by Spring Security.
	 * @param  companyId Id(Long) of company.
	 * @return 			 If company doesn't exists or company already was registered, 
	 * 					 return error message and appropriate HttpStatus code.
	 * 					 Otherwise, return string "Company is not accepted." and HttpStatus OK.
	 * @author Miodrag Vilotijević
	 */
	@RequestMapping(value = "/declineCompany/{companyId}", method = RequestMethod.POST)
	public ResponseEntity<String> declineCompany(Principal principal, @PathVariable Long companyId){
		
		Administrator admin = (Administrator) userService.findByUsername(principal.getName());
		Company company = companyService.findOne(companyId);
		if(company == null){
			return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
		}
		if(!company.isonHold()){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		// set advertiser's company to null, and delete company (request)
		Advertiser owner = (Advertiser) userService.findByEmail(company.getOwner().getEmail());
		advertiserService.setAdvertisersCompany(null, owner.getId());
		companyService.deleteCompanyById(companyId);
		
		//send decline notification to owner
		Notification notification = new Notification();
		notification.setnType("info");
		notification.setToUser(owner);
		notification.setMade(new Date());
		notification.setStatus("NEW");
		notification.setSeen(false);
		notification.setText("Administrator decline registration of "
				+ "company "+company.getName());
		notification.setFromUser(admin);
		notificationService.saveNotification(notification);
		return new ResponseEntity<>("Company is not accepted.", HttpStatus.OK);
		
		
	}
	
}
