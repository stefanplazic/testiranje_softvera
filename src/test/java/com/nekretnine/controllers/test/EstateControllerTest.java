package com.nekretnine.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

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
import com.nekretnine.dto.EstateDTO;
import com.nekretnine.dto.ImageDTO;
import com.nekretnine.dto.RateDTO;
import com.sun.security.auth.UserPrincipal;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class EstateControllerTest {
	
	private static final String URL_PREFIX = "/api/estate";
	
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
	 * Test saving estate by advertiser
	 * 1st case : given correct data and estate name doesn't exists 201 created
	 * 2nd case : estate name does exists conflict 409
	 * 
	 * @throws Exception
	 * @author sirko
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveEstate() throws Exception {
		
		EstateDTO e= new EstateDTO();
		Set<ImageDTO> imgs=new HashSet<ImageDTO>();		
		imgs.add(new ImageDTO(null,"nekiurl",null));
		e.setName("livada");
		e.setCity("selo");
		e.setCityPart("seoce");
		e.setArea(1000);
		e.setPrice(100);
		e.setAddress("bb");
		e.setImages(imgs);
		e.setHeatingSystem("sunce");
		e.setTechnicalEquipment("lift");
		
		
		String json_data=TestUtil.json(e);
		
		//proveri dodavanje u bazu
		mockMvc.perform(post(URL_PREFIX + "/add")
				.principal(new UserPrincipal("mile"))
				.contentType(contentType)
				.content(json_data))
			.andExpect(status().isCreated());
		
		//dodavanje ako vec postoji nekretnina
		mockMvc.perform(post(URL_PREFIX + "/add")
				.principal(new UserPrincipal("mile"))
				.contentType(contentType)
				.content(json_data))
			.andExpect(status().isConflict());	
		
		
		
		
	}
	/**
	 * Test rating estates
	 * 1st case: not rated before by user 201 created
	 * 2nd case: already ratet 409
	 * 3rd case: estate does not exists 404
	 * @throws Exception
	 * @author sirko
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testRateEstate() throws Exception {
		
		RateDTO r=new RateDTO();
		r.setRate(3);
		
		String json_data =TestUtil.json(r);
		
		//dodavanje ako vec postoji nekretnina
		mockMvc.perform(post(URL_PREFIX + "/rate/1")
				.principal(new UserPrincipal("cone"))
				.contentType(contentType)
				.content(json_data))
			.andExpect(status().isCreated());	
		
		//ocena na prethodno ocenjenu nekretninu
		mockMvc.perform(post(URL_PREFIX + "/rate/1")
				.principal(new UserPrincipal("cone"))
				.contentType(contentType)
				.content(json_data))
			.andExpect(status().isConflict());	
		
		
		//ocena na nepostojecu nekretninu
		mockMvc.perform(post(URL_PREFIX + "/rate/14523")
				.principal(new UserPrincipal("cone"))
				.contentType(contentType)
				.content(json_data))
			.andExpect(status().isNotFound());	
		
				
		
	
	}
		

}
