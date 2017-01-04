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
	
	/**
	 * <p>
	 * Test the FindalAll() method from UserService. It executes the method and compares with FIND_ALL_COUNT
	 * that is constant predefined.
	 * <p>
	 * @author stefan plazic
	 */
	@Test
	@Transactional
    @Rollback(true)
	public void testFindAll(){
		List<User> users = service.findAll();
		assertThat(users).hasSize(UserConstants.FIND_ALL_COUNT);
	}
	
	/**
	 * 
	 * <p>
	 * Test the FindAllPagable() method from UserService. It creates the page request of predefined page size,
	 * and then test the returned page size with the same constant PAGE_SIZE.
	 * <p>
	 * @author stefan plazic
	 */
	@Test
	@Transactional
    @Rollback(true)
	public void testFindAllPagable(){
		PageRequest pageRequest = new PageRequest(0, UserConstants.PAGE_SIZE);
		Page<User> users = service.findAll(pageRequest);
		assertThat(users).hasSize(UserConstants.PAGE_SIZE);
	}
	
	/**
	 * 
	 * <p>
	 * Test the FindOne() method from UserService. It finds user by constant ADMIN_ID. After that it uses
	 * assertThat to campare returned user data with given constants (ADMIN_USERNAME,ADMIN_EMAIL,..)
	 * @author stefan plazic
	 */
	@Test
	@Transactional
    @Rollback(true)
	public void testFindOne(){
		//this is always admin
		User user = service.findOne(UserConstants.ADMIN_ID);
		assertThat(user.getUsername()).isEqualTo(UserConstants.ADMIN_USERNAME);
		assertThat(user.getEmail()).isEqualTo(UserConstants.ADMIN_EMAIL);
		assertThat(user.getFirstName()).isEqualTo(UserConstants.ADMIN_FIRST_NAME);
		assertThat(user.getLastName()).isEqualTo(UserConstants.ADMIN_LAST_NAME);
		
	}
	
	/**
	 * 
	 * <p>
	 * Test the Add() method from UserService. It adds user with given user data saved in constants
	 * (USERNAME,FIRST_NAME,LAST_NAME,..) and checks if page size is incremented by one.
	 * <p>
	 * @author stefan plazic
	 */
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
	
	/**
	 * 
	 * <p>
	 * Test the Remove() method from UserService. It will create artificial user with dummy data, after it will be inserted into database.
	 * Then it will take the number of all users in database. After remove of the dummy user from database, it will check if previusly taken size is smaller be the one - from the current size.
	 * <p>
	 * @author stefan plazic
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testRemove(){
		//insert user into database and then delete it
		User user = new User();
		user.setUsername(UserConstants.USERNAME);
		user.setFirstName(UserConstants.FIRST_NAME);
		user.setLastName(UserConstants.LAST_NAME);
		user.setEmail(UserConstants.EMAIL);
		user.setPassword(UserConstants.PASSWORD);
		user.setVerified(UserConstants.VERIFIED);
		user = service.save(user);
		int prevSize = service.findAll().size();
		service.remove(user.getId());
		assertThat(service.findAll()).hasSize(prevSize-1);
		
		User newUser = service.findOne(user.getId());
		assertThat(newUser).isNull();
	}
	
	/**
	 * 
	 * <p>
	 * Test the testFindOneByUsernameAndPassword() method from UserService. It will try to find user by username and password from dataabase , using existing user data.
	 *<p>
	 * @author stefan plazic
	 */
	@Test
	@Transactional
    @Rollback(true)
	public void testFindOneByUsernameAndPassword(){
		User user = service.findOneByUsernameAndPassword(UserConstants.USERNAME_SECOND,UserConstants.PASSWORD_SECOND);
		
		assertThat(user).isNotNull();
		assertThat(user.getUsername()).isEqualTo(UserConstants.USERNAME_SECOND);
		assertThat(user.getPassword()).isEqualTo(UserConstants.PASSWORD_SECOND);
	}
	
	/**
	 * 
	 * <p>
	 * Test the FindByUsername() method from UserService. It will try to find user by username  from dataabase , using existing user data.
	 *<p>
	 * @author stefan plazic
	 */
	@Test
	@Transactional
    @Rollback(true)
	public void testFindByUsername(){
		User user = service.findByUsername(UserConstants.USERNAME_SECOND);
		
		assertThat(user).isNotNull();
		assertThat(user.getUsername()).isEqualTo(UserConstants.USERNAME_SECOND);
	}
	
	/**
	 * 
	 * <p>
	 * Test the FindByEmail() method from UserService. It will find user by given email. Because we used email of user already inserted
	 * , and user will be find. After that it will pass the test of comparing the email of given user by the constant EMAIL_SECOND.
	 * <p>
	 * @author stefan plazic
	 */
	@Test
	@Transactional
    @Rollback(true)
	public void testFindByEmail(){
		User user = service.findByEmail(UserConstants.EMAIL_SECOND);
		
		assertThat(user).isNotNull();
		assertThat(user.getEmail()).isEqualTo(UserConstants.EMAIL_SECOND);
	}
	
	/**
	 * 
	 * <p>
	 * Test the FindByVerifyCode() method from UserService. It will take the user verifikacion code and find user
	 * with that username. After that user is check if contains given verification code.
	 * <p>
	 * @author stefan plazic
	 */
	@Test
	@Transactional
    @Rollback(true)
	public void testFindByVerifyCode(){
		User user = service.findByVerifyCode(UserConstants.VERIFY_CODE);
		
		assertThat(user).isNotNull();
		assertThat(user.getVerifyCode()).isEqualTo(UserConstants.VERIFY_CODE);
	}
	
	
}
