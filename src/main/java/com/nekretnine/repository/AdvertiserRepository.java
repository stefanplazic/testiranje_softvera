package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nekretnine.models.Advertiser;

public interface AdvertiserRepository extends JpaRepository<Advertiser, Long> {

}
