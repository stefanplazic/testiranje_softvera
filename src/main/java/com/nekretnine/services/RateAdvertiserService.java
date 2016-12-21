package com.nekretnine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.RateAdvertiser;
import com.nekretnine.repository.RateAdvertiserRepository;

@Service
public class RateAdvertiserService {

	@Autowired
	private RateAdvertiserRepository repository;
	
	public RateAdvertiser save(RateAdvertiser ra){
		return repository.save(ra);
	}
	
}
