package com.nekretnine.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.nekretnine.MyprojectApplication;
import com.nekretnine.constants.UserConstants;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.User;
import com.nekretnine.services.UserService;
import com.sun.security.auth.UserPrincipal;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
//@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:insert.sql")
public class AdvertiserControllerTest {

	private static final String URL_PREFIX = "/api/advertiser";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserService service;

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

		//create dummyData
		dummyData();
		User user = service.findByUsername(UserConstants.USERNAME_SECOND);
		mockMvc.perform(get(URL_PREFIX + "/profile/" + user.getId())
				.principal(new UserPrincipal(UserConstants.USERNAME_SECOND))).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.firstName").value(UserConstants.FIRST_NAME_SECOND))
				.andExpect(jsonPath("$.lastName").value(UserConstants.LAST_NAME_SECOND))
				.andExpect(jsonPath("$.username").value(UserConstants.USERNAME_SECOND))
				.andExpect(jsonPath("$.email").value(UserConstants.EMAIL_SECOND));
		//test this on id wich doesnt exists
		mockMvc.perform(get(URL_PREFIX + "/profile/" + 1000)
				.principal(new UserPrincipal(UserConstants.USERNAME_SECOND)))
				.andExpect(status().isNotFound());
	}
	
	private void dummyData(){
		User user = new Advertiser();
		user.setFirstName(UserConstants.FIRST_NAME_SECOND);
		user.setLastName(UserConstants.LAST_NAME_SECOND);
		user.setUsername(UserConstants.USERNAME_SECOND);
		user.setEmail(UserConstants.EMAIL_SECOND);
		user.setPassword(UserConstants.PASSWORD_SECOND);
		//check if exists
		if(service.findByUsername(UserConstants.USERNAME_SECOND)==null){
			service.save(user);
		}
	}
	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testGetMyProfile() throws Exception{
		//call dummyData() to populate database
		dummyData();
		User user = service.findByUsername(UserConstants.USERNAME_SECOND);
		mockMvc.perform(get(URL_PREFIX + "/myprofile")
				.principal(new UserPrincipal(UserConstants.USERNAME_SECOND))).andExpect(status().isOk());
	}
	
	
}
