package com.nekretnine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	//za oglasivaca
	@RequestMapping(value="/add",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> add_advertisement(@RequestBody AdvertisementDTO advertisementDTO){
		
		
		Advertisement a=new Advertisement(advertisementDTO);		
		advertisementService.save(a);
		
		return new ResponseEntity<String>("aloebebebebe",HttpStatus.OK);
	}
	
	//za oglasivaca
	@RequestMapping(value="/modify/{advert_id}",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> modify_addvertisement(@PathVariable Long advert_id,@RequestBody AdvertisementDTO advertDTO){
		
		Advertisement adv = advertisementService.findOne(advert_id);
		
		//da li reklama postoji
		if(adv==null) return new ResponseEntity<String>("nemanema",HttpStatus.OK);
		//da li je oglasivac okacio reklamu
		if(adv.getId()==advertDTO.getAdvertiser().getId())new ResponseEntity<String>("nemanema",HttpStatus.OK);
		
		//apdejt status reklame ako je prosledjen
		if(advertDTO.getState()!=null) advertisementService.setState(advertDTO.getState(),advert_id);
		//apdejt vlasnika reklame ako je prosledjen i ako je nov vlasnik njegov kola(iz iste kompanije)
		
		
		return new ResponseEntity<String>("braobrao",HttpStatus.OK);
	}
	
	

}
