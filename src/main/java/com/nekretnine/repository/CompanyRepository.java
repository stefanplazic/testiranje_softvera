package com.nekretnine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.nekretnine.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	Company findOneByNameAndAddress(String name, String address);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Company c set c.address = ?1, c.name = ?2"
			+ " where c.id = ?4")
	int modifyCompany(String address, String name, long id);

	
	@Modifying
	@Transactional
	@Query("delete from Company c where c.id = ?1")
	void deleteCompanyById(Long id);

	
	List<Company> findAllByOnHold(boolean onHold);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Company c set c.status = ?1")
	int setStatusToAllCompanys(String status);

	List<Company> findAllByStatus(String status);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Company c set c.onHold = ?1 where c.id = ?2")
	int setOnHold(boolean onHold, long id);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Company c set c.status = ?1 where c.id = ?2")
	int setStatus(String status, Long companyId);
	
}
