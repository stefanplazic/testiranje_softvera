package com.nekretnine.services;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.nekretnine.models.Authority;
import com.nekretnine.repository.AuthorityRepository;

@Component
public class AuthorityLoader implements ApplicationRunner{

	private AuthorityRepository repository;
	
	public AuthorityLoader(AuthorityRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Authority authority;
		
		if(repository.findByName("ADMIN")==null){
		authority = new Authority();
		authority.setName("ADMIN");
		repository.save(authority);
		}
		if(repository.findByName("MODERATOR")==null){
		//save moderator
		authority = new Authority();
		authority.setName("MODERATOR");
		repository.save(authority);
		}
		
		if(repository.findByName("CUSTOMER")==null){
		//save customer
		authority = new Authority();
		authority.setName("CUSTOMER");
		repository.save(authority);
		}
		if(repository.findByName("ADVERTISER")==null){
		//save advertiser
		authority = new Authority();
		authority.setName("ADVERTISER");
		repository.save(authority);
		}
	}

}
