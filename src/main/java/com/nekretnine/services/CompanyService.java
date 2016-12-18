package com.nekretnine.services;

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

	public int modifyCompany(CompanyDTO companyDTO) {
		return repository.modifyCompany(companyDTO.getAddress(),
				companyDTO.getName(), companyDTO.isOn_hold()
				, companyDTO.getId());
		
	}

	public void deleteCompanyById(long id) {
		repository.deleteCompanyById(id);
		
	}

}
