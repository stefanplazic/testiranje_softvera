package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	Company findOneByNameAndAddress(String name, String address);

}
