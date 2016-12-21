package com.nekretnine.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.EstateDTO;
import com.nekretnine.dto.RateDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Customer;
import com.nekretnine.models.Estate;
import com.nekretnine.models.Image;
import com.nekretnine.models.RateEstate;
import com.nekretnine.services.EstateService;
import com.nekretnine.services.RateEstateService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value="api/estate")
public class EstateController {

	@Autowired
	private EstateService estateService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private RateEstateService rateService;
		
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
	
	//za kupca
		@RequestMapping(value="/rate/{estate_id}",method=RequestMethod.POST)
		public ResponseEntity<String> set_rate(Principal principal,@RequestBody RateDTO rateDTO,@PathVariable Long estate_id){
			Estate e=estateService.findOne(estate_id); //nekretnina
			Customer c=(Customer) userService.findByUsername(principal.getName());//kupac
			
			//da li nekretnina postoji
			if(e==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
			//kreiranje rate-estate objekta
			RateEstate re=new RateEstate();
			re.setAdvertisementRate(rateDTO.getRate());
			re.setEstate(e);
			re.setCustomer(c);
		
			//sve to u bazu
			rateService.save(re);
			
			return new ResponseEntity<>(HttpStatus.CREATED);
		}

}
