package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.nekretnine.dto.CommentDTO;
import com.nekretnine.dto.CustomerDTO;
import com.nekretnine.dto.RateDTO;

@Entity
public class Customer extends User {

	@Column(nullable = false)
	@OneToMany(mappedBy="customer", fetch = FetchType.LAZY)
	private Set<Rate> rates  = new HashSet<Rate>();

	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private Set<Comment> comments = new HashSet<Comment>();
	
	public Customer() {
		super();
	}

	public Customer(Set<Rate> rates, Set<Comment> comments) {
		super();
		this.rates = rates;
		this.comments = comments;
	}

	public Customer(CustomerDTO custdto) {
		super(custdto);
		setRates(custdto.getRates());
		setComments(custdto.getComments());
	}

	public Set<Rate> getRates() {
		return rates;
	}

	public void setRates(Set<?> rates) {
		this.rates = new HashSet<Rate>();
		for(Object obj : rates) {
			if(obj instanceof Rate) {
				this.rates.add((Rate)obj);
			}
			else if (obj instanceof RateDTO) {
				this.rates.add(new Rate((RateDTO)obj));
			}
		}
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<?> comments) {
		this.comments = new HashSet<Comment>();
		for(Object obj : comments) {
			if(obj instanceof Comment) {
				this.comments.add((Comment)obj);
			}
			else if (obj instanceof CommentDTO) {
				this.comments.add(new Comment((CommentDTO)obj));
			}
		}
	}
	
	
}
