package com.nekretnine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.RateEstate;
import com.nekretnine.repository.RateEstateRepository;

@Service
public class RateEstateService {

	@Autowired
	private RateEstateRepository repository;
	

	public RateEstate save(RateEstate re){
		return repository.save(re);
	}
}
