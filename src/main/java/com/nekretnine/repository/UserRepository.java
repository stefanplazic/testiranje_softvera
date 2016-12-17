package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nekretnine.models.Company;
import com.nekretnine.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findOneByUsernameAndPassword(String username, String password);
	User findByUsername(String username);
	User findByEmail(String email);
	User findByVerifyCode(String verifyCode);
	
	@Query("select u.company from User u where u.id = ?1")
	Company findAdvertiserCompany(Long id);
}
