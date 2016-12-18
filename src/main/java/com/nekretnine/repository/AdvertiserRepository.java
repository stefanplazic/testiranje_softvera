package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Company;

public interface AdvertiserRepository extends JpaRepository<Advertiser, Long>{
	

	@Query("select a.company from Advertiser a where a.id = ?1")
	Company findAdvertisersCompany(Long id);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Advertiser a set a.company = ?1 where a.id = ?2")
	int setAdvertisersCompany(Company company, Long id);

}
