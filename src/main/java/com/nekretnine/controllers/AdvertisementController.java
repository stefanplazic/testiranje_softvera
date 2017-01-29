package com.nekretnine.controllers;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.AdvertisementDTO;
import com.nekretnine.dto.AdvertisementSearchDTO;
import com.nekretnine.dto.EstateSearchDTO;
import com.nekretnine.dto.ResponseDTO;
import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Estate;
import com.nekretnine.models.Report;
import com.nekretnine.models.User;
import com.nekretnine.services.AdvertisementService;
import com.nekretnine.services.AdvertiserService;
import com.nekretnine.services.EstateService;
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
	
	@Autowired
	private EstateService estateService;
	
	@Autowired
	private AdvertiserService advertiserService;
	
	/**
	 * <p>
	 * 	Add new advertisement
	 * 	method->post	api/advertisement/add
	 * </p>
	 * @param principal			,user data
	 * @param advertisementDTO	,advertisement to be saved , content type application/json
	 * @param estateId			,long estate id 
	 * @return
	 * 
	 * @author sirko
	 */
	@RequestMapping(value="/add/{estateId}",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<ResponseDTO> addAdvertisement(Principal principal,
			@RequestBody AdvertisementDTO advertisementDTO,@PathVariable Long estateId){
		
		Estate e=estateService.findOne(estateId);
		
		if(e==null) 
			return new ResponseEntity<ResponseDTO>(new ResponseDTO("nematenekretnine"),HttpStatus.NOT_FOUND);
		
		Advertiser u=(Advertiser)userService.findByUsername(principal.getName());
		
		if(e.getOwner().getId()!=u.getId()) 
			return new ResponseEntity<ResponseDTO>(new ResponseDTO("nemoze"),HttpStatus.CONFLICT);
		
		Advertisement a=new Advertisement();
		a.setExpiryDate(advertisementDTO.getExpiryDate());
		a.setPublicationDate(advertisementDTO.getPublicationDate());
		a.setAdvertiser(u);
		a.setEstate(e);
		a.setState(Advertisement.State.OPEN);

		advertisementService.save(a);

		return new ResponseEntity<ResponseDTO>(new ResponseDTO("aloebebebebe"), HttpStatus.CREATED);
	}

	/**
	 * <p>
	 * 	Modify advertisement state
	 * 	method-> post	api/advertisement/modify/{advertisement_id}
	 * </p>
	 * @param principal	,user data
	 * @param advertId	, Long advertisement id
	 * @param advertDTO	, new state to be saved {"state": {state} }, content type application/json
	 * @return
	 * 
	 * @author sirko
	 */
	@RequestMapping(value="/modify/{advertId}",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> modifyAdvertisement(Principal principal,@PathVariable Long advertId, @RequestBody AdvertisementDTO advertDTO){
		
		Advertisement adv = advertisementService.findOne(advertId);
		Advertiser u=(Advertiser)userService.findByUsername(principal.getName());		
	
		//da li reklama postoji
		if(adv==null) 
			return new ResponseEntity<>("nemanema",HttpStatus.NOT_FOUND);
		//da li je oglasivac okacio reklamu
		if(adv.getAdvertiser().getId()!=u.getId())
			return new ResponseEntity<>("nemoze",HttpStatus.CONFLICT);
		
		// apdejt status reklame ako je prosledjen
		if (advertDTO.getState() != null)	
			advertisementService.setState(advertDTO.getState(), advertId);
		// apdejt vlasnika reklame ako je prosledjen i ako je nov vlasnik njegov kola(iz iste kompanije)

		return new ResponseEntity<>("braobrao", HttpStatus.OK);
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
	public ResponseEntity<List<AdvertisementDTO>> searchAdvertisements(@RequestBody AdvertisementSearchDTO advertisement,
			@PathParam("page") int page, @PathParam("count") int count) throws ParseException {

		EstateSearchDTO est = advertisement.getEstate();
		Page<Advertisement> adv = advertisementService.findAdvertisements(advertisement.getPublicationDate(), advertisement.getExpiryDate(),
				advertisement.getState(), est.getName(), est.getMinPrice(), est.getMaxPrice(), est.getMinArea(), est.getMaxArea(),
				est.getAddress(), est.getCity(), est.getCityPart(), est.getTechnicalEquipment(), est.getHeatingSystem(), 
				new PageRequest(page, count));		
		List<AdvertisementDTO> result = new ArrayList<AdvertisementDTO>();
		for(Advertisement ad: adv.getContent()) {
			result.add(new AdvertisementDTO(ad));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	

	/**
	 * <p>
	 * 	get advertisements. Works for both registered and unregistered users
	 * 	method->get	api/advertisement/{advertisement_id}
	 * </p>
	 * @param principal
	 * @param advertId
	 * @return requested advertisement
	 * 
	 * @author sirko
	 */
	@RequestMapping(value="/{advertId}",method=RequestMethod.GET)
	public ResponseEntity<AdvertisementDTO> getAdvertisement(Principal principal,@PathVariable Long advertId){
		
		Advertisement adv = advertisementService.findOne(advertId);
		
		//da li reklama postoji
		if(adv==null) 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		AdvertisementDTO advertDTO = new AdvertisementDTO(adv);
		
		
		//neregistrovan (ogranicen prikaz)
		if(principal==null){
			advertDTO.setAdvertiser(null);
			advertDTO.getEstate().setAddress(null);
			advertDTO.getEstate().setOwner(null);
			
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
	
	/**
	 * Support function for pagination
	 *  * param example:
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
	 * @return total number of Advertisements so that we can calculate number of pages
	 * @author Nemanja Zunic
	 */
	@RequestMapping(value="/count/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> allAdvertisementsSize(@RequestBody AdvertisementSearchDTO ad) {
		
		EstateSearchDTO e = ad.getEstate();
		int res = advertisementService.countAdverts(
				ad.getPublicationDate(), ad.getExpiryDate(), ad.getState(), e.getName(), e.getMinPrice(), 
				e.getMaxPrice(), e.getMinArea(), e.getMaxArea(), e.getAddress(), e.getCity(), e.getCityPart(),
				e.getTechnicalEquipment(), e.getHeatingSystem());
		return new ResponseEntity<>(""+res, HttpStatus.OK);
	}


	/**
	 * Get all advertisment for give advert id
	 * @param id
	 * @return
	 * @author stefan
	 */
	@RequestMapping(value="/advert/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<AdvertisementDTO>> getAdvertismentByAdv(@PathVariable long id) {
		
		Advertiser ad = advertiserService.findOne(id);
		if(ad == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<AdvertisementDTO> dtos = new ArrayList<AdvertisementDTO>();
		for(Advertisement a: ad.getAdvertisements())
			dtos.add(new AdvertisementDTO(a));
		return new ResponseEntity<>(dtos ,HttpStatus.OK);
	}
	
	@RequestMapping(value="/check/{estateId}",method=RequestMethod.GET)
	public ResponseEntity<ResponseDTO> checkAdForEstate(@PathVariable Long estateId){
		Estate e = estateService.findOne(estateId);
		if(e==null) return new ResponseEntity<ResponseDTO>(new ResponseDTO("nekretnina ne postoji"),HttpStatus.NOT_FOUND);
		Advertisement a= advertisementService.findOneByEstate(e);
		if(a==null) return new ResponseEntity<ResponseDTO>(new ResponseDTO("false"),HttpStatus.OK);
		System.out.println("alo");
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(a.getId().toString()),HttpStatus.OK);

	}
}
