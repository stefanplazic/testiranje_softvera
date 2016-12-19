package com.nekretnine.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.AdvertEstateDTO;
import com.nekretnine.dto.AdvertisementDTO;
import com.nekretnine.models.Advertisement;
import com.nekretnine.services.AdvertisementService;

@RestController
@RequestMapping(value = "api/advertisement")
public class AdvertisementController {

	@Autowired
	private AdvertisementService advertisementService;

	// za oglasivaca
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> add_advertisement(@RequestBody AdvertisementDTO advertisementDTO) {

		Advertisement a = new Advertisement(advertisementDTO);
		advertisementService.save(a);

		return new ResponseEntity<String>("aloebebebebe", HttpStatus.OK);
	}

	// za oglasivaca
	@RequestMapping(value = "/modify/{advert_id}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> modify_addvertisement(@PathVariable Long advert_id,
			@RequestBody AdvertisementDTO advertDTO) {

		Advertisement adv = advertisementService.findOne(advert_id);

		// da li reklama postoji
		if (adv == null)
			return new ResponseEntity<String>("nemanema", HttpStatus.OK);
		// da li je oglasivac okacio reklamu
		if (adv.getId() == advertDTO.getAdvertiser().getId())
			new ResponseEntity<String>("nemanema", HttpStatus.OK);

		// apdejt status reklame ako je prosledjen
		if (advertDTO.getState() != null)
			advertisementService.setState(advertDTO.getState(), advert_id);
		// apdejt vlasnika reklame ako je prosledjen i ako je nov vlasnik njegov
		// kola(iz iste kompanije)

		return new ResponseEntity<String>("braobrao", HttpStatus.OK);
	}

	/**
	 * localhost:8080/api/advertisement?name=cone&price=123&area=55&address=sumadijska 62&city=uzice&cityPart=belo groblje&technicalEquipment&heatingSystem=podno grijanje&publicationDate=2016-12-12&expiryDate=2016-10-10
	 * @param search criteria provided by user which the method uses to find specific Advertisement / Estate
	 * @return List of AdvertEstateDTOs that fit the search criteria provided by user 
	 * @see AdvertEstateDTO, Advertisement, Estate
	 */
	@RequestMapping(method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<List<AdvertEstateDTO>> getAllAdvertisements(@RequestParam("name") String name,
			@RequestParam("price") String price, @RequestParam("area") String area, 
			@RequestParam("address") String address, @RequestParam("city") String city,
			@RequestParam("cityPart") String cityPart, @RequestParam("technicalEquipment") String technicalEquipment,
			@RequestParam("heatingSystem") String heatingSystem, @RequestParam("publicationDate") String publicationDate,
			@RequestParam("expiryDate") String expiryDate) throws ParseException {

		String query = "select a.id, a.estate, e.name, e.price, e.area, e.address, e.city, e.city_part, e.technical_equipment, "
				+ "e.heating_system, a.publication_date, a.expiry_date from advertisement a join estate e on a.estate = e.id where";
		
		if(name != null && !name.equals("")) {
			query += " name='"+name+"'";
		}
		if(price != null && !price.equals("")) {
			query += " price='"+price+"'";
		}
		if(area != null && !area.equals("")) {
			query += " area="+area+"'";
		}
		if(address != null && !address.equals("")) {
			query += " address='"+address+"'";
		}
		if(city != null && !city.equals("")) {
			query += " city='"+city+"'";
		}
		if(cityPart != null && !cityPart.equals("")) {
			query += " city_part='"+cityPart+"'";
		}
		if(technicalEquipment != null && !technicalEquipment.equals("")) {
			query += " technical_equipment=''"+technicalEquipment+"'";
		}
		if(heatingSystem != null && !heatingSystem.equals("")) {
			query += " e.heating_system='"+heatingSystem+"'";
		}
		if(publicationDate != null && !publicationDate.equals("")) {
			query += " publication_date='"+publicationDate+"'";
		}
		if(expiryDate != null && !expiryDate.equals("")) {
			query += " expiry_date='"+expiryDate+"'";
		}
		
		List<AdvertEstateDTO> adv = advertisementService.findAdvertisements(query);
		
		//if there are no advert-estate pairs that fit the search criteria
		if(adv.size() == 0) {
			return new ResponseEntity<List<AdvertEstateDTO>>(adv, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<AdvertEstateDTO>>(adv, HttpStatus.OK);
	}

}
