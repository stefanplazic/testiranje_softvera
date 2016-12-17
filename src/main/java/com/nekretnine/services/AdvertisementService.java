package com.nekretnine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertisement.State;
import com.nekretnine.repository.AdvertisementRepository;

@Service
public class AdvertisementService {

	@Autowired
	AdvertisementRepository repository ;
	
	public Advertisement save(Advertisement a){
		return repository.save(a);
		
	}
	
	public Advertisement findOne(Long id){
		return repository.findOne(id);
	}
	
	public void setState(State state,Long id){
		repository.setState(state,id);
	}
	
	public void delete(Long id){
		repository.deleteById(id);
		
		
	}
	
}
