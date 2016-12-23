package com.nekretnine.controllers;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nekretnine.models.Moderator;
import com.nekretnine.models.Notification;
import com.nekretnine.models.Report;
import com.nekretnine.models.User;
import com.nekretnine.models.Advertisement.State;
import com.nekretnine.services.AdvertisementService;
import com.nekretnine.services.NotificationService;
import com.nekretnine.services.ReportService;
import com.nekretnine.services.UserService;

@RestController
@RequestMapping(value = "api/moderator")
public class ModeratorController {

	@Autowired
	public UserService userService;
	
	@Autowired
	public ReportService reportService;
	
	@Autowired
	public NotificationService notificationService;
	
	@Autowired
	public AdvertisementService advertisementService;
	
	/**
	 * Method used to accept Advertisement Report by a Moderator. Moderator responds by removing the Advertisement
	 * and setting Report status. Notification is sent to the User that made the report, informing him about 
	 * Moderators decision.
	 * @author Nemanja Zunic, Miodrag Vilotijevic
	 * @param principal containing Moderator's information
	 * @param reportId id of the Report being accepted by the Moderator
	 */
	@RequestMapping(value = "/acceptReport/{reportId}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> acceptReport(Principal principal, @PathVariable Long reportId){

		Moderator mod = (Moderator) userService.findByUsername(principal.getName());
		
		// modify onHold to false and save in database
		Report report = reportService.findOne(reportId);
		if(report == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else if(!report.isOnHold()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		report.setOnHold(false);
		reportService.save(report);
		//set reported advertisement's state to REMOVED and save in database
		advertisementService.setState(State.REMOVED, report.getAdvertisement().getId());
		
		User reporter = report.getUser();
		
		// send notification to the user
		Notification notification = new Notification();
		notification.setnType("info");
		notification.setToUser(reporter);
		notification.setMade(new Date());
		notification.setStatus("NEW");
		notification.setnType("Info");
		notification.setSeen(false);
		notification.setText("Moderator accepted your report of advertisement id: " 
				+ String.valueOf(report.getAdvertisement().getId()) + " that you reported because: " 
				+ report.getMessage() + ". Advertisement is removed.");
		notification.setFromUser(mod);
		notificationService.saveNotification(notification);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	/**
	 * Method used to reject Report by a Moderator. Moderator responds by setting Report status. 
	 * Notification is sent to the User that made the report, informing him about 
	 * Moderators decision.
	 * @author Miodrag Vilotijevic, Nemanja Zunic
	 * @param principal containing Moderators information
	 * @param reportId id of the Report being rejected by the Moderator
	 */
	@RequestMapping(value = "/rejectReport/{reportId}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> rejectReport(Principal principal, @PathVariable Long reportId){

		Moderator mod = (Moderator) userService.findByUsername(principal.getName());
		
		// modify onHold to false and save in database
		Report report = reportService.findOne(reportId);		
		if(report == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else if(!report.isOnHold()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		report.setOnHold(false);
		reportService.save(report);
		
		User reporter = report.getUser();
		
		// send notification to the user
		Notification notification = new Notification();
		notification.setnType("info");
		notification.setToUser(reporter);
		notification.setMade(new Date());
		notification.setStatus("NEW");
		notification.setSeen(false);
		notification.setText("Moderator rejected your report of advertisement id: " 
				+ String.valueOf(report.getAdvertisement().getId()) + " that you reported because: " 
				+ report.getMessage());
		notification.setFromUser(mod);
		notificationService.saveNotification(notification);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
