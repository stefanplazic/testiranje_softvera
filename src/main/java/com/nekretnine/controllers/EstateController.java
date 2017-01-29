package com.nekretnine.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
import com.nekretnine.dto.ResponseDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Customer;
import com.nekretnine.models.Estate;
import com.nekretnine.models.Image;
import com.nekretnine.models.RateEstate;
import com.nekretnine.services.EstateService;
import com.nekretnine.services.RateEstateService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value = "api/estate")
public class EstateController {

	@Autowired
	private EstateService estateService;

	@Autowired
	private UserService userService;

	@Autowired
	private RateEstateService rateService;

	/**
	 * <p>
	 * Adding new estate method->post api/estate/add
	 * </p>
	 * 
	 * @param principal
	 *            , user data
	 * @param estateDto
	 *            , estate data to be saved , content type application/json
	 * @return
	 * 
	 * @author sirko
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<ResponseDTO> saveEstate(Principal principal, @RequestBody EstateDTO estateDTO) {
		System.out.println(estateDTO.toString());
		Advertiser owner = (Advertiser) userService.findByUsername(principal.getName());
		Estate e = estateService.findOneByName(estateDTO.getName());

		if (e != null)
			return new ResponseEntity<>(new ResponseDTO("vecima"), HttpStatus.CONFLICT);

		Estate estate = new Estate(estateDTO);
		estate.setOwner(owner);

		for (Image i : estate.getImages()) {
			i.setEstate(estate);
		}
		estateService.save(estate);

		return new ResponseEntity<>(new ResponseDTO("aloebebebebe"), HttpStatus.CREATED);
	}

	/**
	 * <p>
	 * Rate estate method-> post api/estate/rate/{estate_id}
	 * </p>
	 * 
	 * @param principal
	 *            , user data
	 * @param rateDTO
	 *            ,estate rate
	 * @param estateId
	 *            ,Long estate id
	 * @return
	 * 
	 * @author sirko
	 */
	@RequestMapping(value = "/rate/{estateId}", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> setRate(Principal principal, @RequestBody RateDTO rateDTO,
			@PathVariable Long estateId) {
		Estate e = estateService.findOne(estateId); // nekretnina
		Customer c = (Customer) userService.findByUsername(principal.getName());// kupac

		// da li nekretnina postoji
		if (e == null)
			return new ResponseEntity<ResponseDTO>(new ResponseDTO("nepostojinekretnina"), HttpStatus.NOT_FOUND);

		// da li je vec rejtovao
		RateEstate jel = rateService.findOneByEstateAndCustomer(e, c);
		if (jel != null)
			return new ResponseEntity<ResponseDTO>(new ResponseDTO("vec si rejtovao"), HttpStatus.OK);

		// kreiranje rate-estate objekta
		RateEstate re = new RateEstate();
		re.setAdvertisementRate(rateDTO.getRate());
		re.setEstate(e);
		re.setCustomer(c);

		// sve to u bazu
		rateService.save(re);

		return new ResponseEntity<ResponseDTO>(new ResponseDTO("brao"), HttpStatus.CREATED);
	}

	/**
	 * <p>
	 * Get estate method-> get api/estate/{estate_id}
	 * </p>
	 * 
	 * @param principal
	 *            , user data
	 * @param estateId
	 *            ,Long estate id
	 * @return EstateDTO
	 * 
	 * @author sirko
	 */
	@RequestMapping(value = "/{estateId}", method = RequestMethod.GET)
	public ResponseEntity<EstateDTO> getRate(Principal principal, @PathVariable Long estateId) {

		if (principal == null) {
			return new ResponseEntity<EstateDTO>(new EstateDTO(), HttpStatus.BAD_REQUEST);
		}
		Estate e = estateService.findOne(estateId); // nekretnina

		if (e == null)
			return new ResponseEntity<EstateDTO>(new EstateDTO(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<EstateDTO>(new EstateDTO(e), HttpStatus.OK);
	}

	/**
	 * <p>
	 * Get estate by user method-> get api/estate/user
	 * </p>
	 * 
	 * @param principal
	 *            , user data
	 * @return List<EstateDTO>
	 * 
	 * @author sirko
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<EstateDTO>> getByUser(Principal principal) {

		if (principal == null) {
			return new ResponseEntity<>(new ArrayList<EstateDTO>(), HttpStatus.BAD_REQUEST);
		}
		Advertiser owner = (Advertiser) userService.findByUsername(principal.getName());
		List<Estate> estates = estateService.findAllByOwnerId(owner); // nekretnina

		if (estates == null)
			return new ResponseEntity<>(new ArrayList<EstateDTO>(), HttpStatus.NOT_FOUND);
		List<EstateDTO> result = new ArrayList<EstateDTO>();
		for (Estate e : estates) {
			result.add(new EstateDTO(e));
		}
		return new ResponseEntity<List<EstateDTO>>(result, HttpStatus.OK);
	}
}
