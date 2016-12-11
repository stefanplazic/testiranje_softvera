package com.nekretnine.controllers;

import java.util.HashSet;
import java.util.Set;

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
import com.nekretnine.services.AdvertiserService;

@RestController
@RequestMapping(value = "/api/advertisers")
public class AdvertiserController {

	@Autowired
	private AdvertiserService service;

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<AdvertiserDTO> getAdvertiserById(@PathVariable Long id){
		
		Advertiser advertiser = service.findOne(id);
		if(advertiser==null)
			return new ResponseEntity<AdvertiserDTO>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<AdvertiserDTO>(new AdvertiserDTO(advertiser),HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<AdvertiserDTO> saveAdvertiser(@RequestBody AdvertiserDTO advDTO){
		Advertiser adv = new Advertiser(advDTO);		
		adv = service.save(adv);
		return new ResponseEntity<>(new AdvertiserDTO(adv), HttpStatus.CREATED);	
	}

}
