package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Estate;

public interface EstateRepository extends JpaRepository<Estate, Long> {

	public Estate findOneByName(String name);
	
}
