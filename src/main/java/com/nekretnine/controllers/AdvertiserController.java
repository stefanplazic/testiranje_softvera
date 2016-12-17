package com.nekretnine.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="api/advertiser")
public class AdvertiserController {

	@RequestMapping(value="/profile/{id}",method=RequestMethod.GET)
	public ResponseEntity<String> getAdvertiserProfile(@PathVariable Long id){
		
		return new ResponseEntity<>("Advertiser founded" ,HttpStatus.OK);
	}
	
}
