package com.nekretnine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Estate;
import com.nekretnine.repository.EstateRepository;

@Service
public class EstateService {
	
	@Autowired
	EstateRepository repository ;
	
	public Estate save(Estate e){
		return repository.save(e);
		
	}

}
