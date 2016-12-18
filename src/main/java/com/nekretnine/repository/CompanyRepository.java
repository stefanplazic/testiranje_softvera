package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.nekretnine.dto.CompanyDTO;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	Company findOneByNameAndAddress(String name, String address);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Company c set c.address = ?1, c.name = ?2, c.on_hold = ?3"
			+ " where c.id = ?4")
	int modifyCompany(String address, String name, boolean on_hold, long id);
	

}
