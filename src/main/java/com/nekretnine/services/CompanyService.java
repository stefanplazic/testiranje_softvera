package com.nekretnine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.dto.CompanyDTO;
import com.nekretnine.models.Company;
import com.nekretnine.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	CompanyRepository repository;
	
	public Company findOne(long id){
		return repository.findOne(id);
	}
	
	public Company saveCompany(Company company){
		return repository.save(company);
	}
	
	public Company findOneByNameAndAddress(String name, String address) {
		return repository.findOneByNameAndAddress(name, address);
	}
	
	public List<Company> findAllByOnHold(boolean on_hold) {
		return repository.findAllByOnHold(on_hold);
	}

	public int modifyCompany(CompanyDTO companyDTO) {
		return repository.modifyCompany(companyDTO.getAddress(),
				companyDTO.getName()
				, companyDTO.getId());	
	}

	public void deleteCompanyById(long id) {
		repository.deleteCompanyById(id);
		
	}
	
	public int setStatusToAllCompanys(String status) {
		return repository.setStatusToAllCompanys(status);	
	}

	public List<Company> findAllByStatus(String status) {
		return repository.findAllByStatus(status);
	}

	public int setOnHold(boolean onHold, long id) {
		return repository.setOnHold(onHold, id);
	}

}
