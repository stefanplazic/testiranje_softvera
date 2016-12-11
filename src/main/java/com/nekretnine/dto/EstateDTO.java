package com.nekretnine.dto;

import java.util.HashSet;
import java.util.Set;

import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Estate;

public class EstateDTO {

	private Long id;
	private String name;
	private double price;
	private double area;
	private String technicalEquipment;
	private Set<String> images = new HashSet<String>();
	private String heatingSystem;
	private CategoryDTO category;
	private LocationDTO location;
	private Set<AdvertisementDTO> advertisements = new HashSet<AdvertisementDTO>();
	
	public EstateDTO() {}

	public EstateDTO(Long id, String name, double price, double area, String technicalEquipment, Set<String> images,
			String heatingSystem, CategoryDTO category, LocationDTO location, Set<AdvertisementDTO> advertisements) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.area = area;
		this.technicalEquipment = technicalEquipment;
		this.images = images;
		this.heatingSystem = heatingSystem;
		this.category = category;
		this.location = location;
		this.advertisements = advertisements;
	}

	public EstateDTO(Estate estate) {
		this.id = estate.getId();
		this.name = estate.getName();
		this.price = estate.getPrice();
		this.area = estate.getArea();
		this.technicalEquipment = estate.getTechnicalEquipment();
		this.images = estate.getImages();
		this.heatingSystem = estate.getHeatingSystem();
		this.category = new CategoryDTO(estate.getCategory());
		this.location = new LocationDTO(estate.getLocation());
		for(Advertisement adv : estate.getAdvertisements()) {
			this.advertisements.add(new AdvertisementDTO(adv));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public String getTechnicalEquipment() {
		return technicalEquipment;
	}

	public void setTechnicalEquipment(String technicalEquipment) {
		this.technicalEquipment = technicalEquipment;
	}

	public Set<String> getImages() {
		return images;
	}

	public void setImages(Set<String> images) {
		this.images = images;
	}

	public String getHeatingSystem() {
		return heatingSystem;
	}

	public void setHeatingSystem(String heatingSystem) {
		this.heatingSystem = heatingSystem;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public Set<AdvertisementDTO> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Set<AdvertisementDTO> advertisements) {
		this.advertisements = advertisements;
	}
	
}
