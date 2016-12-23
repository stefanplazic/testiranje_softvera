package com.nekretnine.controllers;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.fabric.xmlrpc.base.Data;
import com.nekretnine.dto.AdvertisementDTO;
import com.nekretnine.dto.EstateDTO;
import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Report;
import com.nekretnine.models.User;
import com.nekretnine.services.AdvertisementService;
import com.nekretnine.services.ReportService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value = "api/advertisement")
public class AdvertisementController {

	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReportService reportService;
	
	/**
	 * <p>
	 * 	Add new advertisement
	 * 	method->post	api/advertisement/add
	 * </p>
	 * @param principal			,user data
	 * @param advertisementDTO	,advertisement to be saved , content type application/json
	 * @return
	 * 
	 * @author sirko
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> add_advertisement(Principal principal,@RequestBody AdvertisementDTO advertisementDTO){
		
		Advertiser u=(Advertiser)userService.findByUsername(principal.getName());		
		Advertisement a=new Advertisement(advertisementDTO);
		a.setAdvertiser(u);

		advertisementService.save(a);

		return new ResponseEntity<String>("aloebebebebe", HttpStatus.OK);
	}

	/**
	 * <p>
	 * 	Modify advertisement state
	 * 	method-> post	api/advertisement/modify/{advertisement_id}
	 * </p>
	 * @param principal	,user data
	 * @param advert_id	, Long advertisement id
	 * @param advertDTO	, new state to be saved {"state": {state} }, content type application/json
	 * @return
	 * 
	 * @author sirko
	 */
	@RequestMapping(value="/modify/{advert_id}",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> modify_addvertisement(Principal principal,@PathVariable Long advert_id,@RequestBody AdvertisementDTO advertDTO){
		
		Advertisement adv = advertisementService.findOne(advert_id);
		Advertiser u=(Advertiser)userService.findByUsername(principal.getName());		
	
		//da li reklama postoji
		if(adv==null) return new ResponseEntity<String>("nemanema",HttpStatus.OK);
		//da li je oglasivac okacio reklamu
		if(adv.getAdvertiser().getId()!=u.getId())return new ResponseEntity<String>("nemoze",HttpStatus.OK);
		
		// apdejt status reklame ako je prosledjen
		if (advertDTO.getState() != null)	advertisementService.setState(advertDTO.getState(), advert_id);
		// apdejt vlasnika reklame ako je prosledjen i ako je nov vlasnik njegov kola(iz iste kompanije)

		return new ResponseEntity<String>("braobrao", HttpStatus.OK);
	}

	/**
	 * param example:
	 * {
		"state" : "OPEN",
		"publicationDate" : "",
		"expiryDate" : "",
		"estate" : {
			"name" : "s",
			"price" : 3,
			"area" : 3,
			"address" : "sumadijska",
			"city" : "s",
			"cityPart" : "s",
		 	"technicalEquipment" : "s",
			"heatingSystem" : "s"
		 }
		}
	 * @param search criteria provided by user which the method uses to find specific Advertisement / Estate
	 * @return List of AdvertisementDTOs that fit the search criteria provided by user 
	 * @see AdvertEstateDTO, Advertisement, Estate
	 * @author Nemanja Zunic
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<AdvertisementDTO>> searchAdvertisements(@RequestBody AdvertisementDTO advertisement) throws ParseException {

		EstateDTO est = advertisement.getEstate();
		List<Advertisement> adv = advertisementService.findAdvertisements(advertisement.getPublicationDate(), advertisement.getExpiryDate(),
				advertisement.getState(), est.getName(), est.getPrice(), est.getArea(), est.getAddress(), est.getCity(), est.getCityPart(), 
				 est.getTechnicalEquipment(), est.getHeatingSystem());		
		List<AdvertisementDTO> result = new ArrayList<AdvertisementDTO>();
		for(Advertisement ad: adv) {
			result.add(new AdvertisementDTO(ad));
		}
		return new ResponseEntity<List<AdvertisementDTO>>(result, HttpStatus.OK);
	}
	
	/**
	 * <p>
	 * 	Delete advertisement
	 * 	method-> delete	api/delete/{advertisement_id}
	 * </p>
	 * @param principal	,user data
	 * @param advert_id , Long advertisement id
	 * @return
	 * 
	 * @author sirko
	 */
	@RequestMapping(value="/delete/{advert_id}",method=RequestMethod.DELETE,consumes="application/json")
	public ResponseEntity<String> delete_advertisement(Principal principal,@PathVariable Long advert_id ){
		
		Advertiser u=(Advertiser)userService.findByUsername(principal.getName());	
		Advertisement adv = advertisementService.findOne(advert_id);
		
		//da li reklama postoji
		if(adv==null) return new ResponseEntity<String>("nemanema",HttpStatus.OK);
		//da li je oglasivac okacio reklamu
		if(adv.getAdvertiser().getId()!=u.getId()) return new ResponseEntity<String>("nemoze",HttpStatus.OK);
			
		
		advertisementService.delete(advert_id);
		
		return new ResponseEntity<String>("brisnuto",HttpStatus.OK);
	}
	

	/**
	 * <p>
	 * 	get advertisements. Works for both registered and unregistered users
	 * 	method->get	api/advertisement/{advertisement_id}
	 * </p>
	 * @param principal
	 * @param advert_id
	 * @return
	 * 
	 * @author sirko
	 */
	@RequestMapping(value="/{advert_id}",method=RequestMethod.GET)
	public ResponseEntity<AdvertisementDTO> get_advertisement(Principal principal,@PathVariable Long advert_id ){
		
		Advertisement adv = advertisementService.findOne(advert_id);
		
		//da li reklama postoji
		if(adv==null) return new ResponseEntity<>(HttpStatus.OK);
		
		AdvertisementDTO advertDTO = new AdvertisementDTO(adv);
		
		
		//neregistrovan (ogranicen prikaz)
		if(principal==null){
			advertDTO.setAdvertiser(null);
			advertDTO.getEstate().setAddress(null);
			advertDTO.getEstate().setOwner(null);
			
		//registrovan(potpun prikaz)
		}else{
			
		}
		
		
		return new ResponseEntity<>(advertDTO,HttpStatus.OK);
	}
	
	/**
	 * Method used to report a specific Advertisement for inappropriate content by a User.
	 * Moderator should receive a Notification of the Report and respond by removing the Advertisement or 
	 * changing the Report State to CLOSED.
	 * Call example:
	 * localhost:8080/api/advertisement/1/report/?message=because its rude
	 * @param principal Object containing User related data, parsed from token sent by User
	 * @param advert Advertisement id of advertisement being reported by the User.
	 * @param message Appropriate message explaining why specific Advertisement is being reported.
	 * @return Message informing the Client that Report has been received.
	 * @author Nemanja Zunic
	 * @see Advertisement, User, Report, Notification
	 */
	@RequestMapping(value="{advert}/report/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> reportAdvertisement(Principal principal, @PathVariable Long advert, @RequestParam String message){
		User user = userService.findByUsername(principal.getName());
		Advertisement advertisement = advertisementService.findOne(advert);
		
		reportService.save(new Report(user, advertisement, message, "NEW", true));
		return new ResponseEntity<>("braobrao", HttpStatus.OK);
	}
	


}
