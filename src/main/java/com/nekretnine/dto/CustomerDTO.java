package com.nekretnine.dto;

import java.util.HashSet;
import java.util.Set;

import com.nekretnine.models.Comment;
import com.nekretnine.models.Customer;
import com.nekretnine.models.Rate;

public class CustomerDTO extends UserDTO {

	private Set<RateDTO> rates = new HashSet<RateDTO>();
	private Set<CommentDTO> comments = new HashSet<CommentDTO>();

	public CustomerDTO() {}

	public CustomerDTO(Set<RateDTO> rates, Set<CommentDTO> comments) {
		super();
		this.rates = rates;
		this.comments = comments;
	}
	
	public CustomerDTO(Customer customer) {
		super(customer);		
		for(Rate rate : customer.getRates()) {
			rates.add(new RateDTO(rate));
		}
		
		for(Comment comment : customer.getComments()) {
			comments.add(new CommentDTO(comment));
		}
	}

	public Set<RateDTO> getRates() {
		return rates;
	}

	public void setRates(Set<RateDTO> rates) {
		this.rates = rates;
	}

	public Set<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}
	
}
