package com.nekretnine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.EstateDTO;
import com.nekretnine.models.Estate;
import com.nekretnine.models.Image;
import com.nekretnine.services.EstateService;

@RestController
@RequestMapping(value="api/estate")
public class EstateController {

	@Autowired
	private EstateService estateService;
		
	@RequestMapping(method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> saveEstate(@RequestBody EstateDTO estateDTO){
		
		Estate estate= new Estate(estateDTO);
		for(Image i :estate.getImages()){
			i.setEstate(estate);
		}
		estateService.save(estate);	
		System.out.println(estate.toString());
		
		return new ResponseEntity<String>("aloebebebebe",HttpStatus.OK);
	}

}
