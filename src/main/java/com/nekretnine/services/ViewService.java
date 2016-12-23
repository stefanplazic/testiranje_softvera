package com.nekretnine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Customer;
import com.nekretnine.models.View;
import com.nekretnine.repository.ViewRepository;

@Service
public class ViewService {

	@Autowired
	private ViewRepository repository;
	
	public View save(View view) {
		return repository.save(view);
	}
	
	public List<View> findViewsByViewer(Customer viewer) {
		return repository.findTop10ViewsByViewerOrderByTimeDesc(viewer);
	}
}
