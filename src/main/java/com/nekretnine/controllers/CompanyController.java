package com.nekretnine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.CompanyDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Company;
import com.nekretnine.models.User;
import com.nekretnine.services.CompanyService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value="api/company")
public class CompanyController {
	
	@Autowired
	private CompanyService service;
	
	@Autowired
	private UserService userService;
	
	/**
	 * mile
	 * @param companyDTO
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<String> saveCompany(@RequestBody CompanyDTO companyDTO){
		Company company = service.findOneByNameAndAddress(companyDTO.getName(), 
				companyDTO.getAddress());

		User owner = userService.findOne(companyDTO.getOwner().getId());		
		if (owner == null) {
			return new ResponseEntity<String>("Owner not found.", HttpStatus.NOT_FOUND);
		}	
		if(company == null){
			Company com = new Company(companyDTO);
			com.setOwner(new Advertiser(owner));
			service.saveCompany(com);
			return new ResponseEntity<String>("The company has successfully added.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("The company with entered name and address already exists.", HttpStatus.NOT_FOUND);
			
	}
	
	/**
	 * mile
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<CompanyDTO> getUserCompany(@PathVariable Long id){
		Company company = service.findOne(id);
		if(company == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
	}
	
}
