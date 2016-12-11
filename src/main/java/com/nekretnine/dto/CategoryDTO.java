package com.nekretnine.dto;

import java.util.HashSet;
import java.util.Set;

import com.nekretnine.models.Category;
import com.nekretnine.models.Estate;

public class CategoryDTO {

	private Long id;
	private String description;
	private Set<EstateDTO> estates = new HashSet<EstateDTO>();
	
	public CategoryDTO() {}

	public CategoryDTO(Long id, String description, Set<EstateDTO> estates) {
		super();
		this.id = id;
		this.description = description;
		this.estates = estates;
	}

	public CategoryDTO(Category cat) {
		this.id = cat.getId();
		this.description = cat.getDescription();
		setEstates(cat.getEstates());
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

	public Set<EstateDTO> getEstates() {
		return estates;
	}

	public void setEstates(Set<?> estates) {
		this.estates = new HashSet<EstateDTO>();
		for(Object obj : estates) {
			if(obj instanceof EstateDTO) {
				this.estates.add((EstateDTO)obj);
			}
			else if (obj instanceof Estate) {
				this.estates.add(new EstateDTO((Estate)obj));
			}
		}
	}
	
}
