package com.nekretnine.repository;

import java.util.List;
import com.nekretnine.dto.AdvertEstateDTO;

/**
 * Interface used to define custom-made SQL querys. 
 * @see Konstrikcija i Testiranje/Predavanja/02/Spring radni okvir/page 82
 * @author Nemanja Zunic
 */
public interface AdvertisementRepositoryCustom {

	public List<AdvertEstateDTO> findAdvertisements(String query);
}
