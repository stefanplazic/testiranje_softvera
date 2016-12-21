package com.nekretnine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.nekretnine.models.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
	
	List<Report> findAllByStatus(String status);
	
	List<Report> findAllByOnHold(boolean onHold);
		
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Report r set r.status = ?1")
	void setStatusToAllReports(String status);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Report r set r.onHold = ?1 where r.id = ?2")
	int setOnHold(boolean onHold, long id);
}
