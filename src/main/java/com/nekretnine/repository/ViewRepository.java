package com.nekretnine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Customer;
import com.nekretnine.models.View;

public interface ViewRepository extends JpaRepository<View, Long> {
	
	public List<View> findTop10ViewsByViewerOrderByTimeDesc(Customer viewer);
}