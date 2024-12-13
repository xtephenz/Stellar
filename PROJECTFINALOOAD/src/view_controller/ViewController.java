package view_controller;

import java.util.Stack;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import view.AppView;
import view.EventDetailsView;
import view.EventView;
import view.HomeView;
import view.InvitationView;
import view.AdminView;
import view.LoginView;
import view.ManageVendorView;
import view.RegisterView;
import view.UserInviteView;

public class ViewController {
	AppView appView;
	Stack<VBox> pages;
	Stage stage;
	public static ViewController instance;
	
	public static ViewController getInstance(Stage stage) {
		if(instance == null && stage != null) {
			instance = new ViewController(stage);
		}
		return instance;
	}
	
	private ViewController(Stage stage) {
		appView = new AppView(stage);
		this.stage = stage;
		pages = new Stack<VBox>();
	}
	
	public void navigateToLogin() {
		LoginView login = new LoginView();
		pages.add(login);
		appView.getContainer().setCenter(login);
	}
	
	public void navigateToAdmin() {
		AdminView homeView = new AdminView();
		pages.add(homeView);
		appView.getContainer().setCenter(homeView);
	}
	public void navigateToRegister() {
		RegisterView RegisterView = new RegisterView();
		pages.add(RegisterView);
		appView.getContainer().setCenter(RegisterView);
	}
	

	public void navigateToEvent() {
		EventView eventView = new EventView();
		pages.add(eventView);
		appView.getContainer().setCenter(eventView);
	}

	public void navigateToEventDetails(Event event) {
		EventDetailsView eventDetailsView = new EventDetailsView(event);
//		eventDetailsView.setSelectedEvent(event);
		pages.add(eventDetailsView);
		appView.getContainer().setCenter(eventDetailsView);
	}
	public void navigateToInvite(Event event) {
		InvitationView inviteView = new InvitationView(event);
		pages.add(inviteView);
		appView.getContainer().setCenter(inviteView);
	}
	public void navigateToUserHome() {
		HomeView userInviteView = new HomeView();
		pages.add(userInviteView);
		appView.getContainer().setCenter(userInviteView);
	}

	public void navigateToVendorView() {
		ManageVendorView mvv = new ManageVendorView();
		pages.add(mvv);
		appView.getContainer().setCenter(mvv);
	}
	public void goBack() {
	    if (!pages.isEmpty()) {
	        // Remove the current page
	        pages.pop();
	        
	        // Check if there is a previous page
	        if (!pages.isEmpty()) {
	            // Display the previous page
	            VBox previousPage = pages.peek();
	            appView.getContainer().setCenter(previousPage);
	        } else {
	            // Optional: Handle the case where no more pages exist
	            System.out.println("No more pages to go back to.");
	        }
	    } else {
	        System.out.println("No pages in the history.");
	    }
	}
}
