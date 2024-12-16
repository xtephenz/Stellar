package controller;
import java.util.Vector;
import model.Admin;
public class AdminController {
	private Admin admin = new Admin();
	private UserController uc = new UserController();
	private EventController ec = new EventController();
	public AdminController() {
		// TODO Auto-generated constructor stub
		
	}
	public void viewAllEvents() {
		ec.viewAllEvents();
	}
	public void viewEventDetails(String eventID) {
		ec.viewEventDetails(eventID);
	}
	public void deleteEvent(String eventID) {
		admin.deleteEvent(eventID);
	}
	public String deleteUser(String userID) {
		if (admin.deleteUser(userID)) {
			return "User with ID: "+userID+" Has been Deleted!";
		}
		return "Something went wrong!";
	}
	public Vector getAllUsers() {
		return admin.getAllUsers();
	}
	public void getAllEvents() {
		admin.getAllEvents();
	}

}
