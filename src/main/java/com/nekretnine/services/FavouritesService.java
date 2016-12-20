package com.nekretnine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Customer;
import com.nekretnine.models.Estate;
import com.nekretnine.models.Favourites;
import com.nekretnine.repository.FavouritesRepository;

@Service
public class FavouritesService {

	@Autowired
	FavouritesRepository repository ;
	
	public Favourites save(Favourites f){
		return repository.save(f);
	}
	
	public Page<Favourites> findAllByCustomer(Pageable pageable, Customer customer){
		return repository.findAllByCustomer(pageable, customer);
	}

	public Favourites findByEstateAndCustomer(Estate estate, Customer customer) {
		return repository.findByEstateAndCustomer(estate, customer);
	}

	public void delete(Long id) {
		repository.delete(id);
		
	}
}
