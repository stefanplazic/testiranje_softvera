package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{
		Authority findByName(String name);
}
