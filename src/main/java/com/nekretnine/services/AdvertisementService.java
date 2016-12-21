package com.nekretnine.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertisement.State;
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
	
	/*public List<AdvertEstateDTO> findAdvertisements(String query) {
		return repository.findAdvertisements(query);
	}*/
	
	public List<Advertisement> findAdvertisements(Date publicationDate, Date expiryDate, State state, String name, Double price, 
			Double area, String address, String city, String cityPart, String technicalEquipment, String heatingSystem) {
		return repository.findAdvertisement(publicationDate, expiryDate, state, name, price, area, address, city, cityPart,
				technicalEquipment, heatingSystem);
	}
	
}
