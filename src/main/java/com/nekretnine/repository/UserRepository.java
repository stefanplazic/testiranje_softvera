package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findOneByUsernameAndPassword(String username, String password);
	User findByUsername(String username);
}
