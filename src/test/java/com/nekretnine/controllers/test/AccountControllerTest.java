package com.nekretnine.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.nekretnine.constants.AccountConstants;
import com.nekretnine.models.Account;
import com.sun.security.auth.UserPrincipal;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class AccountControllerTest {

	private static final String URL_PREFIX = "/api/account";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testConfig() throws Exception {
		// SecurityContextHolder.getContext().setAuthentication(new
		// UsernamePasswordAuthenticationToken("admin", "admin"));

		Account account = new Account();
		account.setAcountNumber(AccountConstants.FIRST_ACCOUNT_NUMBER);
		account.setAmount(AccountConstants.FIRST_AMOUNT);
		String json = TestUtil.json(account);

		mockMvc.perform(post(URL_PREFIX + "/config").principal(new UserPrincipal("tombola")).contentType(contentType)
				.content(json)).andExpect(status().isCreated());
		// add another account with same number
		account = new Account();
		account.setAcountNumber(AccountConstants.FIRST_ACCOUNT_NUMBER);
		account.setAmount(AccountConstants.FIRST_AMOUNT);
		json = TestUtil.json(account);

		mockMvc.perform(post(URL_PREFIX + "/config").principal(new UserPrincipal("tombola")).contentType(contentType)
				.content(json)).andExpect(status().isConflict());

	}

	@SuppressWarnings("restriction")
	@Test
	@Transactional
	@Rollback(true)
	public void testGetMyAccountData() throws Exception {

		mockMvc.perform(get(URL_PREFIX + "/get").principal(new UserPrincipal("tombola"))).andExpect(status().isFound());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddMoney() throws Exception {

		
		mockMvc.perform(put(URL_PREFIX + "/addMoney/"+AccountConstants.ADD_MONEY).principal(new UserPrincipal("tombola"))).andExpect(status().isOk());
	}
}
