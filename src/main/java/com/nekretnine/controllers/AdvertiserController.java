package com.nekretnine.controllers;

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
import com.nekretnine.dto.AdvertiserMessageDTO;
import com.nekretnine.dto.CallToCompanyDTO;
import com.nekretnine.dto.CompanyDTO;
import com.nekretnine.dto.CustomerMessageDTO;
import com.nekretnine.dto.EstateDTO;
import com.nekretnine.dto.RateDTO;
import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertisement.State;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.CallToCompany;
import com.nekretnine.models.Company;
import com.nekretnine.models.Customer;
import com.nekretnine.models.Notification;
import com.nekretnine.models.RateAdvertiser;
import com.nekretnine.models.User;
import com.nekretnine.services.AdvertisementService;
import com.nekretnine.services.AdvertiserService;
import com.nekretnine.services.CallToCompanyService;
import com.nekretnine.services.CompanyService;
import com.nekretnine.services.CustomerService;
import com.nekretnine.services.NotificationService;
import com.nekretnine.services.RateAdvertiserService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value = "api/advertiser")
public class AdvertiserController {

	@Autowired
	private AdvertiserService service;

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CallToCompanyService callService;

	@Autowired
	private RateAdvertiserService rateService;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CustomerService customerService;

	/**
	 * 
	 * @param id
	 *            of advertiser who's profile we want to get data about
	 * @return returns AdvertiserDTO
	 * @author stefan plazic
	 */
	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
	public ResponseEntity<AdvertiserDTO> getAdvertiserProfile(@PathVariable Long id) {
		Advertiser advertiser = service.findOne(id);
		if (advertiser == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		AdvertiserDTO advertiserDTO = new AdvertiserDTO(advertiser);

		return new ResponseEntity<>(advertiserDTO, HttpStatus.OK);
	}

	/**
	 * 
	 * @param principal
	 *            contains user credentials (part of Spring security)
	 * @return returns AdvertiserDTO contains user data
	 * @author stefan plazic
	 */
	@RequestMapping(value = "/myprofile", method = RequestMethod.GET)
	public ResponseEntity<AdvertiserDTO> getMyProfile(Principal principal) {

		// get my user credentials
		User me = userService.findByUsername(principal.getName());
		AdvertiserDTO advertiserDTO = new AdvertiserDTO((Advertiser) me);

		return new ResponseEntity<>(advertiserDTO, HttpStatus.OK);
	}

	/**
	 * This method shall call advertiser to stark working into company
	 * 
	 * @param advertiserDTO
	 *            potencial worker for our company
	 * @param principal
	 *            data about user from Spring security
	 * @author stefan plazic
	 * @return returns String to notify user about task completion
	 */
	@RequestMapping(value = "/callToCompany", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> addToCompany(@RequestBody AdvertiserDTO advertiserDTO, Principal principal) {
		User user = userService.findByUsername(advertiserDTO.getUsername());
		if (user == null || !(user instanceof Advertiser))
			return new ResponseEntity<>("Ther's not such advertiser", HttpStatus.NOT_FOUND);
		// get the username of advertiser from token
		Advertiser me = (Advertiser) userService.findByUsername(principal.getName());
		if (me.getCompany() == null) {
			return new ResponseEntity<>("Advertiser doesn't work in any company", HttpStatus.NOT_FOUND);
		}

		CallToCompany callToCompany = new CallToCompany();
		callToCompany.setFromAdvertiser(me);
		callToCompany.setToAdvertiser((Advertiser) user);
		callToCompany.setDateOfCall(new Date());
		// save call to company
		callService.save(callToCompany);

		return new ResponseEntity<>("Request send", HttpStatus.OK);
	}

	/**
	 * Method check is the advertiser employed at company.
	 * 
	 * @param principal
	 *            Current logged user (advertiser), created by Spring Security.
	 * @return If advertiser is employed for any company, return true with
	 *         HttpStatus OK. Otherwise, return false with HttpStatus OK.
	 * @author Miodrag Vilotijević
	 */
	@RequestMapping(value = "/isEmployee", method = RequestMethod.GET)
	public ResponseEntity<Boolean> isEmployee(Principal principal) {
		Advertiser me = (Advertiser) userService.findByUsername(principal.getName());
		if (me.getCompany() == null) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	/**
	 * Method return the company in which advertiser is employed.
	 * 
	 * @param principal
	 *            Current logged user (advertiser), created by Spring Security.
	 * @return If advertiser is employed return JSON object which represent
	 *         advertiser's company with HttpStatus OK. Otherwise, return
	 *         HttpStatus NOT_FOUND.
	 * @see CompanyDTO
	 * @author Miodrag Vilotijević
	 */
	@RequestMapping(value = "/getCompany", method = RequestMethod.GET)
	public ResponseEntity<CompanyDTO> getCompany(Principal principal) {
		Advertiser me = (Advertiser) userService.findByUsername(principal.getName());
		if (me.getCompany() == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		CompanyDTO companyDTO = new CompanyDTO(me.getCompany());
		companyDTO.setOwner(new AdvertiserDTO(me));
		return new ResponseEntity<>(companyDTO, HttpStatus.OK);

	}

	/**
	 * <p>
	 * This method allows advetiser to accept company Call - to start working in
	 * some company.
	 * <p>
	 * 
	 * @param callToCompanyDTO
	 *            type of CallToCompanyDTO - represents companyCall in which
	 *            user wants to work
	 * @param principal
	 *            principal contains user credentials (part of Spring security)
	 * @author stefan plazic
	 * @see CallToCompanyDTO
	 * @return
	 */
	@RequestMapping(value = "/acceptCall", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> acceptCallTOCompany(@RequestBody CallToCompanyDTO callToCompanyDTO,
			Principal principal) {

		// get the username of advertiser from token
		Advertiser me = (Advertiser) userService.findByUsername(principal.getName());
		if (me.getCompany() != null) {
			return new ResponseEntity<>("Advertiser is already working in some company", HttpStatus.CONFLICT);
		}
		// get all data about sender advertiser
		CallToCompany callToCompany = callService.findOne(callToCompanyDTO.getId());
		// check if call to company exists at all
		if (callToCompany == null)
			return new ResponseEntity<>("Such call doesn't exists at all", HttpStatus.NOT_FOUND);
		Advertiser sender = callToCompany.getFromAdvertiser();
		Company company = sender.getCompany();
		// register new advertiser as company employer
		me.setCompany(company);
		service.save(me);

		// delete all request for qiven user
		List<CallToCompany> companies = callService.findByToadvrt(me);
		for (CallToCompany callToCompan : companies) {
			callService.remove(callToCompan.getId());
		}
		return new ResponseEntity<>("Congretulate you are company employee!!", HttpStatus.OK);
	}

	/**
	 * <p>
	 * Allows advertiser to get all offers for company working.
	 * <p>
	 * 
	 * @param principal
	 *            contains user credentials (part of Spring security)
	 * @return List of CallToCompanyDTO
	 * @author stefan plazic
	 * @see CallToCompanyDTO
	 */
	@RequestMapping(value = "/allCalls", method = RequestMethod.GET)
	public ResponseEntity<List<CallToCompanyDTO>> allCallTOCompany(Principal principal) {

		// get the username of advertiser from token
		Advertiser me = (Advertiser) userService.findByUsername(principal.getName());

		// get all call from company
		List<CallToCompanyDTO> callToCompaniesDTO = new ArrayList<>();
		List<CallToCompany> companies = callService.findByToadvrt(me);
		for (CallToCompany callToCompan : companies) {
			callToCompaniesDTO.add(new CallToCompanyDTO(callToCompan));
		}
		return new ResponseEntity<>(callToCompaniesDTO, HttpStatus.FOUND);
	}

	/**
	 * <p>
	 * This method will return a list of all advertisers which currently doesn't
	 * work in any company.
	 * <p>
	 * 
	 * @return returns List of AdvertiserDTO
	 * @see AdvertiserDTO
	 * @author stefan plazic
	 */
	@RequestMapping(value = "/unemployed", method = RequestMethod.GET)
	public ResponseEntity<List<AdvertiserDTO>> getUnemployed(Principal principal) {

		// put all unemployed users in this list
		List<AdvertiserDTO> advertiserDTOs = new ArrayList<>();

		// get all advertisers
		List<Advertiser> advertisers = service.findAll();
		for (Advertiser advrt : advertisers) {
			// find only them with out company
			if (advrt.getCompany() == null)
				advertiserDTOs.add(new AdvertiserDTO(advrt));
		}

		return new ResponseEntity<>(advertiserDTOs, HttpStatus.FOUND);
	}

	/**
	 * <p>
	 * Allows advertiser to search all estates that he had sold
	 * <p>
	 * 
	 * @param principal
	 *            contains user credentials (part of Spring security)
	 * @return returns list of sold estates
	 * @see EstateDTO
	 * @author stefan plazic
	 */
	@RequestMapping(value = "/soldEstates", method = RequestMethod.GET)
	public ResponseEntity<List<EstateDTO>> getSoldEstates(Principal principal) {

		// get my user credentials
		User me = userService.findByUsername(principal.getName());
		Advertiser advertiser = (Advertiser) me;

		List<EstateDTO> estateDTOs = new ArrayList<>();
		// get estates from advertisement and convert them to DTOs
		for (Advertisement advertisement : advertiser.getAdvertisements()) {
			if (advertisement.getState() == State.RENTED || advertisement.getState() == State.SOLD)
				estateDTOs.add(new EstateDTO(advertisement.getEstate()));
		}
		return new ResponseEntity<>(estateDTOs, HttpStatus.OK);
	}

	// za kupca
	@RequestMapping(value = "/rate/{advertiserId}", method = RequestMethod.POST)
	public ResponseEntity<String> setRate(Principal principal, @RequestBody RateDTO rateDTO,
			@PathVariable Long advertiserId) {
		Advertiser a = (Advertiser) userService.findOne(advertiserId); // oglasavac
		Customer c = (Customer) userService.findByUsername(principal.getName());// kupac

		// da li oglasavac postoji
		if (a == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		// da li je vec rejtovao
		RateAdvertiser jel = rateService.alreadyRated(a, c);
		if (jel != null)
			return new ResponseEntity<>("vec si rejtovao", HttpStatus.NOT_FOUND);

		// kreiranje rate-advertiser objekta
		RateAdvertiser ra = new RateAdvertiser();
		ra.setAdvertRate(rateDTO.getRate());
		ra.setAdvertiserRate(a);
		ra.setCustomAdv(c);

		// sve to u bazu
		rateService.save(ra);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * Method send message(response about advertisement) to customer.
	 * 
	 * @param principal
	 *            Current logged user (advertiser), created by Spring Security.
	 * @param messageDTO
	 *            JSON object which contains data for sending message to
	 *            customer. Example: @{"message":"Hello", "advertisementId":2,
	 *            "toUserId":1}
	 * @return If advertisement or customer doesn't exists, return appropriate
	 *         failed message and HttpStatus code, otherwise return success
	 *         message and HttpStatus OK.
	 * @see CustomerMessageDTO
	 * @author Miodrag Vilotijević
	 */
	@RequestMapping(value = "/sendMessageToCustomer", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> sendMessageToCustomer(Principal principal,
			@RequestBody AdvertiserMessageDTO messageDTO) {
		Advertiser fromUser = (Advertiser) userService.findByUsername(principal.getName());
		Advertisement advertisement = advertisementService.findOne(messageDTO.getAdvertisementId());

		if (advertisement == null) {
			return new ResponseEntity<>("Advertisement not found", HttpStatus.NOT_FOUND);
		}

		if (fromUser.getId() != advertisement.getAdvertiser().getId()) {
			return new ResponseEntity<>("You are not advertiser on passed advertisement", HttpStatus.CONFLICT);
		}

		Customer toUser = customerService.findOne(messageDTO.getToUserId());
		if (toUser == null) {
			return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
		}

		Notification notification = new Notification();
		notification.setFromUser(fromUser);
		notification.setToUser(toUser);
		notification.setnType("message");
		notification.setText(messageDTO.getMessage());
		notification.setAdvertisement(advertisement);
		notificationService.saveNotification(notification);
		return new ResponseEntity<>("Message is sent to customer", HttpStatus.OK);
	}

	/**
	 * Method send request for company to administrators for approval.
	 * 
	 * @param principal
	 *            Current logged user (advertiser), created by Spring Security.
	 * @param companyDTO
	 *            JSON object which contains data about company.
	 * @return If owner of the company which is passed in companyDTO is already
	 *         employee or company already exists, return appropriate message
	 *         and HttpStatus CONFLICT. Otherwise, return success message and
	 *         HttpStatus OK.
	 * @see CompanyDTO
	 * @author Miodrag Vilotijević
	 */
	@RequestMapping(value = "/sendRequestForCompany", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> sendRequestForCompany(Principal principal, @RequestBody CompanyDTO companyDTO) {

		Advertiser owner = (Advertiser) userService.findByUsername(principal.getName());

		if (owner.getCompany() != null) {
			return new ResponseEntity<>("Owner is already employee.", HttpStatus.CONFLICT);
		}

		Company company = companyService.findOneByNameAndAddress(companyDTO.getName(), companyDTO.getAddress());

		if (company == null) {
			Company com = new Company(companyDTO);
			com.setOwner(new Advertiser(owner));
			com = companyService.saveCompany(com);
			service.setAdvertisersCompany(com, owner.getId());
			return new ResponseEntity<>("The request for company has successfully added.", HttpStatus.OK);
		}
		return new ResponseEntity<>("The company with entered name and address already exists.",
				HttpStatus.CONFLICT);

	}

	@RequestMapping(value = "/removeFromCompany/{advertiserId}/{companyId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> fireFromCompany(Principal principal, @PathVariable Long advertiserId,
			@PathVariable Long companyId) {

		Advertiser a = (Advertiser) userService.findOne(advertiserId);
		Company c = companyService.findOne(companyId);
		Advertiser ma = (Advertiser) userService.findByUsername(principal.getName());

		if (a == null)
			return new ResponseEntity<>("oglasivac ne postoji", HttpStatus.NOT_FOUND);
		if (c == null)
			return new ResponseEntity<>("kompanija ne postoji", HttpStatus.NOT_FOUND);

		// ako nije menadzer
		if (ma.getId() != c.getOwner().getId())
			return new ResponseEntity<>("nisi menadzer kompanije", HttpStatus.I_AM_A_TEAPOT);

		// ako ne radi u toj kompaniji
		if (a.getCompany().getId() != c.getId())
			return new ResponseEntity<>("nemoze", HttpStatus.I_AM_A_TEAPOT);

		// brisi ga
		service.fire(advertiserId);

		return new ResponseEntity<>("sve kul", HttpStatus.OK);
	}

}
