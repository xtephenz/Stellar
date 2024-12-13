package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import controller.EventController;
import controller.GuestController;
import controller.VendorController;
import database.Connect;

public class EventOrganizer extends User{
	private String events_created;
	private EventController ec = new EventController();
	private GuestController gc = new GuestController();
	private VendorController vc = new VendorController();

	public String createEvent(String eventName, String date, String location, String description, int organizerID){
		return ec.createEvent(eventName, eventName, location, description, description);
	}
	public Vector<Event> viewOrganizedEvents(String userID) {
		// Disini nanti query di database table Events yang organizer_idnya == this.userID dari eventorganizer
		Vector<Event> eventList = new Vector<Event>();
	    try {
	        String query = String.format("SELECT * FROM events WHERE organizer_id = '%s'", userID);
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
	public Event viewOrganizedEventDetails(String eventID) {
		// query di database table events where organizer_id == this.userID dan event_id == eventID
		return ec.viewEventDetails(eventID);
	}
	public Vector<Guest> getGuests(String eventId) {
		//CONTROLLER: Ngelist guests di sebuah table yang !!DENGAN ASUMSI!! ada di view Event Details dari sisi event organizer. dengan tambahan checkbox untuk dapat di select dan di insert lewat button di view. (ASUMSI FRONT END)
		
		//MODEL: return vector semua guests yang ada.
		Vector<Guest> guestList = new Vector<Guest>();
		Guest user;
	    try {
	        String query = ("SELECT * FROM users WHERE user_role LIKE 'Guest'");
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        while (rs.next()) {
	        	user = new Guest();
	            user.setUser_id(rs.getString("user_id"));
	            user.setUser_email(rs.getString("user_email"));
	            user.setUser_password(rs.getString("user_password"));
	            user.setUser_name(rs.getString("user_name"));
	            user.setUser_role(rs.getString("user_role"));
	            if (checkUserInvite(user,  eventId)) {
	            	continue;
	            }
	            guestList.add(user);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return guestList;
	}
	public Vector<Vendor> getVendors(String eventId) {
		//CONTROLLER: Ngelist vendors di sebuah table yang !!DENGAN ASUMSI!! ada di view Event Details dari sisi event organizer. dengan tambahan checkbox untuk dapat di select dan di insert lewat button di view. (ASUMSI FRONT END)
		
		//MODEL: return vector semua vendors yang ada.
		Vector<Vendor> vendorList = new Vector<Vendor>();
		Vendor user;
	    try {
	        String query = String.format("SELECT * FROM users WHERE user_role = 'Vendor'");
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        while (rs.next()) {
	        	user = new Vendor();
	            user.setUser_id(rs.getString("user_id"));
	            user.setUser_email(rs.getString("user_email"));
	            user.setUser_password(rs.getString("user_password"));
	            user.setUser_name(rs.getString("user_name"));
	            user.setUser_role(rs.getString("user_role"));
	            if (checkUserInvite(user,  eventId)) {
	            	continue;
	            }
	            vendorList.add(user);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return vendorList;
	}
	public boolean checkUserInvite(User user, String eventId) {
		String secQuery = String.format(
        	    "SELECT * FROM users " +
        	    "JOIN invitations ON invitations.user_id = users.user_id " +
        	    "WHERE (invitation_status LIKE 'Accepted' OR invitation_status LIKE 'Pending') " +
        	    "AND (users.user_id = '%s' AND invitations.event_id = '%s')", 
        	    user.getUser_id(), eventId);
        ResultSet rs1 = Connect.getInstance().execQuery(secQuery);
        try {
			if(rs1.next()) {
//        	System.out.println("HELP ME!!");
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
        return false;
	}
	public Vector<Guest> getGuestByTransactionID(String eventID) {
		//CONTROLLER: Ngelist guests YANG  di sebuah table yang !!DENGAN ASUMSI!! ada di view Event Details dari sisi event organizer. dengan tambahan checkbox untuk dapat di select dan di insert lewat button di view. (ASUMSI FRONT END)
		
			//MODEL: return guests dari user_id dari table invitation yang mempunyai event_id == eventID dan invitation_status == 'Accepted'
		return gc.getGuestsByTransactionID(eventID);
	}
	public Vector<Vendor> getVendorByTransactionID(String eventID) {
		//CONTROLLER: Ngelist vendor YANG  di sebuah table yang !!DENGAN ASUMSI!! ada di view Event Details dari sisi event organizer. dengan tambahan checkbox untuk dapat di select dan di insert lewat button di view. (ASUMSI FRONT END)
		
		//MODEL: return vendor dari user_id dari table invitation yang mempunyai event_id == eventID dan invitation_status == 'Accepted'
		return vc.getVendorsByTransactionID(eventID);
	}

	
}
