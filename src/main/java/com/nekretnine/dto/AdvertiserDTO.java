package com.nekretnine.dto;

import java.util.HashSet;
import java.util.Set;

import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.User;

public class AdvertiserDTO extends UserDTO{

	private CompanyDTO company;
	
	private Set<AdvertisementDTO> advertisements;
	
	public AdvertiserDTO() {
		super();
	}
	
	public AdvertiserDTO(CompanyDTO company, Set<AdvertisementDTO> advertisements) {
		super();
		this.company = company;
		this.advertisements = advertisements;
	}

	public AdvertiserDTO(Advertiser advertiser) {
		super(advertiser);
		this.company = new CompanyDTO(advertiser.getCompany());
		this.advertisements = new HashSet<AdvertisementDTO>();
		for(Advertisement ad : advertiser.getAdvertisements()) {
			advertisements.add(new AdvertisementDTO(ad));
		}
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public Set<AdvertisementDTO> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Set<AdvertisementDTO> advertisements) {
		this.advertisements = advertisements;
	}

}
