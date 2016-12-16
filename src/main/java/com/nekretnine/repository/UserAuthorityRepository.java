package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long>{

}
