package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Favourites;

public interface FavouritesRepository extends JpaRepository<Favourites, Long>{

}
