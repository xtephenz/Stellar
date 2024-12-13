package model;

import java.sql.ResultSet;
import java.util.Vector;

import controller.InvitationController;
import controller.UserController;
import database.Connect;
import main.Session;

public class Vendor extends User {
	private String accepted_invitations;
	private InvitationController ic = new InvitationController();
	private UserController uc = new UserController();
	public void acceptInvitation(String eventID) {
		ic.acceptInvitation(eventID, this.getUser_id());
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
	public boolean manageVendor(String description, String product) {
		boolean isUpdated = false;

	    try {
	        String query = String.format("UPDATE `users` SET `product_name`='%s',`product_description`='%s' WHERE user_id='%s'", product, description, Session.getInstance().getUserSession().getUser_id());
	        int result = Connect.getInstance().execute(query);
	        isUpdated = result > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return isUpdated;
	}
	public String checkManageVendorInput(String description, String product) {
		if(description.equals("") || description.isEmpty()) {
			return "Description can not be empty!";
		} else if (product.equals("") || product.isEmpty()) {
			return "Product name can not be empty!";
		} else return "valid";
	}
	public Vector<Vendor> getVendorsByTransactionID(String eventID) {
		Vector<Vendor> vendorList = new Vector<Vendor>();
		Vendor user;
	    try {
	        String query = String.format("SELECT * FROM invitations INNER JOIN users ON invitations.user_id = users.user_id WHERE (invitation_status = '%s' AND invitation_role = 'Vendor') AND event_id = '%s'", "Accepted", eventID);
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        while (rs.next()) {
	        	user = new Vendor();
	            user.setUser_id(rs.getString("user_id"));
	            user.setUser_email(rs.getString("user_email"));
	            user.setUser_password(rs.getString("user_password"));
	            user.setUser_name(rs.getString("user_name"));
	            user.setUser_role(rs.getString("user_role"));
	            user.setProduct_name(rs.getString("product_name"));
	            user.setProduct_description(rs.getString("product_description"));
	            vendorList.add(user);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return vendorList;
	}
	
}
