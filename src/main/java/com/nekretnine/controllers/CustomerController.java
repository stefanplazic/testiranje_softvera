package com.nekretnine.controllers;

import java.security.Principal;
import java.util.ArrayList;
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

import com.nekretnine.dto.CustomerDTO;
import com.nekretnine.dto.CustomerMessageDTO;
import com.nekretnine.dto.EstateDTO;
import com.nekretnine.dto.PageableDTO;
import com.nekretnine.dto.ResponseDTO;
import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Customer;
import com.nekretnine.models.Estate;
import com.nekretnine.models.Favourites;
import com.nekretnine.models.Notification;
import com.nekretnine.models.User;
import com.nekretnine.services.AccountService;
import com.nekretnine.services.AdvertisementService;
import com.nekretnine.services.AdvertiserService;
import com.nekretnine.services.CustomerService;
import com.nekretnine.services.EstateService;
import com.nekretnine.services.FavouritesService;
import com.nekretnine.services.NotificationService;
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

	@Autowired
	private NotificationService notificationService;

	/**
	 * <p>
	 * get data about given custemer , return Ok status if exista,otherwise it returns not found
	 * <p>
	 * @author stefan
	 * @param id id of Customer you want to get data about
	 * @return  CustomerDTO
	 * @see CustomerDTO
	 */
	@RequestMapping(value = "customerProfile/{id}", method = RequestMethod.GET)
	public ResponseEntity<CustomerDTO> customerProfile(@PathVariable Long id) {
		Customer customer = service.findOne(id);
		if (customer == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(new CustomerDTO(customer), HttpStatus.OK);
	}

	/**
	 * this method is only for customer to seee his own profile, username is take from principal
	 * @param principal user data
	 * @return CustomerDTOC
	 * @author stefan
	 * @see CustomerDTO
	 */
	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ResponseEntity<CustomerDTO> myProfile(Principal principal) {

		// get my user credentials
		User me = userService.findByUsername(principal.getName());
		CustomerDTO customerDTO = new CustomerDTO((Customer) me);

		return new ResponseEntity<>(customerDTO, HttpStatus.OK);
	}

	/**
	 * return list of estates witch custemer had bought. It uses pagination
	 * @param principal
	 * @param pageableDTO
	 * @return List of EsateDto
	 * @see EstateDTO
	 * @see PageableDTO
	 * @author stefan
	 */
	@RequestMapping(value = "/myEstates", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<EstateDTO>> myEstates(Principal principal,
			@RequestBody PageableDTO pageableDTO) {

		Customer customer = (Customer) userService.findByUsername(principal
				.getName());

		Page<Advertisement> page = advertisementService.findAllBySoldto(
				customer,
				new PageRequest(pageableDTO.getPage(), pageableDTO.getCount()));
		List<Advertisement> adv = page.getContent();
		List<EstateDTO> estates = new ArrayList<>();
		for (Advertisement a : adv) {
			estates.add(new EstateDTO(a.getEstate()));
		}
		return new ResponseEntity<>(estates, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/myEstates/size", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> myEstates(Principal principal) {

		Customer customer = (Customer) userService.findByUsername(principal
				.getName());

		int size = advertisementService.findBySoldto(customer);
		
		return new ResponseEntity<>(new ResponseDTO(""+size), HttpStatus.OK);
	}

	/**
	 * Method add estate to customer favorites
	 * 
	 * @param principal
	 *            Current logged user (customer) created by Spring Security.
	 * @param estate_id
	 *            The id of estate which will add to customer's favorites
	 * @return If estate with passed id doesn't exists return string
	 *         "Estate not found" and HttpStatus NOT_FOUND. If estate already in
	 *         customer favorites return appropriate message and HttpStatus
	 *         CONFLICT. Otherwise, return string "Favorite estate is saved" and
	 *         HttpStatus OK.
	 * @author Miodrag Vilotijević
	 */
	@RequestMapping(value = "/addFavourite/{estateId}", method = RequestMethod.POST)
	public ResponseEntity<String> addFavourite(Principal principal,
			@PathVariable Long estateId) {

		Customer customer = (Customer) userService.findByUsername(principal
				.getName());
		Estate estate = estateService.findOne(estateId);
		if (estate == null) {
			return new ResponseEntity<>("Estate not found",
					HttpStatus.NOT_FOUND);
		}

		Favourites favourite = favouritesService.findByEstateAndCustomer(
				estate, customer);
		if (favourite != null) {
			return new ResponseEntity<>(
					"Estate is already in customers favourites",
					HttpStatus.CONFLICT);
		}

		favourite = new Favourites();
		favourite.setCustomer(customer);
		favourite.setEstate(estate);
		favouritesService.save(favourite);
		return new ResponseEntity<>("Favourite Estate is saved", HttpStatus.OK);
	}

	/**
	 * Method remove estate from customer favorites
	 * 
	 * @param principal
	 *            Current logged user (customer), created by Spring Security.
	 * @param estate_id
	 *            The id of estate which will remove from customer's favorites
	 * @return If estate with passed id doesn't exists or isn't in customers
	 *         favorites method return HttpStatus NOT_FOUND with appropriate
	 *         message. Otherwise, return string "Estate is unmarked" and
	 *         HttpStatus OK.
	 * @author Miodrag Vilotijević
	 */
	@RequestMapping(value = "/unmarkFavourite/{estateId}", method = RequestMethod.POST)
	public ResponseEntity<String> unmarkFavourite(Principal principal,
			@PathVariable Long estateId) {

		Customer customer = (Customer) userService.findByUsername(principal
				.getName());
		Estate estate = estateService.findOne(estateId);
		if (estate == null) {
			return new ResponseEntity<>("Estate not found",
					HttpStatus.NOT_FOUND);
		}

		Favourites favourite = favouritesService.findByEstateAndCustomer(
				estate, customer);
		if (favourite == null) {
			return new ResponseEntity<>(
					"This estate isn't in customers favourites",
					HttpStatus.NOT_FOUND);
		}
		favouritesService.delete(favourite.getId());
		return new ResponseEntity<>("Estate is unmarked", HttpStatus.OK);
	}

	/**
	 * Method return customer's favorites estates for concrete page.
	 * 
	 * @param principal
	 *            Current logged user (customer), created by Spring Security.
	 * @param pageableDTO
	 *            JSON object which contains data for paging customer's
	 *            favorites estates. Example: @{"page"=1, count="10"}
	 * @return List of EstateDTO objects which represent customer's favorites
	 *         estates and HttpStatus OK.
	 * @see PageableDTO, EstateDTO
	 * @author Miodrag Vilotijević
	 */
	@RequestMapping(value = "/getFavourites", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<EstateDTO>> getFavourites(Principal principal,
			@RequestBody PageableDTO pageableDTO) {

		Customer customer = (Customer) userService.findByUsername(principal
				.getName());
		Page<Favourites> page = favouritesService.findAllByCustomer(customer,
				new PageRequest(pageableDTO.getPage(), pageableDTO.getCount()));
		List<Favourites> favourites = page.getContent();
		List<EstateDTO> estates = new ArrayList<>();
		for (Favourites f : favourites) {
			estates.add(new EstateDTO(f.getEstate()));
		}
		return new ResponseEntity<>(estates, HttpStatus.OK);
	}

	/**
	 * Method send message to advertiser for concrete advertisement.
	 * 
	 * @param principal
	 *            Current logged user (customer), created by Spring Security.
	 * @param messageDTO
	 *            JSON object which contains data for sending message to
	 *            advertiser Example: @{"message":"Hello", "advertisementId":2}
	 * @return If advertisement with passed id doesn't exists method return
	 *         string "Advertisement not found" and HttpStatus NOT_FOUND.
	 *         Otherwise, return string "Message is sent to advertiser" and
	 *         HttpStatus OK.
	 * @see CustomerMessageDTO
	 * @author Miodrag Vilotijević
	 */
	@RequestMapping(value = "/sendMessageToAdvertiser", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> sendMessageToAdvertiser(Principal principal,
			@RequestBody CustomerMessageDTO messageDTO) {
		Customer fromUser = (Customer) userService.findByUsername(principal
				.getName());
		Advertisement advertisement = advertisementService.findOne(messageDTO
				.getAdvertisementId());
		if (advertisement == null) {
			return new ResponseEntity<>("Advertisement not found",
					HttpStatus.NOT_FOUND);
		}
		Notification notification = new Notification();
		notification.setFromUser(fromUser);
		notification.setToUser(advertisement.getAdvertiser());
		notification.setnType("message");
		notification.setText(messageDTO.getMessage());
		notification.setAdvertisement(advertisement);
		notificationService.saveNotification(notification);
		return new ResponseEntity<>("Message is sent to advertiser",
				HttpStatus.OK);
	}

}
