package com.nekretnine.services.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
		PageRequest pageRequest = new PageRequest(0, UserConstants.PAGE_SIZE);
		Page<User> users = service.findAll(pageRequest);
		assertThat(users).hasSize(UserConstants.PAGE_SIZE);
	}
	@Test
	public void testFindOne(){
		//this is always admin
		User user = service.findOne(UserConstants.ADMIN_ID);
		assertThat(user.getUsername()).isEqualTo(UserConstants.ADMIN_USERNAME);
		assertThat(user.getEmail()).isEqualTo(UserConstants.ADMIN_EMAIL);
		assertThat(user.getFirstName()).isEqualTo(UserConstants.ADMIN_FIRST_NAME);
		assertThat(user.getLastName()).isEqualTo(UserConstants.ADMIN_LAST_NAME);
		
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testAdd(){
		User user = new User();
		user.setUsername(UserConstants.USERNAME);
		user.setFirstName(UserConstants.FIRST_NAME);
		user.setLastName(UserConstants.LAST_NAME);
		user.setEmail(UserConstants.EMAIL);
		user.setPassword(UserConstants.PASSWORD);
		user.setVerified(UserConstants.VERIFIED);
		
		int previusSize = service.findAll().size();
		User saveUser = service.save(user);
		assertThat(saveUser).isNotNull();
		//validate that user is in database
		assertThat(service.findAll()).hasSize(previusSize+1);
		
		assertThat(saveUser.getUsername()).isEqualTo(UserConstants.USERNAME);
		assertThat(saveUser.getEmail()).isEqualTo(UserConstants.EMAIL);
		assertThat(saveUser.getFirstName()).isEqualTo(UserConstants.FIRST_NAME);
		assertThat(saveUser.getLastName()).isEqualTo(UserConstants.LAST_NAME);
		assertThat(saveUser.isVerified()).isEqualTo(UserConstants.VERIFIED);
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testRemove(){
		int prevSize = service.findAll().size();
		service.remove(UserConstants.USER_ID);
		assertThat(service.findAll()).hasSize(prevSize-1);
		
		User user = service.findOne(UserConstants.USER_ID);
		assertThat(user).isNull();
	}
	
	@Test
	public void testFindOneByUsernameAndPassword(){
		User user = service.findOneByUsernameAndPassword(UserConstants.USERNAME_SECOND,UserConstants.PASSWORD_SECOND);
		
		assertThat(user).isNotNull();
		assertThat(user.getUsername()).isEqualTo(UserConstants.USERNAME_SECOND);
		assertThat(user.getPassword()).isEqualTo(UserConstants.PASSWORD_SECOND);
	}
	
	@Test
	public void testFindByUsername(){
		User user = service.findByUsername(UserConstants.USERNAME_SECOND);
		
		assertThat(user).isNotNull();
		assertThat(user.getUsername()).isEqualTo(UserConstants.USERNAME_SECOND);
	}
	
	@Test
	public void testFindByEmail(){
		User user = service.findByEmail(UserConstants.EMAIL_SECOND);
		
		assertThat(user).isNotNull();
		assertThat(user.getEmail()).isEqualTo(UserConstants.EMAIL_SECOND);
	}
	
	@Test
	public void TestFindByVerifyCode(){
		User user = service.findByVerifyCode(UserConstants.VERIFY_CODE);
		
		assertThat(user).isNotNull();
		assertThat(user.getVerifyCode()).isEqualTo(UserConstants.VERIFY_CODE);
	}
	
	
}
