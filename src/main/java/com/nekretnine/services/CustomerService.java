package com.nekretnine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Customer;
import com.nekretnine.repository.CustomerRepository;



@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;
	
	public Customer findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Customer> findAll() {
		return repository.findAll();
	}
	
	public Page<Customer> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Customer save(Customer user) {
		return repository.save(user);
	}

	public void remove(Long id) {
		repository.delete(id);
	}
	
}
