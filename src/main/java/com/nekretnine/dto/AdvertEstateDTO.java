package com.nekretnine.dto;

import java.util.Date;

/**
 * DTO class made so that we can return both advertisement and estate data in the same service call
 * @author Nemanja Zunic
 *
 */
public class AdvertEstateDTO {

	private Long advertisementId;
	private Long estateId;
	private String name;
	private double price;
	private double area;
	private String address;
	private String city;
	private String cityPart;
	private String technicalEquipment;
	private String heatingSystem;
	private Date publicationDate;
	private Date expiryDate;
	
	public AdvertEstateDTO() {
		super();
	}
	
	public AdvertEstateDTO(Long advertisementId, Long estateId, String name, double price, double area, String address,
			String city, String cityPart, String technicalEquipment, String heatingSystem, Date publicationDate,
			Date expiryDate) {
		super();
		this.advertisementId = advertisementId;
		this.estateId = estateId;
		this.name = name;
		this.price = price;
		this.area = area;
		this.address = address;
		this.city = city;
		this.cityPart = cityPart;
		this.technicalEquipment = technicalEquipment;
		this.heatingSystem = heatingSystem;
		this.publicationDate = publicationDate;
		this.expiryDate = expiryDate;
	}

	public Long getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
	}

	public Long getEstateId() {
		return estateId;
	}

	public void setEstateId(Long estateId) {
		this.estateId = estateId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "AdvertEstateDTO [advertisementId=" + advertisementId + ", estateId=" + estateId + ", name=" + name
				+ ", price=" + price + ", area=" + area + ", address=" + address + ", city=" + city + ", cityPart="
				+ cityPart + ", technicalEquipment=" + technicalEquipment + ", heatingSystem=" + heatingSystem
				+ ", publicationDate=" + publicationDate + ", expiryDate=" + expiryDate + "]";
	}
	
}
