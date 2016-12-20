package com.nekretnine.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.nekretnine.dto.AdvertisementDTO;
import com.nekretnine.dto.CustomerDTO;
import com.nekretnine.dto.EstateDTO;
import com.nekretnine.dto.PageableDTO;
import com.nekretnine.models.Account;
import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertisement.State;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Customer;
import com.nekretnine.models.Estate;
import com.nekretnine.models.Favourites;
import com.nekretnine.models.User;
import com.nekretnine.services.AccountService;
import com.nekretnine.services.AdvertisementService;
import com.nekretnine.services.AdvertiserService;
import com.nekretnine.services.CustomerService;
import com.nekretnine.services.EstateService;
import com.nekretnine.services.FavouritesService;
import com.nekretnine.services.UserService;



@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {

	
	@Autowired
	private CustomerService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EstateService estateService;
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private AdvertiserService advertiserService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private FavouritesService favouritesService;

	
	@RequestMapping(value = "profile/{id}",method=RequestMethod.GET)
	public ResponseEntity<CustomerDTO> saveEstate(@PathVariable Long id){
		Customer customer = service.findOne(id);
		if(customer == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		
		return new ResponseEntity<>(new CustomerDTO(customer),HttpStatus.OK);
	}
	
	@RequestMapping(value="/buyestate/{typeState}",method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<String> buyEstae(Principal principal, @RequestBody AdvertisementDTO advertisementDTO, @PathVariable String typeState){
		
		//get my user credentials
		User me = userService.findByUsername(principal.getName());	
		Customer customer = (Customer) me;
		Advertisement advertisement = advertisementService.findOne(advertisementDTO.getId());
		
		//check if exists
		if(advertisement == null)
			return new ResponseEntity<>("Advertisement: doesn't exists" ,HttpStatus.NOT_FOUND);
		
		Estate estate = estateService.findOne(advertisement.getEstate().getId());
		
		//check if advertisment date is expired
		if(advertisement.getExpiryDate().before(new Date()))
			return new ResponseEntity<>("Advertisement has expired" ,HttpStatus.LOCKED);
		/*if(advertisement.getState() != State.OPEN )
			return new ResponseEntity<>("Advertisement is already bought,removed or sold" ,HttpStatus.LOCKED);*/
		
		if(me.getAccount() == null) 
			return new ResponseEntity<>("You don't have account, configure it on /api/account/config" ,HttpStatus.NOT_FOUND);
		else if(me.getAccount().getAmount() < advertisement.getEstate().getPrice())
			return new ResponseEntity<>("You don't have enough money on your account, add money on /api/account/addMoney" ,HttpStatus.BAD_REQUEST);
		
		if(advertisement.getAdvertiser().getAccount() == null)
			return new ResponseEntity<>("You can't make payments, advertiser didn't configured his money account" ,HttpStatus.NOT_FOUND);
		//put money on advertiser account
		accountService.addMoney(advertisement.getAdvertiser().getAccount().getId(), advertisement.getEstate().getPrice());
		//take that money from customer account
		Account cusAccount = me.getAccount();
		cusAccount.setAmount(cusAccount.getAmount() - advertisement.getEstate().getPrice());
		//save state
		accountService.save(cusAccount);
		
		
		//buy this estate
		State saveState = typeState.equalsIgnoreCase("buy")? State.SOLD: State.RENTED;
		advertisement.setState(saveState);
		advertisement.setSoldto(customer);
		advertisementService.save(advertisement);
		
		//remove estate from other advertisers
		List<Advertiser> advertisers = advertiserService.findAll();
		for(Advertiser advrt : advertisers){
			
			//check if it is not given advertiser 
			if(advrt.getId() != advertisement.getAdvertiser().getId()){
				for(Advertisement advertis : advrt.getAdvertisements()){
					
					//if it have given estate,remove it from the list
					if(advertis.getEstate().getId() == advertisement.getEstate().getId()){
						advrt.getAdvertisements().remove(advertis);
						//delete advertisment
						advertisementService.delete(advertis.getId());
					}
						
				}
			}
		}
		
		return new ResponseEntity<>("You succesfully bought/reserved real estate" ,HttpStatus.OK);
	}
	
	@RequestMapping(value="/myprofile",method=RequestMethod.GET)
	public ResponseEntity<CustomerDTO> getMyProfile(Principal principal){
		
		//get my user credentials
		User me = userService.findByUsername(principal.getName());	
		CustomerDTO customerDTO = new CustomerDTO((Customer)me);
		
		return new ResponseEntity<>(customerDTO ,HttpStatus.OK);
	}
	
	@RequestMapping(value="/myEstates",method=RequestMethod.GET)
	public ResponseEntity<List<EstateDTO>> getMyEstates(Principal principal){
		
		//get my user credentials
		User me = userService.findByUsername(principal.getName());	
		Customer customer = (Customer)me;
		
		List<EstateDTO> estateDTOs = new ArrayList<EstateDTO>();
		//get estates from advertisement and convert them to DTOs
		for(Advertisement advertisement : customer.getBoughtAdvertisement()){
			estateDTOs.add(new EstateDTO(advertisement.getEstate()));
		}
		return new ResponseEntity<List<EstateDTO>>(estateDTOs, HttpStatus.OK);
	}
	
	/**
	 * mile
	 * @param principal
	 * @param estate_id
	 * @return
	 */
	@RequestMapping(value="/addFavourite/{estate_id}",method=RequestMethod.POST)
	public ResponseEntity<String> addFavourite(Principal principal, @PathVariable Long estate_id){
		
		Customer customer = (Customer) userService.findByUsername(principal.getName());	
		Estate estate = estateService.findOne(estate_id);
		if(estate == null){
			return new ResponseEntity<>("Estate not found" ,HttpStatus.NOT_FOUND);
		}
		Favourites favourite = new Favourites();
		favourite.setCustomer(customer);
		favourite.setEstate(estate);
		favouritesService.save(favourite);
		return new ResponseEntity<>("Favourite Estate is saved" ,HttpStatus.OK);
	}
	
	@RequestMapping(value="/unmarkFavourite/{estate_id}",method=RequestMethod.POST)
	public ResponseEntity<String> unmarkFavourite(Principal principal, @PathVariable Long estate_id){
		
		Customer customer = (Customer) userService.findByUsername(principal.getName());	
		Estate estate = estateService.findOne(estate_id);
		if(estate == null){
			return new ResponseEntity<>("Estate not found" ,HttpStatus.NOT_FOUND);
		}
		
		Favourites favourite = favouritesService.findByEstateAndCustomer(estate, customer);
		if(favourite == null){
			return new ResponseEntity<>("This estate isn't in customers favourites" ,HttpStatus.NOT_FOUND);
		}
		favouritesService.delete(favourite.getId());
		return new ResponseEntity<>("Estate is unmarked" ,HttpStatus.OK);
	}
	/**
	 * mile
	 * @param principal
	 * @param pageableDTO
	 * @return
	 */
	@RequestMapping(value="/getFavourites",method=RequestMethod.POST)
	public ResponseEntity<List<EstateDTO>> getFavourites(Principal principal, @RequestBody PageableDTO pageableDTO){
		
		Customer customer = (Customer) userService.findByUsername(principal.getName());	
		Page<Favourites> page = favouritesService.findAllByCustomer(new PageRequest(pageableDTO.getPage(),pageableDTO.getCount()), customer);
		List<Favourites> favourites = page.getContent();
		List<EstateDTO> estates = new ArrayList<EstateDTO>();
		for (Favourites f: favourites){
			estates.add(new EstateDTO(f.getEstate()));
		}
		return new ResponseEntity<>(estates ,HttpStatus.OK);
	}

}
