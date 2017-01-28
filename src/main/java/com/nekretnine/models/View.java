package com.nekretnine.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
/**
 * Class used to record every Advertisement view by a User into the database.
 * @author Nemanja Zunic
 * @see Advertisement, User
 */
@Entity
public class View {

	@Id
	@GeneratedValue
	public long id;
	
	@OneToOne
	@JoinColumn(name = "viewer")
	private User viewer;
	
	@OneToOne
	@JoinColumn(name = "advert")
	private Advertisement advert;
	
	@Column
	private Date time;
	
	public View() {
		super();
	}

	public View(User viewer, Advertisement advert, Date time) {
		super();
		this.viewer = viewer;
		this.advert = advert;
		this.time = time;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getViewer() {
		return viewer;
	}

	public void setViewer(User viewer) {
		this.viewer = viewer;
	}

	public Advertisement getAdvert() {
		return advert;
	}

	public void setAdvert(Advertisement advert) {
		this.advert = advert;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	
}
