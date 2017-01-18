package com.nekretnine.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.nekretnine.dto.AdvertisementDTO;
import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Customer;
import com.nekretnine.models.View;
import com.nekretnine.services.AdvertisementService;
import com.nekretnine.services.UserService;
import com.nekretnine.services.ViewService;

@RestController
@RequestMapping(value="api/view")

public class ViewController {

	@Autowired
	private ViewService viewService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdvertisementService advertisementService;
	
	/**
	 * Method used to record every user's advertisement view.
	 * @param principal Object filled with User data on login.
	 * @param advert Id of advertisement a user decided to view.
	 * @return ResponseEntity with a fitting message.
	 * @see UserDetails
	 * @author Nemanja Zunic
	 */
	@RequestMapping(value = "/{advert}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveView(Principal principal, @PathVariable Long advert) {
		
		Customer customer = (Customer) userService.findByUsername(principal.getName());
		Advertisement advertisement = advertisementService.findOne(advert);
		if(advertisement == null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		Date date = new Date();
		viewService.save(new View(customer, advertisement, date));
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
	/**
	 * Method used to get last seen adverts by a particular user
	 * @param principal Object filled with User data on login
	 * @return List of adverts last seen by a user
	 */
	@RequestMapping(method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<List<AdvertisementDTO>> getLastSeen(Principal principal) {
		
		Customer customer = (Customer) userService.findByUsername(principal.getName());
		List<View> views = viewService.findViewsByViewer(customer);
		if(views.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<AdvertisementDTO> result = new ArrayList<>();
		for(View v : views) {
			result.add(new AdvertisementDTO(v.getAdvert()));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
