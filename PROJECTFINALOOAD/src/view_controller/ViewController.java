package view_controller;

import java.util.Stack;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import view.AppView;
import view.ChangeProfileView;
import view.EventDetailsView;
import view.EventView;
import view.HomeView;
import view.InvitationView;
import view.AdminView;
import view.LoginView;
import view.ManageVendorView;
import view.RegisterView;
import view.UserInviteView;
import javafx.stage.Stage;

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
        navigateTo(new LoginView());
    }
    
    public void navigateToAdmin() {
        navigateTo(new AdminView());
    }

    public void navigateToRegister() {
        navigateTo(new RegisterView());
    }  
    public void navigateToEvent() {
        navigateTo(new EventView());
    }

	public void navigateToChangeProfile() {
		navigateTo(new ChangeProfileView());
	}
	public void navigateToEventDetails(Event event) {
        navigateTo(new EventDetailsView(event));
    }
	public void navigateToInvite(Event event) {
        navigateTo(new InvitationView(event));
    }
	 public void navigateToUserHome() {
	        navigateTo(new HomeView());
	    }

	public void navigateToVendor() {
		try {
            System.out.println("Navigating to ManageVendorView...");
            navigateTo(new ManageVendorView());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public void goBack() {
		if (pages.size() > 1) {
            pages.pop(); // Remove current page
            VBox previousPage = pages.peek();
            appView.getContainer().setCenter(previousPage);
        } else {
            navigateToLogin();  // Navigate to login if no pages are left in stack
        }
	}
	
	private void printStack() {
	    System.out.println("Current Stack:");
	    for (VBox page : pages) {
	        System.out.println("- " + page.getClass().getSimpleName());
	    }
	    System.out.println("End of Stack");
	}
	private void navigateTo(VBox view) {
        pages.add(view);
        appView.getContainer().setCenter(view);
        printStack();
    }

}
