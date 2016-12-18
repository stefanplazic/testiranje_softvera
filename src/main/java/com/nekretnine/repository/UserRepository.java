package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.nekretnine.models.Company;
import com.nekretnine.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findOneByUsernameAndPassword(String username, String password);
	User findByUsername(String username);
	User findByEmail(String email);
	User findByVerifyCode(String verifyCode);
	
	@Query("select u.company from User u where u.id = ?1")
	Company findAdvertisersCompany(Long id);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update User u set u.company = ?1 where u.id = ?2")
	int setAdvertisersCompany(Company company, Long id);
}
