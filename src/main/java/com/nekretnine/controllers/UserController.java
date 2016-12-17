package com.nekretnine.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.UserDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.User;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<User> users = service.findAll();
		List<UserDTO> dtos = new ArrayList<>();
		for(User u : users){
			UserDTO dto = new UserDTO(u);
			dtos.add(dto);
		}
		
		return new ResponseEntity<List<UserDTO>>(dtos, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
		
		User user = service.findOne(id);
		if(user==null)
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<UserDTO>(new UserDTO(user),HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO){
		User user = new Advertiser();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUsername());
		
		user = service.save(user);
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);	
	}
	/*
	@RequestMapping(value = "/findUser", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> findOneByUsernameAndPassword(@RequestBody LoginDTO loginDTO) {
		UserDTO user = new UserDTO(service.findOneByUsernameAndPassword(loginDTO.getUsername(), 
				loginDTO.getPassword()));
		return new ResponseEntity<>(user, HttpStatus.OK);
	}*/	
}
