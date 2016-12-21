package com.nekretnine.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nekretnine.dto.ReportDTO;
import com.nekretnine.models.Report;
import com.nekretnine.services.ReportService;

/**
 * 
 * @author Miodrag Vilotijevic, Nemanja Zunic
 *
 */
public class ModeratorDashboardHandler extends TextWebSocketHandler {

	@Autowired
	private ReportService service;
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {

		ObjectMapper mapper = new ObjectMapper();

		// send all requests for reports to moderator
		List<Report> reports = service.findAllByOnHold(true);
		List<ReportDTO> dtos;
		if (reports.size() != 0) {
			dtos = createDtoList(reports);
			try {
				TextMessage msg = new TextMessage(
						mapper.writeValueAsString(dtos));
				session.sendMessage(msg);
				service.setStatusToAllReports("OLD");
				reports.clear();
				dtos.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// wait for new reports and send to moderator
		while (session.isOpen()) {

			try {
				Thread.sleep(10000);
				reports = service.findAllByStatus("NEW");
				
				if (reports.size() != 0) {
					dtos = createDtoList(reports);
					session.sendMessage(new TextMessage(mapper
							.writeValueAsString(dtos)));
					service.setStatusToAllReports("OLD");
					reports.clear();
					dtos.clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private List<ReportDTO> createDtoList(List<Report> reports) {

		List<ReportDTO> dtos = new ArrayList<>();
		for (Report r : reports) {
			dtos.add(new ReportDTO(r));
		}
		return dtos;
	}
}
