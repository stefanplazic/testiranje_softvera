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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.nekretnine.dto.CompanyDTO;

@Entity
public class Company {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private boolean onHold;
	
	@Column(nullable = true)
	private String status;
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER,mappedBy="company")
	private Set<Advertiser> members = new HashSet<Advertiser>();
	
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "owner")
	private Advertiser owner;
	
	public Company() {}

	public Company(Long id, String name, String address,
			String status, Set<Advertiser> members, Advertiser owner) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.onHold = true;
		this.status = status;
		this.members = members;
		this.owner = owner;
	}



	public Company(CompanyDTO companyDTO) {
		id = companyDTO.getId();
		name = companyDTO.getName();
		address = companyDTO.getAddress();
		onHold = true;
		status = "NEW";
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

	public boolean isonHold() {
		return onHold;
	}

	public void setonHold(boolean onHold) {
		this.onHold = onHold;
	}

	public Set<Advertiser> getMembers() {
		return members;
	}

	public void setMembers(Set<Advertiser> members) {
		this.members = members;
	}

	public Advertiser getOwner() {
		return owner;
	}

	public void setOwner(Advertiser owner) {
		this.owner = owner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String isStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", address=" + address
				+ ", onHold=" + onHold + ", members=" + members + ", owner="
				+ owner + "]";
	}
	
	


}
