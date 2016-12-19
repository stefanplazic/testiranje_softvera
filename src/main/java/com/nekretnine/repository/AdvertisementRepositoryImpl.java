package com.nekretnine.repository;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.nekretnine.dto.AdvertEstateDTO;

/**
 * Implementation of methods declared in AdvertisementRepositoryCustom. Each method is one of 
 * custom written SQL querys. 
 * @see AdvertisementRepositoryCustom
 * @author Nemanja Zunic
 *
 */
public class AdvertisementRepositoryImpl implements AdvertisementRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Implementation of method used to search for advertisement - estate using provided user criteria.
	 * Method uses EntityManager to execute custom written SQL query and return the result.
	 * @param SQL query used to find advertisement - estate pairs in the database.
	 * @return List of AdvertEstateDTOs that fit the search criteria provided by user @see AdvertEstateDTO @see Advertisement @see Estate
	 */
	@Override
	public List<AdvertEstateDTO> findAdvertisements(String query) {
		Query q = em.createNativeQuery(query);
		List<AdvertEstateDTO> resultArray = new ArrayList<AdvertEstateDTO>();
		@SuppressWarnings("unchecked")
		List<Object[]> result = q.getResultList();
		for(Object[] a : result) {
			Date date1 = Date.valueOf(((Timestamp)a[10]).toLocalDateTime().toLocalDate());
			Date date2 = Date.valueOf(((Timestamp)a[11]).toLocalDateTime().toLocalDate());
			resultArray.add(new AdvertEstateDTO(((BigInteger)a[0]).longValue(), ((BigInteger)a[1]).longValue(),
					String.valueOf(a[2]), (Double)a[3], (Double)a[4], String.valueOf(a[5]), String.valueOf(a[6]),
					String.valueOf(a[7]), String.valueOf(a[8]), String.valueOf(a[9]), date1, date2));
		}
		return resultArray;
	}

}
