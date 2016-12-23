package com.nekretnine.services.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.repository.query.Param;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nekretnine.MyprojectApplication;
import com.nekretnine.constants.AdvertisementConstraints;
import com.nekretnine.constants.UserConstants;
import com.nekretnine.models.Advertisement;
import com.nekretnine.models.User;
import com.nekretnine.models.Advertisement.State;
import com.nekretnine.services.AdvertisementService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class AdvertisementServiceTest {

	@Autowired
	private AdvertisementService service;
	
	@Test
	public void testFindAll(){
		List<Advertisement> ads = service.findAll();
		assertThat(ads).hasSize(AdvertisementConstraints.INITIAL_SIZE);
	}
	
	@Test
	public void testFindOne() throws ParseException{

		Advertisement advertisement = service.findOne(AdvertisementConstraints.ADVERT_ID);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date pubDate = df.parse(AdvertisementConstraints.PUBLICATION_DATE_INITIAL);
		Date expDate = df.parse(AdvertisementConstraints.EXPIRY_DATE_INITIAL);
		assertThat(advertisement.getState()).isEqualTo(AdvertisementConstraints.INITIAL_STATE);
		assertThat(advertisement.getPublicationDate()).isEqualTo(new Timestamp(pubDate.getTime()));
		assertThat(advertisement.getExpiryDate()).isEqualTo(new Timestamp(expDate.getTime()));
		
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testAdd(){
		
		Advertisement adv = new Advertisement();
		adv.setPublicationDate(AdvertisementConstraints.PUBLICATION_DATE);
		adv.setExpiryDate(AdvertisementConstraints.EXPIRY_DATE);
		adv.setState(AdvertisementConstraints.STATE);
		
		Advertisement ad = service.save(adv);
		//validate that advert is in the database
		assertThat(service.findAll()).hasSize(AdvertisementConstraints.INITIAL_SIZE+1);
		
		assertThat(ad.getPublicationDate()).isEqualTo(AdvertisementConstraints.PUBLICATION_DATE);
		assertThat(ad.getExpiryDate()).isEqualTo(AdvertisementConstraints.EXPIRY_DATE);
		assertThat(ad.getState()).isEqualTo(AdvertisementConstraints.STATE);
	
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSetState() {
		
		service.setState(AdvertisementConstraints.NEW_STATE, AdvertisementConstraints.ADVERT_ID);
		Advertisement adv = service.findOne(AdvertisementConstraints.ADVERT_ID);
		assertThat(adv.getState()).isNotEqualTo(AdvertisementConstraints.INITIAL_STATE);
		assertThat(adv.getState()).isEqualTo(AdvertisementConstraints.NEW_STATE);
	}	
	
}
