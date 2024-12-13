package controller;

import java.sql.ResultSet;
import java.util.Vector;

import database.Connect;
import model.Event;
import model.User;
import model.Vendor;

public class VendorController {
	private Vendor vendor = new Vendor();
	public VendorController() {
		// TODO Auto-generated constructor stub
	}
	public void acceptInvitation(String eventID) {
		vendor.acceptInvitation(eventID);
	}
	public Vector<Event> viewAcceptedEvents(String email) {
		return vendor.viewAcceptedEvents(email);
	}
	public String manageVendor(String description, String product) {
		String inputStatus = checkManageVendorInput(description, product);
		if (!inputStatus.equals("valid")) {
			return inputStatus;
		} else if (vendor.manageVendor(description, product)) {
			return "Product successfully added/modified!";
		} else {
			return "Something went wrong";
		}
	}
	public String checkManageVendorInput(String description, String product) {
		return vendor.checkManageVendorInput(description, product);
	}
	public Vector<Vendor> getVendorsByTransactionID(String eventID) {
		return vendor.getVendorsByTransactionID(eventID);
	}
	

}
