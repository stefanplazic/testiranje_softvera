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
import com.nekretnine.constants.AccountConstants;
import com.nekretnine.constants.UserConstants;
import com.nekretnine.models.Account;
import com.nekretnine.models.User;
import com.nekretnine.services.AccountService;
import com.nekretnine.services.UserService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyprojectApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class AccountServiceTest {

	@Autowired
	private AccountService service;
		
	/**
	 * <p>
	 * This method test adding two account to database. It uses data from AccountConstants.
	 * After insertion it searches for inserted accounts and compares them with given inserted data. Also check are they null.
	 * <p>
	 * @author stefan plazic
	 */
	@Test
	@Transactional
    @Rollback(false)
	public void testAdd(){
		Account account = new Account();
		account.setAcountNumber(AccountConstants.FIRST_ACCOUNT_NUMBER);
		account.setAmount(AccountConstants.FIRST_AMOUNT);
		
		int previusSize = service.findAll().size();
		Account saveAccount = service.save(account);
		assertThat(saveAccount).isNotNull();
		//validate that account is in database
		assertThat(service.findAll()).hasSize(previusSize+1);
		assertThat(saveAccount.getAcountNumber()).isEqualTo(AccountConstants.FIRST_ACCOUNT_NUMBER);
		assertThat(saveAccount.getAmount()).isEqualTo(AccountConstants.FIRST_AMOUNT);
		//add second account
		account = new Account();
		account.setAcountNumber(AccountConstants.SECOND_ACCOUNT_NUMBER);
		account.setAmount(AccountConstants.SECOND_AMOUNT);
		saveAccount = service.save(account);
		assertThat(saveAccount).isNotNull();
		//validate that account is in database
		assertThat(service.findAll()).hasSize(previusSize+2);//this time plus 2 because we added another one
		assertThat(saveAccount.getAcountNumber()).isEqualTo(AccountConstants.SECOND_ACCOUNT_NUMBER);
		assertThat(saveAccount.getAmount()).isEqualTo(AccountConstants.SECOND_AMOUNT);
	}
	
	
	/**
	 * <p>
	 * This method test findAll() method from service by, comparing them with constant COUNT_ACCOUNTS.
	 * <p>
	 * @author stefan plazic
	 */
	@Test
	public void testFindAll(){
		List<Account> accounts = service.findAll();
		assertThat(accounts).hasSize(AccountConstants.COUNT_ACCOUNTS);
	}
	
	/**
	 * <p>
	 * This will test paggable findAll method, by creating page request of size PAGE_SIZE,and
	 * compering returned set is it equal to PAGE_SIZE_ARAY
	 * <p>
	 * @author stefan plazic
	 */
	@Test
	public void testFindAllPagable(){
		PageRequest pageRequest = new PageRequest(0, AccountConstants.PAGE_SIZE);
		Page<Account> accounts = service.findAll(pageRequest);
		assertThat(accounts).hasSize(AccountConstants.PAGE_SIZE_ARAY);
	}
	
	/**
	 * <p>
	 * This method test finding one account. As search parameter uses id of accont retrived from database.
	 * After search is completed it compares account data with previusly fetched one.
	 * <p>
	 * @author stefan plazic
	 */
	@Test
	public void testFindOne(){
		Account acc = service.findAll().get(0);
		Account account = service.findOne(acc.getId());
		assertThat(account.getAcountNumber()).isEqualTo(acc.getAcountNumber());
		assertThat(account.getAmount()).isEqualTo(acc.getAmount());
		
	}
	
	/**
	 * <p>
	 * Tests search capabilities FindByAcountNumber() method . First we fetch  accoutn from the method findAll(),
	 * after that we call FindByAcountNumber() and passes the account number of fecthed account. Then we test it with assertThat test
	 * if data from thoes two accounts are the same.
	 * <p>
	 * @author stefan plazic 
	 */
	@Test
	public void testFindByAcountNumber(){
		//this is always admin
		Account acc = service.findAll().get(0);
		Account account = service.findByAcountNumber(acc.getAcountNumber());
		assertThat(account.getAcountNumber()).isEqualTo(acc.getAcountNumber());
		assertThat(account.getAmount()).isEqualTo(acc.getAmount());
		
	}
	
	/**
	 * <p>
	 * This method test adding money to one account. New Account is fetched , and that some money is added on him.
	 * After that operation we compare amount of money on that account with previus incremented by added amount.
	 * <p>
	 * @author stefan plazic
	 */
	@Test
	public void testAddMoney(){
		//this is always admin
		Account acc = service.findAll().get(0);
		service.addMoney(acc.getId(), AccountConstants.ADD_MONEY);
		Account newAccount = service.findOne(acc.getId());
		assertThat(newAccount.getAcountNumber()).isEqualTo(acc.getAcountNumber());
		assertThat(newAccount.getAmount()).isEqualTo(acc.getAmount() + AccountConstants.ADD_MONEY);
		
	}
	
	/**
	 * <p>
	 * This method tests Remove() method. First we take size all accounts in database, after that we delete one account.
	 * Again we take size of elements and comare it - is it smaller by 1 the the previuse one.
	 * <p>
	 * @author stefan plazic
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testRemove(){
		int prevSize = service.findAll().size();
		Account account = service.findAll().get(0);
		service.remove(account.getId());
		assertThat(service.findAll()).hasSize(prevSize-1);
		
		Account newAccount = service.findOne(account.getId());
		assertThat(newAccount).isNull();
	}
}
