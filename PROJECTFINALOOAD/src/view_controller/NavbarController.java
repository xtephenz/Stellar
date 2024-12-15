package view_controller;

import main.Session;
import model.Event;
import view.EventDetailsView;
import view.NavbarView;

public class NavbarController {
	
	private static NavbarController instance;
    ViewController vc;
	private Event Event;

    private NavbarController() {
        this.vc = ViewController.getInstance(null);
    }
    
    public static NavbarController getInstance() {
        if (instance == null) {
            instance = new NavbarController();
        }
        return instance;
    }

    public void navigateTo(String targetLocation) {
        String userRole = Session.getInstance().getUserSession().getUser_role();
        switch (targetLocation) {    
            case "HomePage":
                if (userRole.equals("Admin")) {
                    vc.navigateToAdmin();   
                }else if(userRole.equals("Event Organizer")){
                	vc.navigateToEvent();
                } else {
                    vc.navigateToUserHome();
                }
                break;
            case "ChangeProfile":
                vc.navigateToChangeProfile();
                break;
            case "ManageProduct":
            	
                vc.navigateToVendor();
                break;
            case "Logout":
                vc.navigateToLogin();
                break;
            case "EventView":
                vc.navigateToEvent();
                break;
            default:
                System.out.println("Error: Unknown location -> " + targetLocation);
        }
    }
}
