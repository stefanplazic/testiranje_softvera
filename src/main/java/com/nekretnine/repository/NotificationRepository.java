package com.nekretnine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.nekretnine.models.Notification;
import com.nekretnine.models.User;

public interface NotificationRepository extends
		JpaRepository<Notification, Long> {

	List<Notification> findByToUser(User toUser);

	List<Notification> findByToUserAndStatus(User toUser, String status);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Notification n set n.status = ?1 where n.toUser = ?2")
	int setNotificationsStatus(String status, User toUser);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Notification n set n.seen = ?1 where n.id = ?2")
	void setNotificationView(boolean seen, Long notificationId);

	List<Notification> findByToUserAndNType(User user, String type);

	List<Notification> findByToUserAndStatusAndNType(User user, String status,
			String type);

	List<Notification> findByFromUserAndNType(User user, String type);

}
