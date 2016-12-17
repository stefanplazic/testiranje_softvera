package com.nekretnine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Advertisement;
import com.nekretnine.repository.AdvertisementRepository;

@Service
public class AdvertisementService {

	@Autowired
	AdvertisementRepository repository ;
	
	public Advertisement save(Advertisement a){
		return repository.save(a);
		
	}
	
}
