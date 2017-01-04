package com.nekretnine.dto;

import java.util.HashSet;
import java.util.Set;

import com.nekretnine.models.Comment;
import com.nekretnine.models.Customer;

public class CustomerDTO {

	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private Set<CommentDTO> commentDTOs;
	
	public CustomerDTO() {
		super();
	}

	public CustomerDTO(String firstName, String lastName, String username, String email, Set<CommentDTO> commentDTOs) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.commentDTOs = commentDTOs;
	}


	public CustomerDTO(Customer customer) {
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
		this.username = customer.getUsername();
		this.email = customer.getEmail();
		this.commentDTOs = new HashSet<>();
		for(Comment comment: customer.getComments()){
			this.commentDTOs.add(new CommentDTO(comment));
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<CommentDTO> getCommentDTOs() {
		return commentDTOs;
	}

	public void setCommentDTOs(Set<CommentDTO> commentDTOs) {
		this.commentDTOs = commentDTOs;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
