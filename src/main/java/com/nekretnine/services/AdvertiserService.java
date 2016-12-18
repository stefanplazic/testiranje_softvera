package com.nekretnine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.CallToCompany;
import com.nekretnine.repository.AdvertiserRepository;

@Service
public class AdvertiserService {

	@Autowired
	AdvertiserRepository repository;
	
	public Advertiser findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Advertiser> findAll() {
		return repository.findAll();
	}
	
	public Advertiser save(Advertiser advertiser) {
		return repository.save(advertiser);
	}

	public void remove(Long id) {
		repository.delete(id);
	}
}
