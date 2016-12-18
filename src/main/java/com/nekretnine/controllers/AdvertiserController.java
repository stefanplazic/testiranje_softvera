package com.nekretnine.controllers;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.AdvertiserDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.CallToCompany;
import com.nekretnine.models.User;
import com.nekretnine.services.AdvertiserService;
import com.nekretnine.services.CallToCompanyService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value="api/advertiser")
public class AdvertiserController {
	
	@Autowired
	private AdvertiserService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CallToCompanyService callService;

	/**
	 * 
	 * @param id of advertiser who's profile we want to get data about
	 * @return
	 */
	@RequestMapping(value="/profile/{id}",method=RequestMethod.GET)
	public ResponseEntity<AdvertiserDTO> getAdvertiserProfile(@PathVariable Long id){
		Advertiser advertiser = service.findOne(id);
		if(advertiser == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		AdvertiserDTO advertiserDTO = new AdvertiserDTO(advertiser);
		
		return new ResponseEntity<>(advertiserDTO ,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param advertiserDTO potencional worker for our company
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/callToCompany",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> addToCompany(@RequestBody AdvertiserDTO advertiserDTO,Principal principal){
		User user = userService.findByUsername(advertiserDTO.getUsername());
		if(user == null || !(user instanceof Advertiser))
			return new ResponseEntity<>("Ther's not such advertiser" ,HttpStatus.NOT_FOUND);
		//get the username of advertiser from token
		Advertiser me = (Advertiser) userService.findByUsername(principal.getName());
		if(me.getCompany() == null){
			return new ResponseEntity<>("Advertiser doesn't work in any company" ,HttpStatus.NOT_FOUND);
		}
		
		CallToCompany callToCompany = new CallToCompany();
		callToCompany.setFromAdvertiser(me);
		callToCompany.setToAdvertiser((Advertiser)user);
		callToCompany.setDateOfCall(new Date());
		//save call to company
		callService.save(callToCompany);
		
		return new ResponseEntity<>("Request send" ,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/acceptCall",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> acceptCallTOCompany(@RequestBody AdvertiserDTO advertiserDTO,Principal principal){
		User user = userService.findByUsername(advertiserDTO.getUsername());
		if(user == null || !(user instanceof Advertiser))
			return new ResponseEntity<>("Ther's not such advertiser" ,HttpStatus.NOT_FOUND);
		//get the username of advertiser from token
		Advertiser me = (Advertiser) userService.findByUsername(principal.getName());
		if(me.getCompany() == null){
			return new ResponseEntity<>("Advertiser doesn't work in any company" ,HttpStatus.NOT_FOUND);
		}
		
		CallToCompany callToCompany = new CallToCompany();
		callToCompany.setFromAdvertiser(me);
		callToCompany.setToAdvertiser((Advertiser)user);
		callToCompany.setDateOfCall(new Date());
		//save call to company
		callService.save(callToCompany);
		
		return new ResponseEntity<>("Request send" ,HttpStatus.OK);
	}
	
	
	
}
