package com.nekretnine.controllers.test;

import static com.nekretnine.constants.CustomerConstants.DB_COUNT_FAVORITES;
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
import com.nekretnine.constants.ViewConstraints;
import com.sun.security.auth.UserPrincipal;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class ViewControllerTest {

	private static final String URL_PREFIX = "/api/view";
	
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
	 * Method used to write User's Advertisement view in the database.
	 * If Advertisement Id being passed is of valid advertisement, new View should be saved in the database
	 * and Http statush sholud be '200 OK'.
	 * If Advertisement Id being passed is of non-existant advertisement expected Http status is '409 Conflict'.
	 * @param Advertisement Id 
	 * @author Nemanja Zunic
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveView() throws Exception {

		mockMvc.perform(
				post(URL_PREFIX + "/" + ViewConstraints.ADVERT_ID).principal(new UserPrincipal(ViewConstraints.CUSTOMER_UNAME))
				.contentType(contentType)).andExpect(status().isCreated());
		
		mockMvc.perform(
				post(URL_PREFIX + "/" + ViewConstraints.ADVERT_ID_INVALID).principal(new UserPrincipal(ViewConstraints.CUSTOMER_UNAME))
				.contentType(contentType)).andExpect(status().isConflict());
		
	}
	
	/**
	 * Method used to get ten last viewed Advertisements by the User.
	 * Method is tested for a user with 1 view, expected Http status is '200 OK' and result size 1.
	 * For user with 0 views, expected Http status is '204 No Content'.
	 * For user that has over 10 views, expected result set size is 10.
	 * @author Nemanja Zunic
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void getLastSeen() throws Exception {

		mockMvc.perform(
				get(URL_PREFIX).principal(new UserPrincipal(ViewConstraints.CUSTOMER_UNAME))
				.contentType(contentType))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
		
		mockMvc.perform(
				get(URL_PREFIX).principal(new UserPrincipal(ViewConstraints.CUSTOMER_UNAME_NO_VIEWS))
				.contentType(contentType))
		.andExpect(status().isNoContent());
		
		for(int i = 0; i < 15; i++) {
			mockMvc.perform(
					post(URL_PREFIX + "/" + ViewConstraints.ADVERT_ID).principal(new UserPrincipal(ViewConstraints.CUSTOMER_UNAME))
					.contentType(contentType)).andExpect(status().isCreated());
		}
		
		mockMvc.perform(
				get(URL_PREFIX).principal(new UserPrincipal(ViewConstraints.CUSTOMER_UNAME))
				.contentType(contentType))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(10)));
		
	}
}
