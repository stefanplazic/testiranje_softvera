package com.nekretnine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Advertiser;
import com.nekretnine.repository.AdvertiserRepository;

@Service
public class AdvertiserService {

	@Autowired
	AdvertiserRepository repository;
	
	public Advertiser findOne(Long id) {
		return repository.findOne(id);
	}

}
