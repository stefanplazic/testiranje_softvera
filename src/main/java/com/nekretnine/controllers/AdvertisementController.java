package com.nekretnine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.AdvertisementDTO;
import com.nekretnine.models.Advertisement;
import com.nekretnine.services.AdvertisementService;

@RestController
@RequestMapping(value="api/advertisement")
public class AdvertisementController {
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@RequestMapping(method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> saveEstate(@RequestBody AdvertisementDTO advertisementDTO){
		
		System.out.println(advertisementDTO.toString());
		Advertisement a=new Advertisement(advertisementDTO);
		
		advertisementService.save(a);
		
		return new ResponseEntity<String>("aloebebebebe",HttpStatus.OK);
	}

}
