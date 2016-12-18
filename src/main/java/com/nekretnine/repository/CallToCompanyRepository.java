package com.nekretnine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.CallToCompany;
import com.nekretnine.models.User;

public interface CallToCompanyRepository extends JpaRepository<CallToCompany, Long>{
	List<CallToCompany> findByToadvrt(User toUser);

}
