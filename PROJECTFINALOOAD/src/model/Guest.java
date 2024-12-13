package model;

import java.sql.ResultSet;
import java.util.Vector;

import controller.InvitationController;
import controller.UserController;
import database.Connect;

public class Guest extends User{
	String accepted_invitations;
	
	private InvitationController ic = new InvitationController();
	private UserController uc = new UserController();
	public Guest() {
		// TODO Auto-generated constructor stub
	}
	public boolean acceptInvitation(String eventId, String userId) {
		return ic.acceptInvitation(eventId, userId);
	}
	public Vector<Event> viewAcceptedEvents(String email) {
		Vector<Event> eventList = new Vector<Event>();
		User user = uc.getUserByEmail(email);
	    try {
	        String query = String.format("SELECT * FROM invitations INNER JOIN events ON invitations.event_id = events.event_id WHERE user_id = '%s' AND invitation_status = '%s'", user.getUser_id(), "Accepted");
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
		Vector<Guest> guestList = new Vector<Guest>();
		Guest user;
	    try {
	        String query = String.format("SELECT * FROM invitations INNER JOIN users ON invitations.user_id = users.user_id WHERE (invitation_status = '%s' AND invitation_role = 'Guest') AND event_id='%s'", "Accepted",eventID);
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        while (rs.next()) {
	        	user = new Guest();
	            user.setUser_id(rs.getString("user_id"));
	            user.setUser_email(rs.getString("user_email"));
	            user.setUser_password(rs.getString("user_password"));
	            user.setUser_name(rs.getString("user_name"));
	            user.setUser_role(rs.getString("user_role"));
	            guestList.add(user);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return guestList;
	}
	
	

}
