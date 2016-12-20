package com.nekretnine.dto;

import java.util.Set;

import com.nekretnine.models.Estate;
import com.nekretnine.models.Image;

public class EstateDTO {
	
	private	long id; 
	private String name;
	private double price;
	private double area;
	private String address;
	private String city;
	private String cityPart;
	private String technicalEquipment;
	private String heatingSystem;
	private AdvertiserDTO owner;
	private Set<Image> images;

	
	
	public EstateDTO(){
		
	}
	
	public EstateDTO(long id,String name,double price,double area,String address,
			String city,String cityPart,String technicalEquipment,String heatingSystem){
		this.id=id;
		this.name=name;
		this.price=price;
		this.area=area;
		this.address=address;
		this.city=city;
		this.cityPart=cityPart;
		this.technicalEquipment=technicalEquipment;
		this.heatingSystem=heatingSystem;
	}
	


	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public AdvertiserDTO getOwner() {
		return owner;
	}

	public void setOwner(AdvertiserDTO owner) {
		this.owner = owner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public EstateDTO(Estate estate){
		this(estate.getId(),estate.getName(),estate.getPrice(),
				estate.getArea(),estate.getAddress(),estate.getCity(),estate.getCityPart(),
				estate.getTechnicalEquipment(),estate.getHeatingSystem());
		this.owner = new AdvertiserDTO(estate.getOwner());
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityPart() {
		return cityPart;
	}

	public void setCityPart(String cityPart) {
		this.cityPart = cityPart;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getHeatingSystem() {
		return heatingSystem;
	}

	public void setHeatingSystem(String heatingSystem) {
		this.heatingSystem = heatingSystem;
	}
	
		

}
