package com.nekretnine.controllers.test;

import static com.nekretnine.constants.CustomerConstants.ADVERTISEMENT_ID;
import static com.nekretnine.constants.CustomerConstants.CUSTOMER;
import static com.nekretnine.constants.CustomerConstants.CUSTOMER_2_ID;
import static com.nekretnine.constants.CustomerConstants.CUSTOMER_EMAIL;
import static com.nekretnine.constants.CustomerConstants.CUSTOMER_EMAIL_2;
import static com.nekretnine.constants.CustomerConstants.CUSTOMER_F_NAME;
import static com.nekretnine.constants.CustomerConstants.CUSTOMER_F_NAME_2;
import static com.nekretnine.constants.CustomerConstants.CUSTOMER_L_NAME;
import static com.nekretnine.constants.CustomerConstants.CUSTOMER_L_NAME_2;
import static com.nekretnine.constants.CustomerConstants.CUSTOMER_USERNAME;
import static com.nekretnine.constants.CustomerConstants.CUSTOMER_USERNAME_2;
import static com.nekretnine.constants.CustomerConstants.DB_COUNT_FAVORITES;
import static com.nekretnine.constants.CustomerConstants.DB_COUNT_FAVORITES_2;
import static com.nekretnine.constants.CustomerConstants.DB_COUNT_FAVORITES_3;
import static com.nekretnine.constants.CustomerConstants.ESTATE_ID;
import static com.nekretnine.constants.CustomerConstants.ESTATE_IN_FAVORITES;
import static com.nekretnine.constants.CustomerConstants.MESSAGE;
import static com.nekretnine.constants.CustomerConstants.PAGE_COUNT;
import static com.nekretnine.constants.CustomerConstants.PAGE_COUNT_2;
import static com.nekretnine.constants.CustomerConstants.PAGE_COUNT_3;
import static com.nekretnine.constants.CustomerConstants.PAGE_NUMBER;
import static com.nekretnine.constants.CustomerConstants.PAGE_NUMBER_2;
import static com.nekretnine.constants.CustomerConstants.PAGE_NUMBER_3;
import static com.nekretnine.constants.CustomerConstants.WRONG_ADVERTISEMENT_ID;
import static com.nekretnine.constants.CustomerConstants.WRONG_CUSTOMER_ID;
import static com.nekretnine.constants.CustomerConstants.WRONG_ESTATE_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.nekretnine.dto.CustomerMessageDTO;
import com.nekretnine.dto.PageableDTO;
import com.sun.security.auth.UserPrincipal;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class CustomerCotrollerTest {

	private static final String URL_PREFIX = "/api/customer";

	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * Test for method getFavourites() from CustomerController.
	 * <p>
	 * Test contains three case:
	 * <p>
	 * 1) Test for part of customer's favorites
	 * <p>
	 * 2) When the customer have favorites but not enough for PAGE_NUMBER_2
	 * <p>
	 * 3) Test for all favorites
	 * 
	 * @throws Exception
	 * @see getFavourites()
	 * @author Miodrag Vilotijević
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testGetFavourites() throws Exception {

		// test for part of customer's favorites
		PageableDTO pageable = new PageableDTO();
		pageable.setPage(PAGE_NUMBER);
		pageable.setCount(PAGE_COUNT);

		String json = TestUtil.json(pageable);
		mockMvc.perform(
				post(URL_PREFIX + "/getFavourites")
						.principal(new UserPrincipal(CUSTOMER))
						.contentType(contentType).content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(DB_COUNT_FAVORITES)));

		// when the customer have favorites but not enough for PAGE_NUMBER_2
		pageable.setPage(PAGE_NUMBER_2);
		pageable.setCount(PAGE_COUNT_2);

		json = TestUtil.json(pageable);
		mockMvc.perform(
				post(URL_PREFIX + "/getFavourites")
						.principal(new UserPrincipal(CUSTOMER))
						.contentType(contentType).content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(DB_COUNT_FAVORITES_2)));

		// test for all favorites
		pageable.setPage(PAGE_NUMBER_3);
		pageable.setCount(PAGE_COUNT_3);

		json = TestUtil.json(pageable);
		mockMvc.perform(
				post(URL_PREFIX + "/getFavourites")
						.principal(new UserPrincipal(CUSTOMER))
						.contentType(contentType).content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(DB_COUNT_FAVORITES_3)));

	}

	/**
	 * Test for method addFavourite from CustomerController.
	 * <p>
	 * Test contains three case:
	 * <p>
	 * 1) When estate doesn't exists
	 * <p>
	 * 2) When estate exists and not in customer's favorites and
	 * <p>
	 * 3) When estate is already in customer's favorites
	 * 
	 * @throws Exception
	 * @see addFavourite()
	 * @author Miodrag Vilotijević
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testAddFavourite() throws Exception {

		// When estate doesn't exists
		mockMvc.perform(
				post(URL_PREFIX + "/addFavourite/" + WRONG_ESTATE_ID)
						.principal(new UserPrincipal(CUSTOMER))).andExpect(
				status().isNotFound());

		// When estate exists and not in customer's favorites
		mockMvc.perform(
				post(URL_PREFIX + "/addFavourite/" + ESTATE_ID).principal(
						new UserPrincipal(CUSTOMER)))
				.andExpect(status().isOk());

		// When estate is already in customer's favorites
		mockMvc.perform(
				post(URL_PREFIX + "/addFavourite/" + ESTATE_ID).principal(
						new UserPrincipal(CUSTOMER))).andExpect(
				status().isConflict());

	}

	/**
	 * Test for method unmarkFavourite() from CustomerController.
	 * <p>
	 * Test contains three case:
	 * <p>
	 * 1) When estate doesn't exists
	 * <p>
	 * 2) When estate exists and not in customer's favorites
	 * <p>
	 * 3) When estate is already in customer's favorites
	 * c
	 * @throws Exception
	 * @see unmarkFavourite()
	 * @author Miodrag Vilotijević
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testUnmarkFavourite() throws Exception {

		// When estate doesn't exists
		mockMvc.perform(
				post(URL_PREFIX + "/unmarkFavourite/" + WRONG_ESTATE_ID)
						.principal(new UserPrincipal(CUSTOMER))).andExpect(
				status().isNotFound());

		// When estate exists and not in customer's favorites
		mockMvc.perform(
				post(URL_PREFIX + "/unmarkFavourite/" + ESTATE_ID).principal(
						new UserPrincipal(CUSTOMER))).andExpect(
				status().isNotFound());

		// When estate is already in customer's favorites
		mockMvc.perform(
				post(URL_PREFIX + "/unmarkFavourite/" + ESTATE_IN_FAVORITES)
						.principal(new UserPrincipal(CUSTOMER))).andExpect(
				status().isOk());

	}

	/**
	 * Test for method sendMessageToAdvertiser() from CustomerController.
	 * <p>
	 * Test contains two case:
	 * <p>
	 * 1) When advertisement doesn't exists
	 * <p>
	 * 2) When advertisement exists
	 * 
	 * @throws Exception
	 * @see sendMessageToAdvertiser()
	 * @author Miodrag Vilotijević
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testSendMessageToAdvertiser() throws Exception {

		// when advertisement doesn't exists
		CustomerMessageDTO messageDTO = new CustomerMessageDTO();
		messageDTO.setMessage(MESSAGE);
		messageDTO.setAdvertisementId(WRONG_ADVERTISEMENT_ID);
		String json = TestUtil.json(messageDTO);

		mockMvc.perform(
				post(URL_PREFIX + "/sendMessageToAdvertiser")
						.principal(new UserPrincipal(CUSTOMER))
						.contentType(contentType).content(json)).andExpect(
				status().isNotFound());

		// when advertisement exists
		messageDTO.setAdvertisementId(ADVERTISEMENT_ID);
		json = TestUtil.json(messageDTO);
		mockMvc.perform(
				post(URL_PREFIX + "/sendMessageToAdvertiser")
						.principal(new UserPrincipal(CUSTOMER))
						.contentType(contentType).content(json)).andExpect(
				status().isOk());

	}

	/**
	 * Test for method myEstates() from CustomerController.
	 * <p>
	 * Test contains three case:
	 * <p>
	 * 1) Test for part of customer's bought estates
	 * <p>
	 * 2) When the customer have bought estates but not enough for passed page
	 * <p>
	 * 3) Test for all bought estates
	 * 
	 * @throws Exception
	 * @see myEstates()
	 * @author Miodrag Vilotijević
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testMyEstates() throws Exception {

		// test for part of customer's bought estates
		PageableDTO pageable = new PageableDTO();
		pageable.setPage(PAGE_NUMBER);
		pageable.setCount(PAGE_COUNT);

		String json = TestUtil.json(pageable);
		mockMvc.perform(
				post(URL_PREFIX + "/myEstates")
						.principal(new UserPrincipal(CUSTOMER))
						.contentType(contentType).content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(DB_COUNT_FAVORITES)));

		// when the customer have bought estates but not enough for passed page
		pageable.setPage(PAGE_NUMBER_2);
		pageable.setCount(PAGE_COUNT_2);

		json = TestUtil.json(pageable);
		mockMvc.perform(
				post(URL_PREFIX + "/myEstates")
						.principal(new UserPrincipal(CUSTOMER))
						.contentType(contentType).content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(DB_COUNT_FAVORITES_2)));

		// test for all bought estates
		pageable.setPage(PAGE_NUMBER_3);
		pageable.setCount(PAGE_COUNT_3);

		json = TestUtil.json(pageable);
		mockMvc.perform(
				post(URL_PREFIX + "/myEstates")
						.principal(new UserPrincipal(CUSTOMER))
						.contentType(contentType).content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(DB_COUNT_FAVORITES_3)));

	}

	/**
	 * Test for method myProfile() from CustomerController.
	 * <p>
	 * Test contains one case:
	 * <p>
	 * 1) Check is valid data for CUSTOMER (from CustomerConstants)
	 * 
	 * @throws Exception
	 * @see myProfile()
	 * @author Miodrag Vilotijević
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testMyProfile() throws Exception {

		// Check data for CUSTOMER (from CustomerConstants)
		mockMvc.perform(
				get(URL_PREFIX + "/myProfile").principal(
						new UserPrincipal(CUSTOMER)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value(CUSTOMER_EMAIL))
				.andExpect(jsonPath("$.firstName").value(CUSTOMER_F_NAME))
				.andExpect(jsonPath("$.lastName").value(CUSTOMER_L_NAME))
				.andExpect(jsonPath("$.username").value(CUSTOMER_USERNAME));

	}

	/**
	 * Test for method customerProfile() from CustomerController.
	 * <p>
	 * Test contains two case:
	 * <p>
	 * 1) When customer with passed id doesn't exists
	 * <p>
	 * 2) When customer with passed id exists
	 * 
	 * @throws Exception
	 * @see customerProfile()
	 * @author Miodrag Vilotijević
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCustomerProfile() throws Exception {

		// When customer with passed id doesn't exists
		mockMvc.perform(
				get(URL_PREFIX + "/customerProfile/" + WRONG_CUSTOMER_ID)
						.principal(new UserPrincipal(CUSTOMER))).andExpect(
				status().isNotFound());

		// Check data for CUSTOMER (from CustomerConstants)
		mockMvc.perform(
				get(URL_PREFIX + "/customerProfile/" + CUSTOMER_2_ID)
						.principal(new UserPrincipal(CUSTOMER)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value(CUSTOMER_EMAIL_2))
				.andExpect(jsonPath("$.firstName").value(CUSTOMER_F_NAME_2))
				.andExpect(jsonPath("$.lastName").value(CUSTOMER_L_NAME_2))
				.andExpect(jsonPath("$.username").value(CUSTOMER_USERNAME_2));

	}

}
