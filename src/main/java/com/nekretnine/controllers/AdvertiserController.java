package com.nekretnine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.AdvertiserDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.services.AdvertiserService;

@RestController
@RequestMapping(value="api/advertiser")
public class AdvertiserController {
	
	@Autowired
	private AdvertiserService service;

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
	
	
	
}
