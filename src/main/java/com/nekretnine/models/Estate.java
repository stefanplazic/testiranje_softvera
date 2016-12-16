package com.nekretnine.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.nekretnine.dto.EstateDTO;

@Entity
public class Estate {
	
	@Id
	@GeneratedValue
	@Column (name = "id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private double price;

	@Column(nullable = false)
	private double area;

	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	private String cityPart;
	
	@Column(nullable = false)
	private String technicalEquipment;

	@Column(nullable = false)
	private String heatingSystem;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Advertiser owner;
	
	
	public Estate() {}

	public Estate(Long id, String name, double price, double area,String address,String city,
			String cityPart,String technicalEquipment,
			String heatingSystem,Advertiser owner) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.area = area;
		this.address=address;
		this.city=city;
		this.cityPart=cityPart;
		this.technicalEquipment = technicalEquipment;		
		this.heatingSystem = heatingSystem;
		
		this.owner=owner;
		
		
	}

	public Estate(EstateDTO estate){
		this(estate.getId(),estate.getName(),estate.getPrice(),
				estate.getArea(),estate.getAddress(),estate.getCity(),estate.getCityPart(),estate.getTechnicalEquipment(),
				estate.getHeatingSystem(),estate.getOwner());
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

	public Advertiser getOwner() {
		return owner;
	}

	public void setOwner(Advertiser owner) {
		this.owner = owner;
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

	public String getHeatingSystem() {
		return heatingSystem;
	}

	public void setHeatingSystem(String heatingSystem) {
		this.heatingSystem = heatingSystem;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

}
