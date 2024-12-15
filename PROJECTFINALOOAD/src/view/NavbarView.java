package view;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import main.Session;
import view_controller.NavbarController;
import view_controller.ViewController;

public class NavbarView extends MenuBar{
	Stage stage;
	private Menu menu;
	private MenuItem homePage, changeProfile, manageProduct, manageEvents, viewEvent,viewEventDetails, logout;
    private NavbarController navbarController;

    public NavbarView() {
        init();
        setComponent();
        setAction();
    }
    
	private void init() {
		menu = new Menu("Menu");
	    homePage = new MenuItem("Home Page"); 
//		link = new Hyperlink("Change profile");
		changeProfile= new MenuItem("Change profile");
		manageProduct = new MenuItem("Manage Product");
		manageEvents = new MenuItem("Manage Event");
		viewEvent = new MenuItem("View Event");
		viewEventDetails = new MenuItem("View Event Details");
		logout = new MenuItem("Logout");
		navbarController = NavbarController.getInstance();
	}
	
	private void setComponent() {
	    getMenus().add(menu);
	    String userRole = Session.getInstance().getUserSession().getUser_role();

	    switch (userRole) {
	        case "Admin":
	            menu.getItems().addAll(homePage, changeProfile, viewEvent, logout);
	            break;
	        case "Vendor":
	            menu.getItems().addAll(homePage, changeProfile, manageProduct, logout);
	            break;
	        case "Guest":
	            menu.getItems().addAll(homePage, changeProfile, logout);
	            break;
	        case "Event Organizer":
	            menu.getItems().addAll(homePage, changeProfile, logout);
	            break;
	        default:
	            menu.getItems().add(logout);
	    }
	}

	
	private void setAction() {
		if (navbarController == null) {
	        throw new IllegalStateException("NavbarController is not initialized.");
	    }
		homePage.setOnAction(e -> navbarController.navigateTo("HomePage"));
		changeProfile.setOnAction(e -> navbarController.navigateTo("ChangeProfile"));
        manageProduct.setOnAction(e -> navbarController.navigateTo("ManageProduct"));
        manageEvents.setOnAction(e -> navbarController.navigateTo("ManageEvents"));
        viewEvent.setOnAction(e -> navbarController.navigateTo("EventView"));
        logout.setOnAction(e -> navbarController.navigateTo("Logout"));

	}
}
