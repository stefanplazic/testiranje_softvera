package com.nekretnine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Advertiser;
import com.nekretnine.repository.AdvertiserRepository;

@Service
public class AdvertiserService {

	@Autowired
	private AdvertiserRepository repository;
	
	public Advertiser findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Advertiser> findAll() {
		return repository.findAll();
	}
	
	public Page<Advertiser> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Advertiser save(Advertiser advertiser) {
		return repository.save(advertiser);
	}

	public void remove(Long id) {
		repository.delete(id);
	}

}

	
