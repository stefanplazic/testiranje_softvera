package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.nekretnine.dto.AdvertisementDTO;
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
	private String technicalEquipment;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "images")
	private Set<String> images = new HashSet<String>();

	@Column(nullable = false)
	private String heatingSystem;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	private Category category;
	
	@OneToOne
	@JoinColumn(name = "location")
	private Location location;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estate", targetEntity=Advertisement.class)
	private Set<Advertisement> advertisements = new HashSet<Advertisement>();
	
	public Estate() {}

	public Estate(Long id, String name, double price, double area, String technicalEquipment, Set<String> images,
			String heatingSystem, Category category, Location location, Set<Advertisement> advertisements) {
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

	public Estate(EstateDTO estdto) {
		this.id = estdto.getId();
		this.name = estdto.getName();
		this.price = estdto.getPrice();
		this.area = estdto.getArea();
		this.technicalEquipment = estdto.getTechnicalEquipment();
		this.images = estdto.getImages();
		this.heatingSystem = estdto.getHeatingSystem();
		this.category = new Category(estdto.getCategory());
		this.location = new Location(estdto.getLocation());
		setAdvertisements(estdto.getAdvertisements());
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Set<Advertisement> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Set<?> advs) {
		this.advertisements = new HashSet<Advertisement>();
		for(Object obj : advs) {
			if(obj instanceof Advertisement) {
				this.advertisements.add((Advertisement)obj);
			}
			else if(obj instanceof AdvertisementDTO) {
				this.advertisements.add(new Advertisement((AdvertisementDTO)obj));
			}
		}
	}

}
