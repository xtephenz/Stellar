package view;


import controller.AdminController;
import controller.EventController;
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
import javafx.beans.property.SimpleStringProperty;
import main.Session;
import model.Event;
import model.Invitation;
import model.User;
import view_controller.ViewController;

public class UserInviteView extends VBox{
	TextField eventNameTf, eventDateTf, eventLocTf, eventDescTf;
	Button submitBtn;
	Label basicLbl, statusLbl;
	TableView<Invitation> inviteTv;
	ObservableList<Invitation> events;
	UserController uc = new UserController();
	EventController ec = new EventController();
	AdminController ac = new AdminController();
	InvitationController ic = new InvitationController();
	Button viewEventDetailsBtn = new Button();
	
	private void init() {
		eventNameTf = new TextField();
		eventDateTf = new TextField();
		eventLocTf = new TextField();
		eventDescTf = new TextField();
		submitBtn = new Button("Accept Invite");
		inviteTv = new TableView<Invitation>();
		basicLbl = new Label("Event Invitations");
		statusLbl = new Label();
		submitBtn.setOnMouseClicked(e->{
			AcceptInvite();
		});
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
	    events = FXCollections.observableArrayList(
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
	    inviteTv.getColumns().addAll(
	        invitationIdCol, eventIdCol, eventNameCol, eventLocCol, invitationStatusCol
	    );

	    // Set the TableView items
	    inviteTv.setItems(events);
	}

	private void setLayout() {
		this.getChildren().addAll(basicLbl,inviteTv,submitBtn);
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
            
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an event.", ButtonType.OK);
            alert.showAndWait();
        }
    }

	
	public UserInviteView() {
		init();
		setUpTable();
		
		setLayout();
	}
	
}