package com.nekretnine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.CallToCompany;
import com.nekretnine.repository.CallToCompanyRepository;

@Service
public class CallToCompanyService {

	@Autowired
	CallToCompanyRepository repository;
	
	public CallToCompany findOne(Long id) {
		return repository.findOne(id);
	}

	public List<CallToCompany> findAll() {
		return repository.findAll();
	}
	
	public CallToCompany save(CallToCompany company) {
		return repository.save(company);
	}

	public void remove(Long id) {
		repository.delete(id);
	}
}
