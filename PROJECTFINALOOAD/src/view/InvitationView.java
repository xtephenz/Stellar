package view;


import controller.AdminController;
import controller.EventController;
import controller.EventOrganizerController;
import controller.InvitationController;
import controller.UserController;
import database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.Session;
import model.Event;
import model.Guest;
import model.User;
import model.Vendor;
import view_controller.ViewController;
//
public class InvitationView extends VBox {
    Label eventIdLbl, eventNameLbl, eventDateLbl, eventLocLbl, eventDescLbl, orgIdLbl;
    TableView<Guest> guestTv;
    TableView<Vendor> vendorTv;
    ObservableList<Event> events;
    ObservableList<Guest> guests;
    ObservableList<Vendor> vendors;
    UserController uc = new UserController();
    EventController ec = new EventController();
    AdminController ac = new AdminController();
    EventOrganizerController eoc = new EventOrganizerController();
    Button addGuest;
    Button addVendor;
    InvitationController ic = new InvitationController();
    Event selectedEvent = new Event();

    public InvitationView(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
        init();
        setLayout();
        setGuestTable();
        setVendorTable();
    }

    private void init() {
        guestTv = new TableView<>();
        vendorTv = new TableView<>();
        addGuest = new Button("Add Guest");
        addVendor = new Button("Add Vendor");

        addGuest.setOnMouseClicked(e -> {
            selectGuest();
            refreshPage(); // Refresh the page after adding a guest
        });

        addVendor.setOnMouseClicked(e -> {
            selectVendor();
            refreshPage(); // Refresh the page after adding a vendor
        });
    }

    private void setGuestTable() {
        guests = FXCollections.observableArrayList(eoc.getGuests(this.selectedEvent.getEvent_id()));

        TableColumn<Guest, String> guestIdCol = new TableColumn<>("Guest ID");
        guestIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<Guest, String> guestEmailCol = new TableColumn<>("Guest Email");
        guestEmailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));

        TableColumn<Guest, String> guestNameCol = new TableColumn<>("Guest Name");
        guestNameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));

        guestTv.getColumns().clear();  // Clear existing columns
        guestTv.getColumns().addAll(guestIdCol, guestEmailCol, guestNameCol);
        guestTv.setItems(guests);
        guestTv.setStyle("-fx-padding: 10; -fx-alignment: center;");
    }

    private void setVendorTable() {
        vendors = FXCollections.observableArrayList(eoc.getVendors(this.selectedEvent.getEvent_id()));

        TableColumn<Vendor, String> vendorIdCol = new TableColumn<>("Vendor ID");
        vendorIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<Vendor, String> vendorEmailCol = new TableColumn<>("Vendor Email");
        vendorEmailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));

        TableColumn<Vendor, String> vendorNameCol = new TableColumn<>("Vendor Name");
        vendorNameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));

        vendorTv.getColumns().clear();  // Clear existing columns
        vendorTv.getColumns().addAll(vendorIdCol, vendorEmailCol, vendorNameCol);
        vendorTv.setItems(vendors);
        vendorTv.setStyle("-fx-padding: 10; -fx-alignment: center;");
    }

    private void setLayout() {
        // Styling the main container
        this.setSpacing(10);
        this.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Adding the tables and buttons to the layout with appropriate margins and paddings
        VBox guestVBox = new VBox(10, guestTv, addGuest);
        guestVBox.setStyle("-fx-alignment: center; -fx-padding: 10;");

        VBox vendorVBox = new VBox(10, vendorTv, addVendor);
        vendorVBox.setStyle("-fx-alignment: center; -fx-padding: 10;");

        this.getChildren().addAll(guestVBox, vendorVBox);
    }

    private void selectGuest() {
        Guest selectedGuest = guestTv.getSelectionModel().getSelectedItem();

        if (selectedGuest != null) {
            ic.sendInvitation(selectedEvent.getEvent_id(), selectedGuest.getUser_email());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select guest.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void selectVendor() {
        Vendor selectedVendor = vendorTv.getSelectionModel().getSelectedItem();

        if (selectedVendor != null) {
            ic.sendInvitation(selectedEvent.getEvent_id(), selectedVendor.getUser_email());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select vendor.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    // Method to refresh the page content after adding a guest or vendor
    private void refreshPage() {
        setGuestTable();  // Re-populate guest table
        setVendorTable(); // Re-populate vendor table
    }
}

//public class InvitationView extends VBox{
//	//TextField event, passwordTf;
//	//Button submitBtn;
//	Label eventIdLbl, eventNameLbl, eventDateLbl, eventLocLbl, eventDescLbl, orgIdLbl;
//	TableView<Guest> guestTv;
//	TableView<Vendor> vendorTv;
//	ObservableList<Event> events;
//	ObservableList<Guest> guests;
//	ObservableList<Vendor> vendors;
//	UserController uc = new UserController();
//	EventController ec = new EventController();
//	AdminController ac = new AdminController();
//	EventOrganizerController eoc = new EventOrganizerController();
//	Button viewEventDetailsBtn = new Button();
//	Event selectedEvent = new Event();
//	InvitationController ic = new InvitationController();
//	Button addGuest;
//	Button addVendor;
//	
//	public Event getSelectedEvent() {
//		return selectedEvent;
//	}
//
//	public void setSelectedEvent(Event selectedEvent) {
//		this.selectedEvent = selectedEvent;
//	}
//
//	private void init() {
////		nameTf = new TextField();
////		passwordTf = new TextField();
////		submitBtn = new Button("Add User");
////		eventTv = new TableView<Event>();
//		//userLbl = new Label("Users");
//		eventIdLbl = new Label();
//		eventNameLbl = new Label();
//		eventDateLbl = new Label();
//		eventLocLbl = new Label();
//		eventDescLbl = new Label();
//		orgIdLbl = new Label();
//		guestTv = new TableView<Guest>();
//		vendorTv = new TableView<Vendor>();
//		addGuest = new Button("Add Guest");
//		addGuest.setOnMouseClicked(e->{
//			selectGuest();
//		});
//		addVendor = new Button("Add Vendor");
//		addVendor.setOnMouseClicked(e->{
//			selectVendor();
//		});
////		viewEventDetailsBtn = new Button("View Event Details");
////		viewEventDetailsBtn.setOnMouseClicked(e->{
////			deleteSelectedRow();
////		});
//	}
//	private void setGuestTable() {
//		guests = FXCollections.observableArrayList(eoc.getGuests());
//		
//		System.out.println("aaaaa");
//		TableColumn<Guest, String> guestIdCol = new TableColumn<Guest, String>("Guest ID");
//		guestIdCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("user_id"));
//		
//		TableColumn<Guest, String> guestEmailCol = new TableColumn<Guest, String>("Guest Email");
//		guestEmailCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("user_email"));
//		
//		TableColumn<Guest, String> guestNameCol = new TableColumn<Guest, String>("Guest Name");
//		guestNameCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("user_name"));
//		
////		TableColumn<User, String> passwordCol = new TableColumn<User, String>("Password");
////		passwordCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_password"));
//
////		TableColumn<User, String> roleCol = new TableColumn<User, String>("Role");
////		roleCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_role"));
//		//TableColumn<User, Boolean> selectCol = new TableColumn<>("Select");
//		//selectCol.setCellValueFactory(data -> data.getValue().selectedProperty()); // Binding to BooleanProperty
//		//selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));
//        
//		guestTv.getColumns().addAll(guestIdCol, guestEmailCol, guestNameCol);
//		if (guests==null || guests.isEmpty()) {
//			return;
//		}
//		guestTv.setItems(guests);
//	}
//	private void setVendorTable() {
//		
//		vendors = FXCollections.observableArrayList(eoc.getVendors());
//		
//		System.out.println("aaaaa");
//		TableColumn<Vendor, String> vendorIdCol = new TableColumn<Vendor, String>("Vendor ID");
//		vendorIdCol.setCellValueFactory(new PropertyValueFactory<Vendor, String>("user_id"));
//		
//		TableColumn<Vendor, String> vendorEmailCol = new TableColumn<Vendor, String>("Vendor Email");
//		vendorEmailCol.setCellValueFactory(new PropertyValueFactory<Vendor, String>("user_email"));
//		
//		TableColumn<Vendor, String> vendorNameCol = new TableColumn<Vendor, String>("Vendor Name");
//		vendorNameCol.setCellValueFactory(new PropertyValueFactory<Vendor, String>("user_name"));
//		
////		TableColumn<User, String> passwordCol = new TableColumn<User, String>("Password");
////		passwordCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_password"));
//
////		TableColumn<User, String> roleCol = new TableColumn<User, String>("Role");
////		roleCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_role"));
//		//TableColumn<User, Boolean> selectCol = new TableColumn<>("Select");
//		//selectCol.setCellValueFactory(data -> data.getValue().selectedProperty()); // Binding to BooleanProperty
//		//selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));
//        
//		vendorTv.getColumns().addAll(vendorIdCol, vendorEmailCol, vendorNameCol);
//		if(vendors==null || vendors.isEmpty()) {
//			return;
//		}
//		vendorTv.setItems(vendors);
//	}
//	
//	private void setLayout() {
////		eventDetailLbl.setText(String.format("Event ID: %s\nEvent Name: %s\nEvent Date: %s\nEvent Location: %s\nEvent Description%s\nEvent Organizer ID: %s\n", selectedEvent.getEvent_id(), selectedEvent.getEvent_name(), selectedEvent.getEvent_date(), selectedEvent.getEvent_location(), selectedEvent.getEvent_description(), selectedEvent.getOrganizer_id()));
////		eventIdLbl.setText(String.format("Event ID: %s",selectedEvent.getEvent_id()));
////		eventNameLbl.setText(String.format("Event Name: %s",selectedEvent.getEvent_name()));
////		eventDateLbl.setText(String.format("Event Date: %s",selectedEvent.getEvent_date()));
////		eventLocLbl.setText(String.format("Event Location: %s", selectedEvent.getEvent_location()));
////		eventDescLbl.setText(String.format("Event Description: %s", selectedEvent.getEvent_description()));
////		orgIdLbl.setText(String.format("Organizer: %s - %s", selectedEvent.getOrganizer_id(),uc.getUserById(selectedEvent.getOrganizer_id()).getUser_name()));
//		this.getChildren().addAll(guestTv ,addGuest,vendorTv,addVendor);
//	}
//	public InvitationView(Event selectedEvent) {
//		this.selectedEvent = selectedEvent;
//		System.out.println(selectedEvent.getEvent_name());
//		init();
//		setGuestTable();
//		setVendorTable();
//		setLayout();
//	}
//	private void selectGuest() {
//        Guest selectedGuest = guestTv.getSelectionModel().getSelectedItem();
//        
//        if (selectedGuest != null) {
//        	ic.sendInvitation(selectedEvent.getEvent_id(), selectedGuest.getUser_email());
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select guest.", ButtonType.OK);
//            alert.showAndWait();
//        }
//    }
//	private void selectVendor() {
//        Vendor selectedVendor = vendorTv.getSelectionModel().getSelectedItem();
//        
//        if (selectedVendor != null) {
//        	ic.sendInvitation(selectedEvent.getEvent_id(), selectedVendor.getUser_email());
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select guest.", ButtonType.OK);
//            alert.showAndWait();
//        }
//    }
//
//}
