package com.nekretnine.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nekretnine.dto.CompanyDTO;
import com.nekretnine.models.Company;
import com.nekretnine.services.CompanyService;

public class AdminDashboardHandler extends TextWebSocketHandler{

	@Autowired
	private CompanyService service;
	
	/**
	 * mile
	 */
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {

		ObjectMapper mapper = new ObjectMapper();

		// send all requests for company to administrator
		List<Company> companys = service.findAllByOnHold(true);
		List<CompanyDTO> dtos;
		if (!companys.isEmpty()) {
			dtos = createDtoList(companys);
			try {
				TextMessage msg = new TextMessage(
						mapper.writeValueAsString(dtos));
				session.sendMessage(msg);
				service.setStatusToAllCompanys("OLD");
				companys.clear();
				dtos.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// wait for new requests and send to administrator
		while (session.isOpen()) {

			try {
				Thread.sleep(3000);
				companys = service.findAllByStatus("NEW");
				
				if (!companys.isEmpty()) {
					companys.clear();
					companys = service.findAllByOnHold(true);
					dtos = createDtoList(companys);
					session.sendMessage(new TextMessage(mapper
							.writeValueAsString(dtos)));
					Thread.sleep(3100);
					service.setStatusToAllCompanys("OLD");
					companys.clear();
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
	private List<CompanyDTO> createDtoList(
			List<Company> companys) {

		List<CompanyDTO> dtos = new ArrayList<>();
		for (Company c : companys) {
			CompanyDTO dto = new CompanyDTO(c);
			dtos.add(dto);
		}
		return dtos;
	}
}
