package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.nekretnine.dto.CategoryDTO;
import com.nekretnine.dto.EstateDTO;

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

	public Category(CategoryDTO catdto) {
		this.id = catdto.getId();
		this.description = catdto.getDescription();
		setEstates(catdto.getEstates());
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

	public void setEstates(Set<?> est) {
		this.estates = new HashSet<Estate>();
		for(Object obj : est) {
			if(obj instanceof Estate) {
				this.estates.add((Estate)obj);
			}
			else if (obj instanceof EstateDTO) {
				this.estates.add(new Estate((EstateDTO) obj));
			}
		}
	}

	
	
}
