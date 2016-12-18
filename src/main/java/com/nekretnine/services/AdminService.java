package com.nekretnine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Administrator;
import com.nekretnine.repository.AdministratorRepository;

@Service
public class AdminService {
	
	@Autowired
	AdministratorRepository repository ;
	
	public Administrator findOne(Long id){
		return repository.findOne(id);
		
	}
}
