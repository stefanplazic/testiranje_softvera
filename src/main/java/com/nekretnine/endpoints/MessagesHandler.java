package com.nekretnine.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nekretnine.dto.NotificationDTO;
import com.nekretnine.models.Notification;
import com.nekretnine.models.User;
import com.nekretnine.services.NotificationService;

public class MessagesHandler extends TextWebSocketHandler {

	@Autowired
	private NotificationService service;

	/**
	 * mile
	 */
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {

		Long id = Long.valueOf(message.getPayload());
		
		ObjectMapper mapper = new ObjectMapper();

		// send all notifications to client
		List<Notification> notifications = service.findByToUserAndNType(new User(id), "message");
		//notifications.addAll(service.findByFromUserAndNType(new User(id), "message"));
		List<NotificationDTO> dtos;
		if (!notifications.isEmpty()) {
			dtos = createDtoList(notifications);
			try {
				TextMessage msg = new TextMessage(
						mapper.writeValueAsString(dtos));
				session.sendMessage(msg);
				service.setNotificationsStatus("OLD", new User(id));
				notifications.clear();
				dtos.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// wait for new notifications and send
		while (session.isOpen()) {

			try {
				Thread.sleep(3000);
				notifications = service.findByToUserAndStatusAndNType(new User(id),
						"NEW", "message");
				
				if (!notifications.isEmpty()) {
					notifications.clear();
					notifications = service.findByToUserAndNType(new User(id), "message");
					//notifications.addAll(service.findByFromUserAndNType(new User(id), "message"));
					dtos = createDtoList(notifications);
					session.sendMessage(new TextMessage(mapper
							.writeValueAsString(dtos)));
					
					service.setNotificationsStatus("OLD", new User(id));
					notifications.clear();
					dtos.clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * mile
	 * @param notifications
	 * @return
	 */
	private List<NotificationDTO> createDtoList(
			List<Notification> notifications) {

		List<NotificationDTO> dtos = new ArrayList<>();
		for (Notification n : notifications) {
			NotificationDTO dto = new NotificationDTO(n);
			dtos.add(dto);
		}
		return dtos;
	}

}
