package com.nekretnine.dto;

import com.nekretnine.models.Image;

public class ImageDTO {

	private Long id;
	private String url;
	private EstateDTO estate;
	
	public ImageDTO(){
		
	}
	public ImageDTO(Long id,String url,EstateDTO estate){
		this.id=id;
		this.url=url;
		this.estate=estate;
	}
	
	public ImageDTO(Image img){
		this(img.getId(),img.getUrl(),null);
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
	public EstateDTO getEstate() {
		return estate;
	}
	public void setEstate(EstateDTO estate) {
		this.estate = estate;
	}
	
}
