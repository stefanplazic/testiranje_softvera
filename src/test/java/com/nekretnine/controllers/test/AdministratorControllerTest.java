package com.nekretnine.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.sun.security.auth.UserPrincipal;

import com.nekretnine.MyprojectApplication;
import com.nekretnine.TestUtil;
import com.nekretnine.constants.AdministratorConstraints;
import com.nekretnine.models.Administrator;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Customer;
import com.nekretnine.models.User;
import com.nekretnine.services.UserService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class AdministratorControllerTest {
	
	private static final String URL_PREFIX = "/api/administrator";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * Method used for testing registering a new Admin/Moderator by an already existing Admin.
	 * New Admin will be created with valid data (all data is vaild cause fields are Strings),
	 * and saved to database - response status should be '201 Created'.
	 * New Admin with same data will will be written to database but because one Admin with 
	 * same username already exists - response status should be '409 Conflict'
	 * @throws Exception
	 * @author Nemanja Zunic
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveAdmin() throws Exception {
		
		Administrator admin = new Administrator();
		admin.setFirstName(AdministratorConstraints.FIRST_NAME_SECOND);
		admin.setLastName(AdministratorConstraints.LAST_NAME_SECOND);
		admin.setUsername(AdministratorConstraints.USERNAME_SECOND);
		admin.setEmail(AdministratorConstraints.EMAIL_SECOND);
		admin.setPassword(AdministratorConstraints.PASSWORD_SECOND);

		String json = TestUtil.json(admin);
		mockMvc.perform(post(URL_PREFIX + "/register/administrator").principal(new UserPrincipal(AdministratorConstraints.ADMIN_USERNAME)).contentType(contentType)
				.content(json)).andExpect(status().isCreated());
		
		// check if admin with same username can be creted
		admin = new Administrator();
		admin.setFirstName(AdministratorConstraints.FIRST_NAME);
		admin.setLastName(AdministratorConstraints.LAST_NAME);
		admin.setUsername(AdministratorConstraints.USERNAME);
		admin.setEmail(AdministratorConstraints.EMAIL);
		admin.setPassword(AdministratorConstraints.PASSWORD);

		json = TestUtil.json(admin);
		mockMvc.perform(post(URL_PREFIX + "/register/administrator").principal(new UserPrincipal(AdministratorConstraints.ADMIN_USERNAME)).contentType(contentType)
				.content(json)).andExpect(status().isConflict());		

	}

	/**
	 * Method used for testing accepting a new Company registration by Admin.
	 * If provided Company data is valid (company exists and is waiting for approval - not accepted or 
	 * rejected yet) response status should be '200 OK'.
	 * If data points to already accepted Company response status should be '409 Conflict'
	 * If data points to missing or rejected Company response status is '404 Not Found'
	 * @throws Exception
	 * @author Nemanja Zunic
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testAcceptCompany() throws Exception {
		
		//if company accept is legit - company with param id exists and is waiting for status change
		mockMvc.perform(post(URL_PREFIX + "/acceptCompany/" + AdministratorConstraints.COMPANY_ID).principal(new UserPrincipal(AdministratorConstraints.ADMIN_USERNAME))
				.contentType(contentType)).andExpect(status().isOk());
		
		//if company targeted with id param is already accepted
		mockMvc.perform(post(URL_PREFIX + "/acceptCompany/" + AdministratorConstraints.COMPANY_ID).principal(new UserPrincipal(AdministratorConstraints.ADMIN_USERNAME))
				.contentType(contentType)).andExpect(status().isConflict());

		//if passed id param is of non-existent company
		mockMvc.perform(post(URL_PREFIX + "/acceptCompany/" + AdministratorConstraints.COMPANY_ID_INVALID).principal(new UserPrincipal(AdministratorConstraints.ADMIN_USERNAME))
				.contentType(contentType)).andExpect(status().isNotFound());
		
	}
	
	/**
	 * Similar method to the one for accepting a Company.
	 * @throws Exception
	 * @see testAcceptCompany
	 * @author Nemanja Zunic
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeclineCompany() throws Exception {
		
		//if company decline is legit - company with param id exists and is waiting for status change
		mockMvc.perform(post(URL_PREFIX + "/declineCompany/" + AdministratorConstraints.COMPANY_ID).principal(new UserPrincipal(AdministratorConstraints.ADMIN_USERNAME))
				.contentType(contentType)).andExpect(status().isOk());
		
		//if same company is targeted with id param, company is already deleted
		mockMvc.perform(post(URL_PREFIX + "/declineCompany/" + AdministratorConstraints.COMPANY_ID).principal(new UserPrincipal(AdministratorConstraints.ADMIN_USERNAME))
				.contentType(contentType)).andExpect(status().isNotFound());

		//if passed id param is of already accepted company
		mockMvc.perform(post(URL_PREFIX + "/declineCompany/" + AdministratorConstraints.COMPANY_ID_REJECTED).principal(new UserPrincipal(AdministratorConstraints.ADMIN_USERNAME))
				.contentType(contentType)).andExpect(status().isConflict());
		
	}
	
}

