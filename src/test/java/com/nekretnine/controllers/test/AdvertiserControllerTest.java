package com.nekretnine.controllers.test;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.Thread.State;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.nekretnine.MyprojectApplication;
import com.nekretnine.TestUtil;
import com.nekretnine.constants.AdvertismentConstants;
import com.nekretnine.constants.CallToCompanyConstants;
import com.nekretnine.constants.CompanyConstants;
import com.nekretnine.constants.RateConstants;
import com.nekretnine.constants.UserConstants;
import com.nekretnine.dto.AdvertiserDTO;
import com.nekretnine.dto.AdvertiserMessageDTO;
import com.nekretnine.dto.CallToCompanyDTO;
import com.nekretnine.dto.CompanyDTO;
import com.nekretnine.dto.RateDTO;
import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.CallToCompany;
import com.nekretnine.models.Company;
import com.nekretnine.models.RateAdvertiser;
import com.nekretnine.models.User;
import com.nekretnine.services.AdvertisementService;
import com.nekretnine.services.CallToCompanyService;
import com.nekretnine.services.CompanyService;
import com.nekretnine.services.UserService;
import com.sun.security.auth.UserPrincipal;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
// @Sql(executionPhase =
// ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:insert.sql")
public class AdvertiserControllerTest {

	private static final String URL_PREFIX = "/api/advertiser";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserService service;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CallToCompanyService calltoService;

	@Autowired
	private AdvertisementService advertisementService;

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	public AdvertiserControllerTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * test the method for  geting data about adveriser. If correct id is send, it return OK status,
	 * otherwise it returns NOT_FOUND.
	 * <p>
	 * @throws Exception
	 * @author stefan plazic
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAdvertiserProfile() throws Exception {

		User user = service.findByUsername(UserConstants.USERNAME_SECOND);
		mockMvc.perform(get(URL_PREFIX + "/profile/" + user.getId())
				.principal(new UserPrincipal(UserConstants.USERNAME_SECOND))).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.firstName").value(UserConstants.FIRST_NAME_SECOND))
				.andExpect(jsonPath("$.lastName").value(UserConstants.LAST_NAME_SECOND))
				.andExpect(jsonPath("$.username").value(UserConstants.USERNAME_SECOND))
				.andExpect(jsonPath("$.email").value(UserConstants.EMAIL_SECOND));
		// test this on id wich doesnt exists
		mockMvc.perform(
				get(URL_PREFIX + "/profile/" + 1000).principal(new UserPrincipal(UserConstants.USERNAME_SECOND)))
				.andExpect(status().isNotFound());
	}

	/**
	 * <p>
	 * returns advertiser profile, here you need to send just prinicipal, and using this it returns
	 * user data and OK status.
	 * <p>
	 * @throws Exception
	 * @author stefan plazic
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testGetMyProfile() throws Exception {
		Company company = companyService.findOneByNameAndAddress("Some Adress", "StefiCompany");
		User user = service.findByUsername(UserConstants.USERNAME);
		mockMvc.perform(get(URL_PREFIX + "/myprofile").principal(new UserPrincipal(UserConstants.USERNAME_SECOND)))
				.andExpect(status().isOk());
	}
	/**
	 * test the method for calling advertiser to work in the company
	 * it uses AdvertiserDTO to to call advertiser to company.
	 * 1. first we give all correct parameters and OK status is returned.
	 * 2. advertiser is not company empoyer , it returns NOT FOund
	 * 3. sending of none existing advertiser , returns NOt FOund
	 * @throws Exception
	 * @author stefan plazic
	 * @see AdvertiserDTO
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testAddToCompany() throws Exception {
		Advertiser user = (Advertiser) service.findByUsername(UserConstants.USERNAME_ADVETISER);
		String json = TestUtil.json(new AdvertiserDTO(user));
		mockMvc.perform(post(URL_PREFIX + "/callToCompany").principal(new UserPrincipal(UserConstants.USERNAME_SECOND))
				.contentType(contentType).content(json)).andExpect(status().isOk());
		// unemployed adveriser send call to company, it should return NOT_FOUND
		// status code
		user = (Advertiser) service.findByUsername(UserConstants.USERNAME_SECOND);
		json = TestUtil.json(new AdvertiserDTO(user));
		mockMvc.perform(post(URL_PREFIX + "/callToCompany")
				.principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER)).contentType(contentType).content(json))
				.andExpect(status().isNotFound());
		// send not existing Advertiser to method, it should return NOT_FOUND
		// status code
		AdvertiserDTO advertiserDTO = new AdvertiserDTO();
		advertiserDTO.setUsername(UserConstants.UN_EXISTING_ADVERTISER_USERNAME);
		json = TestUtil.json(advertiserDTO);
		mockMvc.perform(post(URL_PREFIX + "/callToCompany")
				.principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER)).contentType(contentType).content(json))
				.andExpect(status().isNotFound());
	}

	/**
	 * if user does work in company will return ok status,
	 * otherwise it will return NOT FOUND status
	 * @throws Exception
	 * @author stefan plazic
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testGetCompany() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/getCompany").principal(new UserPrincipal(UserConstants.USERNAME_SECOND)))
				.andExpect(status().isOk());
		// get company for user who doesn't work in company

		mockMvc.perform(get(URL_PREFIX + "/getCompany").principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER)))
				.andExpect(status().isNotFound());

	}

	/**
	 * <p>
	 * 1. in this case user can acceptCall to work in company in which is invited
	 * 2. in this case user tries to accept company even if he is working in some other company, returns CONFLICT
	 * <p>
	 * @throws Exception
	 * @author stefan plazic
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void acceptCallTOCompany() throws Exception {

		// get the company id.
		CallToCompany callToCompany = calltoService.findOne(CallToCompanyConstants.CALL_ONE_ID);
		String json = TestUtil.json(new CallToCompanyDTO(callToCompany));
		mockMvc.perform(post(URL_PREFIX + "/acceptCall").principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER))
				.contentType(contentType).content(json)).andExpect(status().isOk());

		// try to accept call to company even if you are working in some company
		// . status code conflict
		CallToCompany callToCompany2 = new CallToCompany();
		callToCompany2.setId(CallToCompanyConstants.CALL_ONE_ID);
		json = TestUtil.json(callToCompany2);
		mockMvc.perform(post(URL_PREFIX + "/acceptCall").principal(new UserPrincipal(UserConstants.USERNAME_SECOND))
				.contentType(contentType).content(json)).andExpect(status().isConflict());

	}

	/**
	 * <p>
	 * test method for returning all company call for some user.
	 * in first case he has list with company call with size of CALL_SIZE
	 * in second case he has no company calls
	 * <p>
	 * @author stefan plazic
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testAllCallTOCompany() throws Exception {

		mockMvc.perform(get(URL_PREFIX + "//allCalls").principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER)))
				.andExpect(status().isFound()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(CallToCompanyConstants.CALL_SIZE)));
		// advertiser who doesn't have any companycalls ,should have size 0
		mockMvc.perform(get(URL_PREFIX + "//allCalls").principal(new UserPrincipal(UserConstants.USERNAME_SECOND)))
				.andExpect(status().isFound()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(CallToCompanyConstants.NONE_CALL_SIZE)));
	}
	/**
	 * test does the size of unemployed users equals to ADVERT_SIZE constant
	 * @author stefan plazic
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testGetUnemployed() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "//unemployed").principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER)))
				.andExpect(status().isFound()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(CallToCompanyConstants.ADVERT_SIZE)));

	}
	/**test method for showing advertiser list of all advertisments he has sold
	 * first the two advertisment are saved with SOLD state to database, after that we call the API "api/advertiser/soldEstates"
	 * to check does the list of sold estates contains previusly sold states 
	 * @author stefan plazic
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testGetSoldEstates() throws Exception {
		// change the status advertisment
		Advertisement advertisement = advertisementService.findOne(CallToCompanyConstants.ADVERISMENT_ID_FIRST);
		advertisement.setState(Advertisement.State.SOLD);
		advertisementService.save(advertisement);

		advertisement = advertisementService.findOne(CallToCompanyConstants.ADVERISMENT_ID_SECOND);
		advertisement.setState(Advertisement.State.SOLD);
		advertisementService.save(advertisement);

		mockMvc.perform(get(URL_PREFIX + "/soldEstates").principal(new UserPrincipal(UserConstants.USERNAME_SECOND)))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(CallToCompanyConstants.SOLD_ESTATES_STEFAN)));
		// testing stefi
		mockMvc.perform(
				get(URL_PREFIX + "/soldEstates").principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER_TWO)))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(CallToCompanyConstants.SOLD_ESTATES_STEFI)));

	}

	/**
	 * test the rating of adveriser 
	 * @throws Exception
	 * @author stefan
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testSet_rate() throws Exception {

		RateDTO rate = new RateDTO();
		rate.setRate(RateConstants.RATE_ONE);
		String json = TestUtil.json(rate);
		// testing for user with id 2
		mockMvc.perform(post(URL_PREFIX + "/rate/" + UserConstants.ADVERT_FIRST)
				.principal(new UserPrincipal(UserConstants.CUSTOMER_MILOS)).contentType(contentType)
				.content(json))
				.andExpect(status().isCreated());
	}
	/**
	 * <p>
	 * 1. returns NOt FOUnd because given customer doesn't exists
	 * 2.returns NOt FOUnd given advertisement doesn't exists
	 * 3. return OK 
	 * <p>
	 * @throws Exception
	 * @author stefan
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testSendMessageToCustomer() throws Exception {
	
		// this test should return NOT_FOUND because user doesn't exists
		AdvertiserMessageDTO dto = new AdvertiserMessageDTO();
		dto.setAdvertisementId(AdvertismentConstants.ADVERTISMENT_ID);
		dto.setToUserId(UserConstants.UN_EXISTING_ADVERTISER_ID);
		String json = TestUtil.json(dto);
		mockMvc.perform(post(URL_PREFIX + "sendMessageToCustomer")
				.principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER_TWO)).content(json)
				.contentType(contentType))
				.andExpect(status().isNotFound());
		
		//in this case should return NOT_FOUND because advertisement doesn't exists
		dto = new AdvertiserMessageDTO();
		dto.setAdvertisementId(AdvertismentConstants.ADVERTISMENT_ID_NONE_EXISTING);
		dto.setToUserId(UserConstants.USERNAME_CUSTOMER_MILOS);
		json = TestUtil.json(dto);
		mockMvc.perform(post(URL_PREFIX + "sendMessageToCustomer")
				.principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER_TWO)).content(json)
				.contentType(contentType))
				.andExpect(status().isNotFound());
		
		//should return OK 
		dto = new AdvertiserMessageDTO();
		dto.setAdvertisementId(AdvertismentConstants.ADVERTISMENT_ID);//2
		dto.setToUserId(UserConstants.USERNAME_CUSTOMER_MILOS);//3
		json = TestUtil.json(dto);
		mockMvc.perform(post(URL_PREFIX + "sendMessageToCustomer")
				.principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER_TWO)).contentType(contentType)
				.content(json)
				)//stefi
				.andExpect(status().isOk());
	}
	/**
	 * <p>
	 * sending request for company in case where  requester is already a emploied in other company
	 * returns Conlfict
	 * <p>
	 * @author stefan
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testSendRequestForCompany() throws Exception {
		
		//case company owner is already an employe , returns CONFLICT
	
		String json = TestUtil.json(new CompanyDTO());
		// testing for user with id 2
		mockMvc.perform(post(URL_PREFIX + "/sendRequestForCompany")
				.principal(new UserPrincipal(UserConstants.USERNAME_SECOND)).content(json)
				.contentType(contentType))
				.andExpect(status().isConflict());
		
	}
	/**
	 * <p>
	 * testing removing user from company
	 * 1 everytin is good- returns OK
	 * 2 advetier to be remove doesn't exists - returns NOT FOUND
	 * 3 company from to be removed doesn't exists - returns NOT FOUND
	 * 4 a none owner of company trying to delete someone - returns I am a teapot.
	 * <p>
	 * @author stefan
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testFire_from_company() throws Exception{
		
		mockMvc.perform(delete(URL_PREFIX + "/removeFromCompany/"+UserConstants.ID_2+"/"+CompanyConstants.COMPANY_ID_FIRST)
				.principal(new UserPrincipal(UserConstants.USERNAME_SECOND)))
				.andExpect(status().isOk());
		
		//advetier id doesn't exists - returns NOT FOUND
		mockMvc.perform(delete(URL_PREFIX + "/removeFromCompany/"+UserConstants.ID_NOt+"/"+CompanyConstants.COMPANY_ID_FIRST)
				.principal(new UserPrincipal(UserConstants.USERNAME_SECOND)))
				.andExpect(status().isNotFound());
		
		//company id doesn't exists - returns NOT FOUND
				mockMvc.perform(delete(URL_PREFIX + "/removeFromCompany/"+UserConstants.ID_2+"/"+CompanyConstants.COMPANY_ID_NOT)
						.principal(new UserPrincipal(UserConstants.USERNAME_SECOND)))
						.andExpect(status().isNotFound());
				
	 //not menagery of company tea pot
				mockMvc.perform(delete(URL_PREFIX + "/removeFromCompany/"+UserConstants.ID_2+"/"+CompanyConstants.COMPANY_ID_FIRST)
						.principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER_TWO)))
						.andExpect(status().isIAmATeapot());		
	}
}

