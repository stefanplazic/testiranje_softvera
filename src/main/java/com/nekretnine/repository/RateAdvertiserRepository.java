package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Customer;
import com.nekretnine.models.RateAdvertiser;

public interface RateAdvertiserRepository extends JpaRepository<RateAdvertiser,Long> {

	

	RateAdvertiser findOneByAdvertiserRateAndCustomAdv(Advertiser id,Customer id2);
}
