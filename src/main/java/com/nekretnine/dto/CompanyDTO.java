package com.nekretnine.dto;

import java.util.HashSet;
import java.util.Set;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Company;

public class CompanyDTO {

	private Long id;
	private String name;
	private boolean approved;
	private Set<AdvertiserDTO> members = new HashSet<AdvertiserDTO>();
	private AdvertiserDTO owner;
	
	public CompanyDTO() {}
	
	public CompanyDTO(Long id, String name, boolean approved, Set<AdvertiserDTO> members, AdvertiserDTO owner) {
		super();
		this.id = id;
		this.name = name;
		this.approved = approved;
		this.members = members;
		this.owner = owner;
	}



	public CompanyDTO(Company company) {
		this.id = company.getId();
		this.name = company.getName();
		this.approved = company.isApproved();
		for(Advertiser adv : company.getMembers()) {
			this.members.add(new AdvertiserDTO(adv));
		}
		this.owner = new AdvertiserDTO(company.getOwner());
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

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Set<AdvertiserDTO> getMembers() {
		return members;
	}

	public void setMembers(Set<AdvertiserDTO> members) {
		this.members = members;
	}

	public AdvertiserDTO getOwner() {
		return owner;
	}

	public void setOwner(AdvertiserDTO owner) {
		this.owner = owner;
	}
	
	
	
	
	
}
