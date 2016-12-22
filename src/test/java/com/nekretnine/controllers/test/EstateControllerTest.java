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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.nekretnine.TestUtil;
import com.nekretnine.dto.EstateDTO;
import com.nekretnine.dto.ImageDTO;

//@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
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
	 * <p>
	 * This method will test saveEstate method in EstateController. First it
	 * will create user with given username and passwrod nad save it as
	 * Advertiser. Result should be Created. After that it will try to save
	 * Customer , but with same email - wich will send Conflict HTTP status
	 * code.
	 * <p>
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
				
				.contentType(contentType)
				.content(json_data))
			.andExpect(status().isCreated());
		
		//dodavanje ako vec postoji korisnik
		
		
		
		
		
		
		
	}
	
	

}
