package com.nekretnine.dto;

public class PageableDTO {

	private int page;
	private int count;
	
	public PageableDTO(){}

	public PageableDTO(int page, int count) {
		super();
		this.page = page;
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	
	
}
