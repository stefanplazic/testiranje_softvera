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

import com.nekretnine.dto.AdvertiserDTO;
import com.nekretnine.dto.CompanyDTO;

@Entity
public class Company {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private boolean approved;
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER,mappedBy="company")
	private Set<Advertiser> members = new HashSet<Advertiser>();
	
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "owner")
	private Advertiser owner;
	
	public Company() {}

	public Company(Long id, String name, boolean approved, Set<Advertiser> members, Advertiser owner) {
		super();
		this.id = id;
		this.name = name;
		this.approved = approved;
		this.members = members;
		this.owner = owner;
	}
	
	public Company(CompanyDTO cmpdto) {
		this.id = cmpdto.getId();
		this.name = cmpdto.getName();
		this.approved = cmpdto.isApproved();
		setMembers(cmpdto.getMembers());
		this.owner = new Advertiser(cmpdto.getOwner());
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

	public Set<Advertiser> getMembers() {
		return members;
	}
	
	public void setMembers(Set<?> members) {
		this.members = new HashSet<Advertiser>();
		for(Object obj : members) {
			if(obj instanceof Advertiser) {
				this.members.add((Advertiser)obj);
			}
			else if (obj instanceof AdvertiserDTO) {
				this.members.add(new Advertiser((AdvertiserDTO)obj));
			}
		}
	}

	public Advertiser getOwner() {
		return owner;
	}

	public void setOwner(Advertiser owner) {
		this.owner = owner;
	}


}
