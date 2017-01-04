package com.nekretnine.controllers.test;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Date;

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
import com.nekretnine.dto.AdvertisementDTO;
import com.nekretnine.dto.AdvertisementSearchDTO;
import com.nekretnine.dto.EstateSearchDTO;
import com.nekretnine.models.Advertisement;
import com.sun.security.auth.UserPrincipal;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class AdvertisementControllerTest {
	
	private static final String URL_PREFIX = "/api/advertisement";
	
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
	 * Test for advertisement creating by advertisers.
	 * 1st case: provided right data response 201 created
	 * 2nd case: provided nonexisting estate id response 404 notfound
	 * 3rd case: provided someone elses estate id response conflict
	 * @throws Exception
	 * @author sirko
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void saveAdvertisementTest() throws Exception{
		
		AdvertisementDTO a = new AdvertisementDTO();
		
		a.setPublicationDate(new Date());
		a.setExpiryDate(new Date());		
		
		String jsonData = TestUtil.json(a);
		
		//dodavanje oglasa u bazu
		mockMvc.perform(post(URL_PREFIX + "/add/1")
				.principal(new UserPrincipal("stefan"))
				.contentType(contentType)
				.content(jsonData))
			.andExpect(status().isCreated());	
		
		//dodavanje oglasa za nepostojecu nekretninu
		mockMvc.perform(post(URL_PREFIX + "/add/1389")
				.principal(new UserPrincipal("stefan"))
				.contentType(contentType)
				.content(jsonData))
			.andExpect(status().isNotFound());	
		
		//dodavanje oglasa za nekretninu kojoj nema pristup
		mockMvc.perform(post(URL_PREFIX + "/add/3")
				.principal(new UserPrincipal("stefan"))
				.contentType(contentType)
				.content(jsonData))
			.andExpect(status().isConflict());	
		
	}
	/**
	 * Test for modifying status of an ad
	 * 1st case: is authorised to modify and advertisement does exists 200 ok
	 * 2nd case: not authorised to modify response conflict
	 * 3rd case: ad with requested id does not exists 404 not found
	 * @throws Exception
	 * @author sirko
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void modifyAdvertisementStatusTest() throws Exception{
		
		AdvertisementDTO a = new AdvertisementDTO();		
		a.setState(Advertisement.State.REMOVED);
		
		String jsonData = TestUtil.json(a);
		
		
		//promeni status na REMOVED
		mockMvc.perform(post(URL_PREFIX + "/modify/1")
				.principal(new UserPrincipal("stefan"))
				.contentType(contentType)
				.content(jsonData))
			.andExpect(status().isOk());
		
		//not authorised to modify
		mockMvc.perform(post(URL_PREFIX + "/modify/1")
				.principal(new UserPrincipal("stefi"))
				.contentType(contentType)
				.content(jsonData))
			.andExpect(status().isConflict());	
		
		//invalid advertisement id
		mockMvc.perform(post(URL_PREFIX + "/modify/1234234")
				.principal(new UserPrincipal("stefan"))
				.contentType(contentType)
				.content(jsonData))				
			.andExpect(status().isNotFound());	
		
	}
	
	/**
	 * Test returning advertisement list
	 * 1st case : chek if returned lists size is as requested
	 * @throws Exception
	 * @author sirko
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void getAdvertisementListTest() throws Exception{
		
		AdvertisementSearchDTO a = new AdvertisementSearchDTO();
		a.setEstate(new EstateSearchDTO());
		String jsonData = TestUtil.json(a);
		int count=2;
		mockMvc.perform(post(URL_PREFIX + "?page=0&count="+count)
				.principal(new UserPrincipal("stefi"))
				.contentType(contentType)
				.content(jsonData))				
			.andExpect(status().isOk())
			.andExpect(jsonPath("$",hasSize(count)));	
		
	}
	
	
	/**
	 * Tests returning of a single advertisement with full and restricted view
	 * 1st case: check does it return advertisement with requested id 200
	 * 2nd case :ad with requested id does not exist 404
	 * 3rd case : checking is address attribute not found in restrivted view
	 * @throws Exception
	 * @author sirko
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void getAdvertisementTest() throws Exception{
		
		//check does it return advertisement with requested id
		int advertId = 1;
		mockMvc.perform(get(URL_PREFIX + "/"+advertId)
				.principal(new UserPrincipal("admin")))				
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(advertId));	
		
		
		//invalid advertisement id
		mockMvc.perform(get(URL_PREFIX + "/12017")
				.principal(new UserPrincipal("admin")))				
			.andExpect(status().isNotFound());
			
		//is it for unregistered user
		mockMvc.perform(get(URL_PREFIX + "/"+advertId))				
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.estate.adress").doesNotExist());	
		
	}
	
	/**
	 * Test saving report to database
	 * 1st case: correct parameteres given 200
	 * @throws Exception
	 * @author sirko
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void saveReportTest() throws Exception{
		
		//test saving report
		String message="message";		
		mockMvc.perform(post(URL_PREFIX + "/2/report/")
				.principal(new UserPrincipal("stefi"))
				.contentType(contentType)
				.param("message",message))				
			.andExpect(status().isOk());
					
	}
	
	
}
