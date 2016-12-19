package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertisement.State;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>,
AdvertisementRepositoryCustom {

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Advertisement a set a.state = ?1 where a.id = ?2")
	void setState(State status,Long id );
	
}
