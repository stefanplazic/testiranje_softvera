package com.nekretnine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.models.Notification;
import com.nekretnine.services.NotificationService;

@RestController
@RequestMapping(value = "/api/notifications")
public class NotificationController {
	
	@Autowired
	private NotificationService service;
	
	/**
	 * mile
	 * @param notification
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Notification> saveUser(@RequestBody Notification notification){
		
		notification = service.saveNotification(notification);
		return new ResponseEntity<>(notification, HttpStatus.CREATED);	
	}
}
