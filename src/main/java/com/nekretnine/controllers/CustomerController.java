package com.nekretnine.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.AdvertiserDTO;
import com.nekretnine.dto.CustomerDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Customer;
import com.nekretnine.models.User;
import com.nekretnine.services.CustomerService;
import com.nekretnine.services.UserService;



@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {

	
	@Autowired
	private CustomerService service;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "profile/{id}",method=RequestMethod.GET)
	public ResponseEntity<CustomerDTO> saveEstate(@PathVariable Long id){
		Customer customer = service.findOne(id);
		if(customer == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		
		return new ResponseEntity<>(new CustomerDTO(customer),HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/myprofile",method=RequestMethod.GET)
	public ResponseEntity<CustomerDTO> getMyProfile(Principal principal){
		
		//get my user credentials
		User me = userService.findByUsername(principal.getName());	
		CustomerDTO customerDTO = new CustomerDTO((Customer)me);
		
		return new ResponseEntity<>(customerDTO ,HttpStatus.OK);
	}
}
