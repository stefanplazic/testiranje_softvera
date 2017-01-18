package com.nekretnine.constants;

import java.util.Date;

import com.nekretnine.models.Advertisement.State;

public class AdvertisementConstraints {

	public static final Date PUBLICATION_DATE = new Date();
	
	public static final Date EXPIRY_DATE = new Date();
	
	public static final State STATE = State.OPEN;
	
	public static final int INITIAL_SIZE = 3;
	
	public static final Long ADVERT_ID = 1L;
	
	public static final State INITIAL_STATE = State.OPEN;
	
	public static final State NEW_STATE = State.RENTED;
	
	public static final String PUBLICATION_DATE_INITIAL = "2016-12-01 00:00:00";
	
	public static final String EXPIRY_DATE_INITIAL = "2017-02-01 12:00:00";
	
	
}
