package com.nekretnine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Account;
import com.nekretnine.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;
	
	public Account findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Account> findAll() {
		return repository.findAll();
	}
	
	public Page<Account> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Account save(Account Account) {
		return repository.save(Account);
	}

	public void remove(Long id) {
		repository.delete(id);
	}
	
	public void addMoney(Long id,double amount){
		Account account = repository.findOne(id);
		account.setAmount(account.getAmount()+amount);
		repository.save(account);
	}
	
	public Account findByAcountNumber(String acountNumber){
		return repository.findByAcountNumber(acountNumber);
	}
}
