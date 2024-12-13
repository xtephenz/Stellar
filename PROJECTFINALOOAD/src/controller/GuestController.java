package controller;

import java.sql.ResultSet;
import java.util.Vector;

import database.Connect;
import model.Event;
import model.Guest;
import model.User;

public class GuestController {
	private Guest guest = new Guest();
	public GuestController() {
		// TODO Auto-generated constructor stub
	}
	public boolean acceptInvitation(String eventId, String userId) {
		return guest.acceptInvitation(eventId, userId);
	}
	public Vector<Event> viewAcceptedEvents(String email) {
		return guest.viewAcceptedEvents(email);
	}
	public Vector<Guest> getGuestsByTransactionID(String eventID) {
		return guest.getGuestsByTransactionID(eventID);
	}
}
