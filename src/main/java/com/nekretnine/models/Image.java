package com.nekretnine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Image {

	@Id
	@GeneratedValue
	@Column (name = "id")
	private Long id;
	
	@Column(nullable=false)
	private String url;
	
	@ManyToOne
    @JoinColumn(name = "estate")
	private Estate estate;
	
	public Image(){
		
	}
	
	public Image(Long id,String url,Estate estate){
		this.id=id;
		this.url=url;
		this.estate=estate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}
	
}
