package com.nekretnine.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertisement.State;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Advertisement a set a.state = ?1 where a.id = ?2")
	void setState(State status,Long id );
	
	/**
	 * HPA query for searching advertisements by user provided input parameters.
	 * @return List of adverts that fit the search criteria.
	 * @author Nemanja Zunic
	 */
	@Modifying
	@Transactional
    @Query("delete from Advertisement a where a.id = ?1")
	void deleteById(Long id);
	
	@Query("select a from Advertisement as a join a.estate as e WHERE "
			+ "(:publicationDate is null or a.publicationDate = :publicationDate) AND "
			+ "(:expiryDate is null or a.expiryDate = :expiryDate) AND "
			+ "(:state is null or a.state = :state) AND "
			+ "(:name is null or e.name = :name) AND "
			+ "(:price is null or e.price = :price) AND "
			+ "(:area is null or e.area = :area) AND "
			+ "(:address is null or e.address = :address) AND "
			+ "(:city is null or e.city = :city) AND "
			+ "(:cityPart is null or e.cityPart = :cityPart) AND "
			+ "(:technicalEquipment is null or e.technicalEquipment = :technicalEquipment) AND "
			+ "(:heatingSystem is null or e.heatingSystem = :heatingSystem)")
	List<Advertisement> findAdvertisement(@Param("publicationDate") Date publicationDate, @Param("expiryDate") Date expiryDate,
			@Param("state") State state, @Param("name") String name, @Param("price") Double price, @Param("area") Double area,
			@Param("address") String address, @Param("city") String city, @Param("cityPart") String cityPart,
			@Param("technicalEquipment") String technicalEquipment, @Param("heatingSystem") String heatingSystem);
}
