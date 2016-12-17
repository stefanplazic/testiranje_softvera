package com.nekretnine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekretnine.models.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

}
