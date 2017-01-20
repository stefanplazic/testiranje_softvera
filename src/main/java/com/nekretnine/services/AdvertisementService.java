package com.nekretnine.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertisement.State;
import com.nekretnine.models.Customer;
import com.nekretnine.repository.AdvertisementRepository;

@Service
public class AdvertisementService {

	@Autowired
	AdvertisementRepository repository ;
	
	public Advertisement save(Advertisement a){
		return repository.save(a);
	}
	
	public Advertisement findOne(Long id){
		return repository.findOne(id);
	}
	
	public void setState(State state,Long id){
		repository.setState(state,id);
	}
	
	public void delete(Long id){
		repository.deleteById(id);
	}
	

	public Page<Advertisement> findAdvertisements(Date publicationDate, Date expiryDate, State state, String name, Double minPrice,
			Double maxPrice, Double minArea, Double maxArea, String address, String city, String cityPart, String technicalEquipment,
			String heatingSystem, Pageable pageable) {
		return repository.findAdvertisement(publicationDate, expiryDate, state, name, minPrice, maxPrice,
				minArea, maxArea, address, city, cityPart, technicalEquipment, heatingSystem, pageable);
	}
	
	public List<Advertisement> findAll() {
		return repository.findAll();
	}

	public Page<Advertisement> findAllBySoldto(Customer customer,
			Pageable pageable) {
	
		return repository.findAllBySoldto(customer, pageable);
	}
	
	public Long count() {
		return repository.count();
	}

	public int findBySoldto(Customer customer){
		return repository.findBySoldto(customer.getId());
	}
	
}
