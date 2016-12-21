package com.nekretnine.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.dto.AccountDTO;
import com.nekretnine.models.Account;
import com.nekretnine.models.User;
import com.nekretnine.services.AccountService;
import com.nekretnine.services.UserService;


@RestController
@RequestMapping(value="api/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(value="/addMoney/{amount}",method=RequestMethod.PUT)
	public ResponseEntity<String> addMoney(Principal principal, @PathVariable double amount){
		if(amount <0)
			return new ResponseEntity<String>("Can't put negative ammount of money",HttpStatus.BAD_REQUEST);
		//get the user data
		User user = userService.findByUsername(principal.getName());
		//add money to account
		Account account= user.getAccount();
		if(account == null){
			return new ResponseEntity<String>("You first must configure your money account",HttpStatus.NOT_FOUND);
		}
		
		accountService.addMoney(user.getAccount().getId(), amount);
		accountService.save(account);
		return new ResponseEntity<String>("Successufull account update",HttpStatus.OK);
	}
	
	@RequestMapping(value="/config",method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<String> configure(Principal principal, @RequestBody AccountDTO accountDTO){
		
		//get the user data
		User user = userService.findByUsername(principal.getName());
		if(user.getAccount() != null)
			return new ResponseEntity<String>("You already have your account setup",HttpStatus.BAD_REQUEST);
		
		if(accountService.findByAcountNumber(accountDTO.getAcountNumber())!=null)
			return new ResponseEntity<String>("Account with given name already exists",HttpStatus.BAD_REQUEST);
		
		//make account
		Account account= new Account();
		account.setAcountNumber(accountDTO.getAcountNumber());
		account.setAmount(accountDTO.getAmount());
		accountService.save(account);
		//add to user
		user.setAccount(account);
		userService.save(user);
		
		return new ResponseEntity<String>("Successufull created account",HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public ResponseEntity<AccountDTO> getMyAccountData(Principal principal){
		
		//get the user data
		User user = userService.findByUsername(principal.getName());
		if(user.getAccount() == null)
			return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(user.getAccount()),HttpStatus.FOUND);
	}
	
}
