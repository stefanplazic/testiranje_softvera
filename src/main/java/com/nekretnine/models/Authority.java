package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Authority {
	@Id
	@GeneratedValue
	private Long id;
	
	String name;
	
	@OneToMany(mappedBy = "authority", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<UserAuthority> userAuthorities = new HashSet<>();

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

	public Set<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	@Override
	public String toString() {
		return "Authority [id=" + id + ", name=" + name + ", userAuthorities=" + userAuthorities + "]";
	}

}
