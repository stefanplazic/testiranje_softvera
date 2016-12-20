package com.nekretnine.controllers;

import static org.mockito.Mockito.calls;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.AdvertiserDTO;
import com.nekretnine.dto.CallToCompanyDTO;
import com.nekretnine.dto.EstateDTO;
import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertisement.State;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.CallToCompany;
import com.nekretnine.models.Company;
import com.nekretnine.models.Customer;
import com.nekretnine.models.User;
import com.nekretnine.services.AdvertiserService;
import com.nekretnine.services.CallToCompanyService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value="api/advertiser")
public class AdvertiserController {
	
	@Autowired
	private AdvertiserService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CallToCompanyService callService;

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
	
	@RequestMapping(value="/myprofile",method=RequestMethod.GET)
	public ResponseEntity<AdvertiserDTO> getMyProfile(Principal principal){
		
		//get my user credentials
		User me = userService.findByUsername(principal.getName());	
		AdvertiserDTO advertiserDTO = new AdvertiserDTO((Advertiser)me);
		
		return new ResponseEntity<>(advertiserDTO ,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param advertiserDTO potencional worker for our company
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/callToCompany",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> addToCompany(@RequestBody AdvertiserDTO advertiserDTO,Principal principal){
		User user = userService.findByUsername(advertiserDTO.getUsername());
		if(user == null || !(user instanceof Advertiser))
			return new ResponseEntity<>("Ther's not such advertiser" ,HttpStatus.NOT_FOUND);
		//get the username of advertiser from token
		Advertiser me = (Advertiser) userService.findByUsername(principal.getName());
		if(me.getCompany() == null){
			return new ResponseEntity<>("Advertiser doesn't work in any company" ,HttpStatus.NOT_FOUND);
		}
		
		CallToCompany callToCompany = new CallToCompany();
		callToCompany.setFromAdvertiser(me);
		callToCompany.setToAdvertiser((Advertiser)user);
		callToCompany.setDateOfCall(new Date());
		//save call to company
		callService.save(callToCompany);
		
		return new ResponseEntity<>("Request send" ,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/acceptCall",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> acceptCallTOCompany(@RequestBody CallToCompanyDTO callToCompanyDTO,Principal principal){
		
		//get the username of advertiser from token
		Advertiser me = (Advertiser) userService.findByUsername(principal.getName());
		if(me.getCompany() != null){
			return new ResponseEntity<>("Advertiser is already working in some company" ,HttpStatus.BAD_REQUEST);
		}
		//get all data about sender advertiser
		CallToCompany callToCompany = callService.findOne(callToCompanyDTO.getId());
		Advertiser sender = callToCompany.getFromAdvertiser();
		Company company = sender.getCompany();
		//register new advertiser as company employer
		me.setCompany(company);
		service.save(me);
		
		//delete all request for qiven user
		List<CallToCompany> companies =  callService.findByToadvrt(me);
		for(CallToCompany callToCompan : companies){
			callService.remove(callToCompan.getId());
		}
		return new ResponseEntity<>("Congretulate you are company employee!!" ,HttpStatus.OK);
	}
	
	@RequestMapping(value="/allCalls",method=RequestMethod.GET)
	public ResponseEntity<List<CallToCompanyDTO>> acceptCallTOCompany(Principal principal){
		
		//get the username of advertiser from token
		Advertiser me = (Advertiser) userService.findByUsername(principal.getName());
		
		//get all call from company
		List<CallToCompanyDTO> callToCompaniesDTO = new ArrayList<CallToCompanyDTO>();
		List<CallToCompany> companies =  callService.findByToadvrt(me);
		for(CallToCompany callToCompan : companies){
			callToCompaniesDTO.add(new CallToCompanyDTO(callToCompan));
		}
		return new ResponseEntity<List<CallToCompanyDTO>>(callToCompaniesDTO ,HttpStatus.FOUND);
	}
	
	@RequestMapping(value="/unemployed",method=RequestMethod.GET)
	public ResponseEntity<List<AdvertiserDTO>> getUnemployed(){
		
		//put all unemployed users in this list
		List<AdvertiserDTO> advertiserDTOs = new ArrayList<AdvertiserDTO>();
		
		//get all advertisers
		List<Advertiser> advertisers =  service.findAll();
		for(Advertiser advrt : advertisers){
			//find only them with out company
			if(advrt.getCompany()==null)
				advertiserDTOs.add(new AdvertiserDTO(advrt));
		}
		
		return new ResponseEntity<List<AdvertiserDTO>>(advertiserDTOs ,HttpStatus.FOUND);
	}
	@RequestMapping(value="/soldEstates",method=RequestMethod.GET)
	public ResponseEntity<List<EstateDTO>> getSoldEstates(Principal principal){
		
		//get my user credentials
		User me = userService.findByUsername(principal.getName());	
		Advertiser advertiser = (Advertiser)me;
		
		List<EstateDTO> estateDTOs = new ArrayList<EstateDTO>();
		//get estates from advertisement and convert them to DTOs
		for(Advertisement advertisement : advertiser.getAdvertisements()){
			if(advertisement.getState() == State.RENTED || advertisement.getState() == State.SOLD)
			estateDTOs.add(new EstateDTO(advertisement.getEstate()));
		}
		return new ResponseEntity<List<EstateDTO>>(estateDTOs, HttpStatus.OK);
	}
}
