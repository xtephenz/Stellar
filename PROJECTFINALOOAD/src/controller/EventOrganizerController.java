package controller;

import java.sql.ResultSet;
import java.util.Vector;

import database.Connect;
import model.Event;
import model.EventOrganizer;
import model.Guest;
import model.Vendor;

public class EventOrganizerController {
	private EventOrganizer eo = new EventOrganizer();
	public EventOrganizerController() {
		// TODO Auto-generated constructor stub
	}
	public String createEvent(String eventName, String date, String location, String description, int organizerID){
		return eo.createEvent(eventName, date, location, description, organizerID);
	}
	public Vector<Event> viewOrganizedEvents(String userID) {
		// Disini nanti query di database table Events yang organizer_idnya == this.userID dari eventorganizer
		return eo.viewOrganizedEvents(userID);
	}
	public Event viewOrganizedEventDetails(String eventID) {
		// query di database table events where organizer_id == this.userID dan event_id == eventID
		return eo.viewOrganizedEventDetails(eventID);
	}
	public Vector<Guest> getGuests(String eventId) {
		//CONTROLLER: Ngelist guests di sebuah table yang !!DENGAN ASUMSI!! ada di view Event Details dari sisi event organizer. dengan tambahan checkbox untuk dapat di select dan di insert lewat button di view. (ASUMSI FRONT END)
		
		//MODEL: return vector semua guests yang ada.
		return eo.getGuests(eventId);
	}
	public Vector<Vendor> getVendors(String eventId) {
		//CONTROLLER: Ngelist vendors di sebuah table yang !!DENGAN ASUMSI!! ada di view Event Details dari sisi event organizer. dengan tambahan checkbox untuk dapat di select dan di insert lewat button di view. (ASUMSI FRONT END)
		
		//MODEL: return vector semua vendors yang ada.
		return eo.getVendors(eventId);
	}
	public Vector<Guest> getGuestByTransactionID(String eventID) {
		//CONTROLLER: Ngelist guests YANG  di sebuah table yang !!DENGAN ASUMSI!! ada di view Event Details dari sisi event organizer. dengan tambahan checkbox untuk dapat di select dan di insert lewat button di view. (ASUMSI FRONT END)
		
			//MODEL: return guests dari user_id dari table invitation yang mempunyai event_id == eventID dan invitation_status == 'Accepted'
		return eo.getGuestByTransactionID(eventID);
	}
	public Vector<Vendor> getVendorByTransactionID(String eventID) {
		//CONTROLLER: Ngelist vendor YANG  di sebuah table yang !!DENGAN ASUMSI!! ada di view Event Details dari sisi event organizer. dengan tambahan checkbox untuk dapat di select dan di insert lewat button di view. (ASUMSI FRONT END)
		
		//MODEL: return vendor dari user_id dari table invitation yang mempunyai event_id == eventID dan invitation_status == 'Accepted'
		return eo.getVendorByTransactionID(eventID);
	}

}
