package com.nekretnine.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Customer;
import com.nekretnine.models.Estate;
import com.nekretnine.models.Favourites;

public interface FavouritesRepository extends JpaRepository<Favourites, Long>{

	Page<Favourites> findAllByCustomer(Customer customer, Pageable pageable);

	Favourites findByEstateAndCustomer(Estate estate, Customer customer);

}
