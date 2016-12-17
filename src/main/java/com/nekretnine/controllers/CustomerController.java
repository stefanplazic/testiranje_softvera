package com.nekretnine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.CustomerDTO;
import com.nekretnine.models.Customer;
import com.nekretnine.services.CustomerService;



@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {

	
	@Autowired
	private CustomerService service;
	
	@RequestMapping(value = "profile/{id}",method=RequestMethod.GET)
	public ResponseEntity<CustomerDTO> saveEstate(@PathVariable Long id){
		Customer customer = service.findOne(id);
		if(customer == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		
		return new ResponseEntity<>(new CustomerDTO(customer),HttpStatus.OK);
	}
}
