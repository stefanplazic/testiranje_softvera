package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.nekretnine.dto.AdvertisementDTO;
import com.nekretnine.dto.AdvertiserDTO;

@Entity
public class Advertiser extends User{

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Company company;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="advertiser")
	private Set<Advertisement> advertisements = new HashSet<Advertisement>();

	public Advertiser() {
		super();
	}

	public Advertiser(Company company, Set<Advertisement> advertisements) {
		super();
		this.company = company;
		this.advertisements = advertisements;
	}
	
	public Advertiser(AdvertiserDTO advdto) {
		super(advdto);
		this.company = new Company(advdto.getCompany());
		setAdvertisements(advdto.getAdvertisements());
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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
			else if (obj instanceof AdvertisementDTO) {
				this.advertisements.add(new Advertisement((AdvertisementDTO)obj));
			}
		}
	}
	
}
