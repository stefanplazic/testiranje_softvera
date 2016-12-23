package com.nekretnine.controllers.test;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.nekretnine.constants.CallToCompanyConstants;
import com.nekretnine.constants.UserConstants;
import com.nekretnine.dto.CallToCompanyDTO;
import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.CallToCompany;
import com.nekretnine.models.Company;
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

	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testAddToCompany() throws Exception {
		Advertiser user = (Advertiser) service.findByUsername(UserConstants.USERNAME_ADVETISER);
		String json = TestUtil.json(user);
		mockMvc.perform(post(URL_PREFIX + "/callToCompany").principal(new UserPrincipal(UserConstants.USERNAME_SECOND))
				.contentType(contentType).content(json)).andExpect(status().isOk());
		// test call to for already employed advertiser
		// NE RADI
	}

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

	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testGetUnemployed() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "//unemployed").principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER)))
				.andExpect(status().isFound()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(CallToCompanyConstants.ADVERT_SIZE)));

	}
	
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testGetSoldEstates() throws Exception {
		
		
		mockMvc.perform(get(URL_PREFIX + "/soldEstates").principal(new UserPrincipal(UserConstants.USERNAME_SECOND)))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(CallToCompanyConstants.SOLD_ESTATES_STEFAN)));
		//testing stefi
		mockMvc.perform(get(URL_PREFIX + "/soldEstates").principal(new UserPrincipal(UserConstants.USERNAME_ADVETISER_TWO)))
		.andExpect(status().isOk()).andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(CallToCompanyConstants.SOLD_ESTATES_STEFI)));

	}
}
