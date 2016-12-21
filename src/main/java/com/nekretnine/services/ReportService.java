package com.nekretnine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Report;
import com.nekretnine.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	ReportRepository repository;
	
	public Report findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Report> findAll() {
		return repository.findAll();
	}
	
	public Report save(Report report) {
		return repository.save(report);
	}

	public void remove(Long id) {
		repository.delete(id);
	}
	
	public List<Report> findAllByStatus(String status) {
		return repository.findAllByStatus(status);
	}

	public List<Report> findAllByOnHold(boolean onHold) {
		return repository.findAllByOnHold(onHold);
	}
	
	public void setStatusToAllReports(String status) {
		repository.setStatusToAllReports(status);	
	}
	
	public void setOnHold(boolean onHold, Long reportId) {
		repository.setOnHold(onHold, reportId);
	}
	
}

