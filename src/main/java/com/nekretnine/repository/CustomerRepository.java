package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Customer;



public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
