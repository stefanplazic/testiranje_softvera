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
	
	void setNotificationView(boolean view, Long notification_id){
		repository.setNotificationView(view, notification_id);
	}

}
