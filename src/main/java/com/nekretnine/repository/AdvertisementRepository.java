package com.nekretnine.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertisement.State;
import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Customer;

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
			+ "(:publicationDate is null or a.publicationDate > :publicationDate) AND "
			+ "(:expiryDate is null or a.expiryDate < :expiryDate) AND "
			+ "(:state is null or a.state = :state) AND "
			+ "(:name is null or e.name LIKE :name) AND "
			+ "(:minPrice = 0.0 or e.price > :minPrice) AND "
			+ "(:maxPrice = 0.0 or e.price < :maxPrice) AND "
			+ "(:minArea = 0.0 or e.area > :minArea) AND "
			+ "(:maxArea = 0.0 or e.area < :maxArea) AND "
			+ "(:address is null or e.address = :address) AND "
			+ "(:city is null or e.city = :city) AND "
			+ "(:cityPart is null or e.cityPart = :cityPart) AND "
			+ "(:technicalEquipment is null or e.technicalEquipment = :technicalEquipment) AND "
			+ "(:heatingSystem is null or e.heatingSystem = :heatingSystem)")
	Page<Advertisement> findAdvertisement(@Param("publicationDate") Date publicationDate, @Param("expiryDate") Date expiryDate,
			@Param("state") State state, @Param("name") String name, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
			@Param("minArea") Double minArea, @Param("maxArea") Double maxArea, @Param("address") String address, @Param("city") String city, @Param("cityPart") String cityPart,
			@Param("technicalEquipment") String technicalEquipment, @Param("heatingSystem") String heatingSystem, Pageable pageable);

	Page<Advertisement> findAllBySoldto(Customer customer, Pageable pageable);
	
	@Query("select count(id) from Advertisement where soldTo = ?1")
	int findBySoldto(Long id);
	
	@Query("select count(a.state) from Advertisement as a join a.estate as e WHERE "
			+ "(:publicationDate is null or a.publicationDate > :publicationDate) AND "
			+ "(:expiryDate is null or a.expiryDate < :expiryDate) AND "
			+ "(:state is null or a.state = :state) AND "
			+ "(:name is null or e.name LIKE :name) AND "
			+ "(:minPrice = 0.0 or e.price > :minPrice) AND "
			+ "(:maxPrice = 0.0 or e.price < :maxPrice) AND "
			+ "(:minArea = 0.0 or e.area > :minArea) AND "
			+ "(:maxArea = 0.0 or e.area < :maxArea) AND "
			+ "(:address is null or e.address = :address) AND "
			+ "(:city is null or e.city = :city) AND "
			+ "(:cityPart is null or e.cityPart = :cityPart) AND "
			+ "(:technicalEquipment is null or e.technicalEquipment = :technicalEquipment) AND "
			+ "(:heatingSystem is null or e.heatingSystem = :heatingSystem)")
	int countAdverts(@Param("publicationDate") Date publicationDate, @Param("expiryDate") Date expiryDate,
			@Param("state") State state, @Param("name") String name, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
			@Param("minArea") Double minArea, @Param("maxArea") Double maxArea, @Param("address") String address, @Param("city") String city, @Param("cityPart") String cityPart,
			@Param("technicalEquipment") String technicalEquipment, @Param("heatingSystem") String heatingSystem);
	
	List<Advertisement> findAllByAdvertiser(Advertiser advertiser);
}
