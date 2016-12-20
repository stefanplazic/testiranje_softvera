package com.nekretnine.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.CustomerDTO;
import com.nekretnine.models.Customer;
import com.nekretnine.models.Estate;
import com.nekretnine.models.Favourites;
import com.nekretnine.models.User;
import com.nekretnine.services.CustomerService;
import com.nekretnine.services.EstateService;
import com.nekretnine.services.FavouritesService;
import com.nekretnine.services.UserService;



@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {

	
	@Autowired
	private CustomerService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FavouritesService favouritesService;
	
	@Autowired
	private EstateService estateService;
	
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
	
	/**
	 * mile
	 * @param principal
	 * @param estate_id
	 * @return
	 */
	@RequestMapping(value="/addFavourite/{estate_id}",method=RequestMethod.POST)
	public ResponseEntity<String> addFavourite(Principal principal, @PathVariable Long estate_id){
		
		Customer customer = (Customer) userService.findByUsername(principal.getName());	
		Estate estate = estateService.findOne(estate_id);
		if(estate == null){
			return new ResponseEntity<>("Estate not found" ,HttpStatus.NOT_FOUND);
		}
		Favourites favourite = new Favourites();
		favourite.setCustomer(customer);
		favourite.setEstate(estate);
		favouritesService.save(favourite);
		return new ResponseEntity<>("Favourite Estate is saved" ,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getFavourites",method=RequestMethod.GET)
	public ResponseEntity<String> getFavourites(Principal principal){
		
		Customer customer = (Customer) userService.findByUsername(principal.getName());	
		
		return new ResponseEntity<>("Favourite Estate is saved" ,HttpStatus.OK);
	}
}
