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

import com.nekretnine.MyprojectApplication;
import com.nekretnine.constants.AdministratorConstraints;
import com.nekretnine.constants.ModeratorConstraints;
import com.nekretnine.models.Report;
import com.nekretnine.services.ReportService;
import com.sun.security.auth.UserPrincipal;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class ModeratorControllerTest {

	private static final String URL_PREFIX = "/api/moderator";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private ReportService rsc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	/**
	 * Method used to test Report accepting by a Moderator. If valid Report id is provided response status
	 * should be '200 OK'.
	 * If provided Report id is of non-existent Report response status should be '404 Not Found'.
	 * If provided Report id is of already reviewed Report response status should be '409 Conflict'.
	 * @throws Exception
	 * @author Nemanja Zunic
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testAcceptReport() throws Exception {
		
		
		mockMvc.perform(post(URL_PREFIX + "/acceptReport/" + ModeratorConstraints.REPORT_ID_INVALID)
				.principal(new UserPrincipal(ModeratorConstraints.MODERATOR_USERNAME)).contentType(contentType))
		.andExpect(status().isNotFound());
		
		mockMvc.perform(post(URL_PREFIX + "/acceptReport/" + ModeratorConstraints.REPORT_ID_VALID)
				.principal(new UserPrincipal(ModeratorConstraints.MODERATOR_USERNAME)).contentType(contentType))
		.andExpect(status().isOk());
		
		mockMvc.perform(post(URL_PREFIX + "/acceptReport/" + ModeratorConstraints.REPORT_ID_ACCEPTED)
				.principal(new UserPrincipal(ModeratorConstraints.MODERATOR_USERNAME)).contentType(contentType))
		.andExpect(status().isConflict());
		
	}
	
	/**
	 * Similar to accepting Report method test.
	 * @author Nemanja Zunic
	 * @see testAcceptReport
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void rejectReport() throws Exception {
		
		mockMvc.perform(post(URL_PREFIX + "/rejectReport/" + ModeratorConstraints.REPORT_ID_INVALID)
				.principal(new UserPrincipal(ModeratorConstraints.MODERATOR_USERNAME)).contentType(contentType))
		.andExpect(status().isNotFound());
		
		mockMvc.perform(post(URL_PREFIX + "/rejectReport/" + ModeratorConstraints.REPORT_ID_VALID)
				.principal(new UserPrincipal(ModeratorConstraints.MODERATOR_USERNAME)).contentType(contentType))
		.andExpect(status().isOk());
		
		mockMvc.perform(post(URL_PREFIX + "/acceptReport/" + ModeratorConstraints.REPORT_ID_ACCEPTED)
				.principal(new UserPrincipal(ModeratorConstraints.MODERATOR_USERNAME)).contentType(contentType))
		.andExpect(status().isConflict());
		
	}

}
