package com.nekretnine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nekretnine.models.Notification;
import com.nekretnine.models.User;
import com.nekretnine.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	NotificationRepository repository;
	
	public Notification saveNotification(Notification notification){
		return repository.save(notification);
	}
	
	public List<Notification> findByToUserAndStatus(User toUser, String status){
		return repository.findByToUserAndStatus(toUser, status);
	}
	
	public List<Notification> findByToUser(User toUser){
		return repository.findByToUser(toUser);
	}
	
	public void setNotificationsStatus(String status, User toUser){
		repository.setNotificationsStatus(status, toUser);
	}
	
	public void setNotificationView(boolean view, Long notificationId){
		repository.setNotificationView(view, notificationId);
	}

	public List<Notification> findByToUserAndNType(User user, String type) {
		return repository.findByToUserAndNType(user, type);
	}

	public List<Notification> findByToUserAndStatusAndNType(User user,
			String status, String type) {
		return repository.findByToUserAndStatusAndNType(user, status, type);
	}

	public List<Notification> findByFromUserAndNType(User user, String type) {
		return repository.findByFromUserAndNType(user, type);
	}


}
