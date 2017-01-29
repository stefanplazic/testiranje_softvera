package com.nekretnine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Estate;

public interface EstateRepository extends JpaRepository<Estate, Long> {

	public Estate findOneByName(String name);
	
	public List<Estate> findAllByOwnerId(Long owner_id);
	
}
