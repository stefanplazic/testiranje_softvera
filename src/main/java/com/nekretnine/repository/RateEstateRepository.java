package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Customer;
import com.nekretnine.models.Estate;
import com.nekretnine.models.RateEstate;

public interface RateEstateRepository extends JpaRepository<RateEstate,Long> {

	RateEstate findOneByEstateAndCustomer(Estate id,Customer id2);
}
