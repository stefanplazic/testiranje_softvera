package com.nekretnine.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nekretnine.dto.UserDTO;
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
}
