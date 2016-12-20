package com.nekretnine.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
		Account findByAcountNumber(String acountNumber);
}
