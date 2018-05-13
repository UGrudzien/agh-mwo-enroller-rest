package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/meetings")
public class MitingsRestController {

	@Autowired
	MeetingService meetingService;

	@RequestMapping(value = "", method = RequestMethod.GET)//dlaczego tu nie może być participant?
	public ResponseEntity<?> getParticipants() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") Long id) {
	     Meeting meeting = meetingService.findByLogin(id);
	     if (meeting == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	 }
	 @RequestMapping(value = "", method = RequestMethod.POST)
	 public ResponseEntity<?> registerMeetingt(@RequestBody Meeting meeting){
		 //sprawdzić czy nie istnieje
		 if ((meetingService.findByLogin(meeting.getId())!=null)){
			 return new ResponseEntity("Meeting with login: "+ meeting.getId(),
					 HttpStatus.CONFLICT);
		 }

		 // dodać/
		 meetingService.addMeeting(meeting);
		 //zwrócić
		 return new ResponseEntity<Meeting>(meeting,HttpStatus.OK);
		
		
	 }

	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<?> deleteMeeting(@PathVariable("id") Long id) {
		     Meeting meeting = meetingService.findByLogin(id);
		     if (meeting == null) {
		         return new ResponseEntity(HttpStatus.NOT_FOUND);
		     }
		     meetingService.deleteMeeting(meeting);
		     return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
		 }
	 
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity<?> updateMeeting(@PathVariable("id") Long id, @RequestBody Meeting updateMeeting) {
		     Meeting meeting = meetingService.findByLogin(id);
		     if (meeting == null) {
		         return new ResponseEntity(HttpStatus.NOT_FOUND);
		     }
		     meeting.setTitle(updateMeeting.getTitle());
		     meetingService.update(meeting);
		     return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
		 }

}
