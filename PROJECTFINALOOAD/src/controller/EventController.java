package controller;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Vector;

import database.Connect;
import model.Event;

public class EventController {
	Event event = new Event();
	LocalDate today = LocalDate.now();
	public EventController() {
		// TODO Auto-generated constructor stub
	}
	public String createEvent(String eventName, String date, String location, String description, String organizerID) {
		try {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Adjusted format
	        LocalDate eventDate = LocalDate.parse(date, formatter);

	        if (!eventDate.isAfter(today)) { // Ensure the date is in the future
	            return "Date must be in the future!";
	        }
	    } catch (DateTimeParseException e) {
	        return "Invalid date format! Please use dd/MM/yyyy.";
	    }

		if (eventName.equals("")) {
			return "Event name can not be empty!";
		} else if (date.equals(today)) {
			return "Date must be in the future!";
		} else if (location.equals("")) {
			return "Location can not be empty!";
		} else if (location.length()<5) {
			return "Location must be minimum length of 5 characters!";
		} else if (description.equals("")) {
			return "Description can not be empty!";
		} else if (description.length()>200) {
			return "Description has a maximum length of 200 characters!";
		} else if(!event.createEvent(eventName, date, location, description, organizerID)){
			return "Something went wrong!";
		}
		return "Event successfully created!";
	}
	public Event viewEventDetails(String eventID) {
		return event.viewEventDetails(eventID);
	}
	public Vector<Event> viewAllEvents(){
		return event.viewAllEvents();
	}
	public Event getEventById(String event_id) {
		return event.getEventById(event_id);
	}
}
