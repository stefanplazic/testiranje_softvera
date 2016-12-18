package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Administrator;

public interface AdministratorRepository  extends JpaRepository<Administrator, Long>{

}
