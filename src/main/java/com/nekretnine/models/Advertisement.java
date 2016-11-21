package com.nekretnine.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Advertisement {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private Date publicationDate;
	
	@Column(nullable = false)
	private Date expiryDate;
	
	@Column(nullable = false)
	private Date lastUpdate;
	
	@Column(nullable = false)
	private String contactInfo;
}
