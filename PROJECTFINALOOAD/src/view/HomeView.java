package view;

import controller.AdminController;
import controller.EventController;
import controller.EventOrganizerController;
import controller.GuestController;
import controller.InvitationController;
import controller.UserController;
import controller.VendorController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.Session;
import model.Event;
import model.Guest;
import model.Invitation;
import model.Vendor;
import view_controller.ViewController;


public class HomeView extends VBox{
	TextField eventNameTf, eventDateTf, eventLocTf, eventDescTf;
	Label productLbl, productDescLbl;
	Button submitBtn;
	Label basicLbl, statusLbl;
	TableView<Invitation> inviteTv;
	TableView<Event> acceptInviteTv;
	ObservableList<Invitation> invites;
	ObservableList<Event> acceptInvites;
	UserController uc = new UserController();
	GuestController gc = new GuestController();
	VendorController vc = new VendorController();
	EventController ec = new EventController();
	AdminController ac = new AdminController();
	InvitationController ic = new InvitationController();
	Button viewEventDetailsBtn = new Button("View Event Details");
	Button manageVendorBtn = new Button("Manage Product");
	Event selectedEvent;

    private void selectEvent() {
        Event selectedEvent = acceptInviteTv.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            ViewController.getInstance(null).navigateToEventDetails(ec.getEventById(selectedEvent.getEvent_id()));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an event.", ButtonType.OK);
            alert.showAndWait();
        }
    }
	private void init() {
		eventNameTf = new TextField();
		eventDateTf = new TextField();
		eventLocTf = new TextField();
		eventDescTf = new TextField();
		submitBtn = new Button("Accept Invite");
		inviteTv = new TableView<Invitation>();
		basicLbl = new Label("Event Invitations");
		statusLbl = new Label();
		acceptInviteTv = new TableView<Event>();
		productLbl = new Label();
		productDescLbl = new Label();
		manageVendorBtn.setOnMouseClicked(e->{
			ViewController.getInstance(null).navigateToVendorView();
		});
		submitBtn.setOnMouseClicked(e->{
			AcceptInvite();
		});
		viewEventDetailsBtn.setOnMouseClicked(e->{
			selectEvent();
		});
	}
	private void setUpProductLbl() {
		productLbl.setText("Your Product: "+Session.getInstance().getUserSession().getProduct_name());
		productDescLbl.setText("Product Description: "+Session.getInstance().getUserSession().getProduct_description());
	}
//	
//	private void setUpTable() {
//		events = FXCollections.observableArrayList(ic.getInvitations(Session.getInstance().getUserSession().getUser_email()));
//		System.out.println("aaaaa");
//		TableColumn<Invitation, String> invitationIdCol = new TableColumn<Invitation, String>("Invitation ID");
//		invitationIdCol.setCellValueFactory(new PropertyValueFactory<Invitation, String>("invitation_id"));
//		
//		TableColumn<Invitation, String> eventIdCol = new TableColumn<Invitation, String>("Event ID");
//		eventIdCol.setCellValueFactory(new PropertyValueFactory<Invitation, String>("event_id"));
//		TableColumn<Event, String> eventNameCol = new TableColumn<>("Event Name");
//		eventNameCol.setCellValueFactory(cellData -> {
//		    String eventId = cellData.getValue().getEvent_id(); // Get the event ID
//		    String eventName = ec.getEventById(eventId).getEvent_name(); // Use the method to get the event name
//		    return new SimpleStringProperty(eventName); // Wrap the value in a StringProperty
//		});
//		TableColumn<Event, String> eventLocCol = new TableColumn<>("Event Location");
//		eventNameCol.setCellValueFactory(cellData -> {
//			String eventId = cellData.getValue().getEvent_id(); // Get the event ID
//			String eventLocation = ec.getEventById(eventId).getEvent_location(); // Use the method to get the event name
//			return new SimpleStringProperty(eventLocation); // Wrap the value in a StringProperty
//		});
//		TableColumn<Invitation, String> invitationStatusCol = new TableColumn<Invitation, String>("Invitation Status");
//		invitationStatusCol.setCellValueFactory(new PropertyValueFactory<Invitation, String>("invitation_status"));
////		
////		TableColumn<Event, String> eventLocCol = new TableColumn<Event, String>("Event Name");
////		eventLocCol.setCellValueFactory(new PropertyValueFactory<Event, String>("event_location"));
////
////		TableColumn<Event, String> eventDescCol = new TableColumn<Event, String>("Event Description");
////		eventDescCol.setCellValueFactory(new PropertyValueFactory<Event, String>("event_description"));
////
////		TableColumn<Event, String> organizerIdCol = new TableColumn<Event, String>("Organizer ID");
////		organizerIdCol.setCellValueFactory(new PropertyValueFactory<Event, String>("organizer_id"));
//		//TableColumn<User, Boolean> selectCol = new TableColumn<>("Select");
//		//selectCol.setCellValueFactory(data -> data.getValue().selectedProperty()); // Binding to BooleanProperty
//		//selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));
//        
////		eventTv.getColumns().addAll(eventIdCol, eventNameCol, eventDateCol, eventLocCol, eventDescCol, organizerIdCol);
//		inviteTv.getColumns().addAll(invitationIdCol, eventIdCol, eventNameCol,eventLocCol,invitationStatusCol);
//		inviteTv.setItems(events);
//	}
	private void setUpTable() {
	    // Fetch invitations for the current user
	    invites = FXCollections.observableArrayList(
	        ic.getInvitations(Session.getInstance().getUserSession().getUser_email())
	    );

	    // Invitation ID column
	    TableColumn<Invitation, String> invitationIdCol = new TableColumn<>("Invitation ID");
	    invitationIdCol.setCellValueFactory(new PropertyValueFactory<>("invitation_id"));

	    // Event ID column
	    TableColumn<Invitation, String> eventIdCol = new TableColumn<>("Event ID");
	    eventIdCol.setCellValueFactory(new PropertyValueFactory<>("event_id"));

	    // Event Name column (custom logic to fetch event name)
	    TableColumn<Invitation, String> eventNameCol = new TableColumn<>("Event Name");
	    eventNameCol.setCellValueFactory(cellData -> {
	        String eventId = cellData.getValue().getEvent_id(); // Get the event ID
	        Event event = ec.getEventById(eventId); // Fetch the event object
	        String eventName = (event != null) ? event.getEvent_name() : "Unknown Event"; // Handle null event
	        return new SimpleStringProperty(eventName); // Wrap the value in a StringProperty
	    });

	    // Event Location column (custom logic to fetch event location)
	    TableColumn<Invitation, String> eventLocCol = new TableColumn<>("Event Location");
	    eventLocCol.setCellValueFactory(cellData -> {
	        String eventId = cellData.getValue().getEvent_id(); // Get the event ID
	        Event event = ec.getEventById(eventId); // Fetch the event object
	        String eventLocation = (event != null) ? event.getEvent_location() : "Unknown Location"; // Handle null event
	        return new SimpleStringProperty(eventLocation); // Wrap the value in a StringProperty
	    });

	    // Invitation Status column
	    TableColumn<Invitation, String> invitationStatusCol = new TableColumn<>("Invitation Status");
	    invitationStatusCol.setCellValueFactory(new PropertyValueFactory<>("invitation_status"));

	    // Add all columns to the TableView
	    inviteTv.getColumns().clear();
	    inviteTv.getColumns().addAll(
	        invitationIdCol, eventIdCol, eventNameCol, eventLocCol, invitationStatusCol
	    );

	    // Set the TableView items
	    inviteTv.setItems(invites);
	}
	private void setAcceptTable() {
	    // Fetch invitations for the current user
		
	    acceptInvites = FXCollections.observableArrayList(
	    		gc.viewAcceptedEvents(Session.getInstance().getUserSession().getUser_email())
	    );

        TableColumn<Event, String> eventIdColumn = new TableColumn<>("Event ID");
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("event_id"));

        TableColumn<Event, String> eventNameColumn = new TableColumn<>("Event Name");
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("event_name"));

        TableColumn<Event, String> eventDateColumn = new TableColumn<>("Event Date");
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("event_date"));
        acceptInviteTv.getColumns().clear();
        acceptInviteTv.getColumns().addAll(eventIdColumn, eventNameColumn, eventDateColumn);
        acceptInviteTv.setItems(acceptInvites);

	}
	

	private void setLayout() {
		this.getChildren().addAll(productLbl, productDescLbl, manageVendorBtn, basicLbl,inviteTv,submitBtn,acceptInviteTv, viewEventDetailsBtn);
	}
//	private void addEvent() {
//		String eventName;
//		String eventDate;
//		String eventLoc;
//		String eventDesc;
//		try {
//			 eventName = eventNameTf.getText();
//			 eventDate = eventDateTf.getText();
//			 eventLoc = eventLocTf.getText();
//			 eventDesc = eventDescTf.getText();
//		} catch (Exception e) {
//			 eventName = "";
//			 eventDate = "";
//			 eventLoc = "";
//			 eventDesc = "";
//		}
//		String status = ec.createEvent(eventName, eventDate, eventLoc, eventDesc, Session.getInstance().getUserSession().getUser_id());
//		if (status.equals("Event successfully created!")) {
//			statusLbl.setText(status);
//			setUpTable();
//		} else {
//			statusLbl.setText("");
//			Alert alert = new Alert(Alert.AlertType.WARNING, status, ButtonType.OK);
//            alert.showAndWait();
//		}
		
		
		
//	}
	private void AcceptInvite() {
        Invitation selectedInvite = inviteTv.getSelectionModel().getSelectedItem();
        
        if (selectedInvite != null) {
        	ic.acceptInvitation(selectedInvite.getEvent_id(), Session.getInstance().getUserSession().getUser_id());
//        	setUpTable();
        	refresh();
            
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an event.", ButtonType.OK);
            alert.showAndWait();
        }
    }
	public void refresh() {
		setUpTable();
		setAcceptTable();
	}
	
	public HomeView() {
		init();
		setUpTable();
		setAcceptTable();
		if(Session.getInstance().getUserSession().getUser_role().equals("Vendor")) {
			setUpProductLbl();
		}
		setLayout();
	}
	
}
