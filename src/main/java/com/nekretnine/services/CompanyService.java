package com.nekretnine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Company;
import com.nekretnine.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	CompanyRepository repository;
	
	public Company findOne(long id){
		return repository.getOne(id);
	}
	
	public Company saveCompany(Company company){
		return repository.save(company);
	}
	
	public Company findOneByNameAndAddress(String name, String address) {
		return repository.findOneByNameAndAddress(name, address);
	}
}
