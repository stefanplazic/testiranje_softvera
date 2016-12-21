package com.nekretnine.services.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nekretnine.MyprojectApplication;
import com.nekretnine.constants.UserConstants;
import com.nekretnine.models.User;
import com.nekretnine.services.UserService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserServiceTest {

	@Autowired
	private UserService service;
	
	@Test
	public void testFindAll(){
		List<User> users = service.findAll();
		assertThat(users).hasSize(UserConstants.FIND_ALL_COUNT);
	}
	
	@Test
	public void testFindAllPagable(){
		PageRequest pageRequest = new PageRequest(1, UserConstants.PAGE_SIZE);
		Page<User> users = service.findAll(pageRequest);
		assertThat(users).hasSize(UserConstants.PAGE_SIZE);
	}
}
