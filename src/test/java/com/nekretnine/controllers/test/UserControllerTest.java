package com.nekretnine.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.nekretnine.TestUtil;
import com.nekretnine.constants.UserConstants;
import com.nekretnine.dto.LoginDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Customer;
import com.nekretnine.models.User;
import com.nekretnine.services.UserService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserControllerTest {

	private static final String URL_PREFIX = "/api/users";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserService userService;

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * <p>
	 * This method will test saveCustomer method in UserController. First it
	 * will create user with given username and passwrod nad save it as
	 * Advertiser. Result should be Created. After that it will try to save
	 * Customer , but with same email - wich will send Conflict HTTP status
	 * code.
	 * <p>
	 * 
	 * @throws Exception
	 * @author stefan plazic
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveUser() throws Exception {
		User user = new Advertiser();
		user.setFirstName(UserConstants.FIRST_NAME);
		user.setLastName(UserConstants.LAST_NAME);
		user.setUsername(UserConstants.USERNAME);
		user.setEmail(UserConstants.EMAIL);
		user.setPassword(UserConstants.PASSWORD);

		String json = TestUtil.json(user);
		mockMvc.perform(post(URL_PREFIX + "/register/advertiser").contentType(contentType).content(json))
				.andExpect(status().isCreated());
		// check if user with same username can be creted
		user = new Customer();
		user.setFirstName(UserConstants.FIRST_NAME);
		user.setLastName(UserConstants.LAST_NAME);
		user.setUsername(UserConstants.USERNAME_SECOND);
		user.setEmail(UserConstants.EMAIL);
		user.setPassword(UserConstants.PASSWORD);

		json = TestUtil.json(user);
		mockMvc.perform(post(URL_PREFIX + "/register/customer").contentType(contentType).content(json))
				.andExpect(status().isConflict());

	}

	/**
	 * <p>
	 * This method will test login method in UserController. First it will try
	 * to login with correct useraname and password, it will return OK http
	 * status code. Then we try to login with wrong credentionals - result Not
	 * found http status code.
	 * <p>
	 * 
	 * @throws Exception
	 * @author stefan plazic
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testLogin() throws Exception {
		// login with first user creditionals
		LoginDTO user = new LoginDTO();
		user.setUsername(UserConstants.USERNAME_SECOND);
		user.setPassword(UserConstants.PASSWORD_SECOND_TRUE);

		String json = TestUtil.json(user);
		mockMvc.perform(post(URL_PREFIX + "/login").contentType(contentType).content(json)).andExpect(status().isOk());

		// this needs to fail
		user.setUsername(UserConstants.FIRST_NAME_THIRD);
		user.setPassword(UserConstants.PASSWORD_THIRD);

		json = TestUtil.json(user);
		mockMvc.perform(post(URL_PREFIX + "/login").contentType(contentType).content(json))
				.andExpect(status().isNotFound());
	}

	/**
	 * <p>
	 * This method will test verify method in UserController. First it will get
	 * user from database and user his verify code to test the method. It will
	 * return OK status. After that will try to verify with incorrect code and
	 * result will be NOT FOUND.
	 * <p>
	 * 
	 * @throws Exception
	 * @author stefan plazic
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testVerify() throws Exception {
		// this test should be valid , we already have a user with that username
		User user = userService.findByUsername(UserConstants.USERNAME_SECOND);
		mockMvc.perform(get(URL_PREFIX + "/verify/" + user.getVerifyCode())).andExpect(status().isOk());
		// this should fail bacause the the given verification code doesn't
		// exists
		mockMvc.perform(get(URL_PREFIX + "/verify/" + user.getVerifyCode() + "asssss"))
				.andExpect(status().isNotFound());
	}

}
