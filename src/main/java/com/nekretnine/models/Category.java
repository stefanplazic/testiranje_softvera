package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String description;
	
	@OneToMany(mappedBy="category", fetch = FetchType.LAZY)
	private Set<Estate> estates = new HashSet<Estate>();
	
	public Category() {}

	public Category(Long id, String description, Set<Estate> estates) {
		super();
		this.id = id;
		this.description = description;
		this.estates = estates;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Estate> getEstates() {
		return estates;
	}

	public void setEstates(Set<Estate> estates) {
		this.estates = estates;
	}

}
