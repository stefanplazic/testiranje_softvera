package com.nekretnine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nekretnine.models.User;
import com.nekretnine.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public User findOne(Long id) {
		return repository.findOne(id);
	}

	public List<User> findAll() {
		return repository.findAll();
	}
	
	public Page<User> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public User save(User user) {
		return repository.save(user);
	}

	public void remove(Long id) {
		repository.delete(id);
	}
	
	public User findOneByUsernameAndPassword(String username, String password) {
		return repository.findOneByUsernameAndPassword(username, password);
	}
	
	public User findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}
}
