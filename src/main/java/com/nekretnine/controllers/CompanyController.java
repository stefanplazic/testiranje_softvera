package com.nekretnine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.CompanyDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Company;
import com.nekretnine.services.AdvertiserService;
import com.nekretnine.services.CompanyService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value="api/company")
public class CompanyController {
	
	@Autowired
	private CompanyService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdvertiserService advertiserService;
	
	/**
	 * mile
	 * @param companyDTO
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<String> saveCompany(@RequestBody CompanyDTO companyDTO){
	
		Advertiser owner = (Advertiser)userService.findByEmail(companyDTO.getOwner().getEmail());		
		if (owner == null) {
			return new ResponseEntity<String>("Owner not found.", HttpStatus.NOT_FOUND);
		}else if(owner.getCompany()!=null){
			return new ResponseEntity<String>("Owner is already employee.", HttpStatus.BAD_REQUEST);
		}
	
		Company company = service.findOneByNameAndAddress(companyDTO.getName(), 
				companyDTO.getAddress());
		
		if(company == null){
			Company com = new Company(companyDTO);
			com.setOwner(new Advertiser(owner));
			com = service.saveCompany(com);
			advertiserService.setAdvertisersCompany(com, owner.getId());
			return new ResponseEntity<String>("The request for company has successfully added.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("The company with entered name and address already exists.", HttpStatus.NOT_FOUND);
			
	}
	
	
}
