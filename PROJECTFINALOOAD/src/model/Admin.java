package model;

import java.sql.ResultSet;
import java.util.Vector;

import controller.EventController;
import controller.GuestController;
import controller.VendorController;
import database.Connect;

public class Admin {
	private GuestController gc = new GuestController();
	private VendorController vc = new VendorController();
	private EventController ec = new EventController();
	public Admin() {
		// TODO Auto-generated constructor stub
	}
	public void viewAllEvents() {
		ec.viewAllEvents();
	}
	public void viewEventDetails(String eventID) {
		ec.viewEventDetails(eventID);
	}
	
	public boolean deleteEvent(String eventID) {
		boolean isDeleted = false;
	    try {
	        String query = "DELETE FROM events WHERE event_id = " + eventID;
	        int result = Connect.getInstance().execute(query);
	        isDeleted = result > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return isDeleted; 
	}
	
	public boolean deleteUser(String userID) {
		 boolean isDeleted = false;
		    try {
		        String query = "DELETE FROM users WHERE user_id = " + userID;
		        int result = Connect.getInstance().execute(query);
		        isDeleted = result > 0;
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return isDeleted; 
	}
	public Vector<User> getAllUsers() {
		Vector<User> userList = new Vector<>();

	    try {
	        String query = "SELECT * FROM users";
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        while (rs.next()) {
	            String userId = rs.getString("user_id");
	            String userEmail = rs.getString("user_email");
	            String userPassword = rs.getString("user_password");
	            String userName = rs.getString("user_name");
	            String userRole = rs.getString("user_role");
	            User user = new User();
	            user.setUser_email(userEmail);
	            user.setUser_id(userId);
	            user.setUser_name(userName);
	            user.setUser_password(userPassword);
	            user.setUser_role(userRole);
	            userList.add(user);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return userList;
	}
	public Vector<Event> getAllEvents() {
		Vector<Event> eventList = new Vector<Event>();
	    try {
	        String query = ("SELECT * FROM events");
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        while (rs.next()) {
	        	String eventDate = rs.getString("event_date");
	        	String eventDesc = rs.getString("event_description");
	        	String eventId = rs.getString("event_id");
	        	String eventLocation = rs.getString("event_location");
	        	String eventName = rs.getString("event_name");
	        	String organizerId = rs.getString("organizer_id");
	        	
	            Event event = new Event();
	            event.setEvent_date(eventDate);
	            event.setEvent_description(eventDesc);
	            event.setEvent_id(eventId);
	            event.setEvent_location(eventLocation);
	            event.setEvent_name(eventName);
	            event.setOrganizer_id(organizerId);
	            eventList.add(event);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return eventList; 
	}
	public Vector<Guest> getGuestsByTransactionID(String eventID) {
		return gc.getGuestsByTransactionID(eventID);
	}
	public Vector<Vendor> getVendorsByTransactionID(String eventID) {
		return vc.getVendorsByTransactionID(eventID);
	}

}
