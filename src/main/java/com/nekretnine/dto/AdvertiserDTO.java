package com.nekretnine.dto;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.User;

public class AdvertiserDTO extends UserDTO{

	private CompanyDTO company;
	
	public AdvertiserDTO() {
		super();

	}

	public AdvertiserDTO(Long userId, String first_name, String last_name, String email, String username,
			String password) {
		super(userId, first_name, last_name, email, username, password);
	}

	public AdvertiserDTO(User user) {
		super(user);
		}
	
	public AdvertiserDTO(Advertiser advertiser) {
		super(advertiser);
		this.company = new CompanyDTO(advertiser.getCompany());
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	
}
