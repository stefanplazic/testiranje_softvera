package com.nekretnine.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.EstateDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Estate;
import com.nekretnine.models.Image;
import com.nekretnine.services.EstateService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value="api/estate")
public class EstateController {

	@Autowired
	private EstateService estateService;
	
	@Autowired
	private UserService userService;
		
	//za oglasivaca
	@RequestMapping(method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> saveEstate(Principal principal,@RequestBody EstateDTO estateDTO){
		//oglasivac
		Advertiser owner=(Advertiser) userService.findByUsername(principal.getName());
		
		Estate estate= new Estate(estateDTO);
		estate.setOwner(owner);
		
		for(Image i :estate.getImages()){
			i.setEstate(estate);
		}
		estateService.save(estate);	
				
		return new ResponseEntity<String>("aloebebebebe",HttpStatus.OK);
	}

}
