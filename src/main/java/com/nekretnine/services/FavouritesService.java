package com.nekretnine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Favourites;
import com.nekretnine.repository.FavouritesRepository;

@Service
public class FavouritesService {

	@Autowired
	FavouritesRepository repository ;
	
	public Favourites save(Favourites f){
		return repository.save(f);
	}
}
