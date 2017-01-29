package com.nekretnine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Estate;
import com.nekretnine.repository.EstateRepository;

@Service
public class EstateService {
	
	@Autowired
	EstateRepository repository ;
	
	public Estate save(Estate e){
		return repository.save(e);
	}
	
	public Estate findOne(Long id){
		return repository.findOne(id);
	}

	public List<Estate> findAll() {
		return repository.findAll();
	}
	
	public Page<Estate> findAll(Pageable page) {
		return repository.findAll(page);
	}
	
	public void remove(Long id) {
		repository.delete(id);
	}
	
	public Estate findOneByName(String name){
		return repository.findOneByName(name);
	}
	
	public List<Estate> findAllByOwnerId(Advertiser owner){
		return repository.findAllByOwnerId(owner.getId());
	}
}
