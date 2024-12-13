package view;


import controller.AdminController;
import controller.EventController;
import controller.EventOrganizerController;
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
import model.User;
import view_controller.ViewController;
//
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class EventView extends VBox {

    private Label titleLabel, eventLabel, statusLabel;
    private TextField eventNameField, eventDateField, eventLocationField, eventDescriptionField;
    private TableView<Event> eventTable;
    private ObservableList<Event> events;
    private Button addEventButton, viewEventDetailsButton;
    private EventController eventController;
    private EventOrganizerController eoc;

    public EventView() {
        initializeComponents();
        configureComponents();
        setLayout();
        setEventTable();
    }

    private void initializeComponents() {
        titleLabel = new Label("Event Management");
        eventLabel = new Label("Events List");
        statusLabel = new Label();

        eventNameField = new TextField();
        eventDateField = new TextField();
        eventLocationField = new TextField();
        eventDescriptionField = new TextField();

        eventTable = new TableView<>();

        addEventButton = new Button("Add Event");
        viewEventDetailsButton = new Button("View Event Details");

        eventController = new EventController();
        eoc = new EventOrganizerController();
    }

    private void configureComponents() {
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10;");
        eventLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        eventNameField.setPromptText("Event Name");
        eventDateField.setPromptText("Event Date");
        eventLocationField.setPromptText("Event Location");
        eventDescriptionField.setPromptText("Event Description");

        addEventButton.setOnMouseClicked(event -> addEvent());

        viewEventDetailsButton.setOnMouseClicked(event -> selectEvent());
    }

    private void setLayout() {
        VBox inputBox = new VBox(10, eventNameField, eventDateField, eventLocationField, eventDescriptionField, addEventButton, statusLabel);
        inputBox.setStyle("-fx-alignment: center-left; -fx-spacing: 10; -fx-padding: 10;");

        HBox buttonBox = new HBox(10, viewEventDetailsButton);
        buttonBox.setStyle("-fx-alignment: center; -fx-padding: 10;");

        this.getChildren().addAll(
            titleLabel,
            inputBox,
            eventLabel,
            eventTable,
            buttonBox
        );

        this.setSpacing(10);
        this.setStyle("-fx-padding: 20; -fx-alignment: center;");
    }

    private void setEventTable() {
        if ("Event Organizer".equals(Session.getInstance().getUserSession().getUser_role())) {
        	events = FXCollections.observableArrayList(eoc.viewOrganizedEvents(Session.getInstance().getUserSession().getUser_id()));
        } else if ("Admin".equals(Session.getInstance().getUserSession().getUser_role())) {
        	events = FXCollections.observableArrayList(eventController.viewAllEvents());
        }  else {
        	return;
        }

        TableColumn<Event, String> eventIdColumn = new TableColumn<>("Event ID");
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("event_id"));

        TableColumn<Event, String> eventNameColumn = new TableColumn<>("Event Name");
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("event_name"));

        TableColumn<Event, String> eventDateColumn = new TableColumn<>("Event Date");
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("event_date"));

        eventTable.getColumns().addAll(eventIdColumn, eventNameColumn, eventDateColumn);
        eventTable.setItems(events);
    }

    private void addEvent() {
        String eventName = eventNameField.getText();
        String eventDate = eventDateField.getText();
        String eventLocation = eventLocationField.getText();
        String eventDescription = eventDescriptionField.getText();

        String status = eventController.createEvent(eventName, eventDate, eventLocation, eventDescription, Session.getInstance().getUserSession().getUser_id());

        if ("Event successfully created!".equals(status)) {
            statusLabel.setText(status);
            setEventTable();
        } else {
            statusLabel.setText("");
            Alert alert = new Alert(Alert.AlertType.WARNING, status, ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void selectEvent() {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            ViewController.getInstance(null).navigateToEventDetails(eventController.getEventById(selectedEvent.getEvent_id()));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an event.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
//public class EventView extends VBox{
//	TextField eventNameTf, eventDateTf, eventLocTf, eventDescTf;
//	Button submitBtn;
//	Label userLbl, statusLbl;
//	TableView<Event> eventTv;
//	ObservableList<Event> events;
//	UserController uc = new UserController();
//	EventController ec = new EventController();
//	AdminController ac = new AdminController();
//	Button viewEventDetailsBtn = new Button();
//	
//	private void init() {
//		eventNameTf = new TextField();
//		eventDateTf = new TextField();
//		eventLocTf = new TextField();
//		eventDescTf = new TextField();
//		submitBtn = new Button("Add Event");
//		eventTv = new TableView<Event>();
//		userLbl = new Label("Events");
//		statusLbl = new Label();
//		submitBtn.setOnMouseClicked(e->{
//			addEvent();
//		});
//		viewEventDetailsBtn = new Button("View Event Details");
//		viewEventDetailsBtn.setOnMouseClicked(e->{
//			selectEvent();
//		});
//	}
//	
//	private void setUpTable() {
//		if(Session.getInstance().getUserSession().getUser_role().equals("Admin")) {
//			events = FXCollections.observableArrayList(ec.viewAllEvents());
//			System.out.println("aaaaa");
//		} else {
//			return;
//		}
//		TableColumn<Event, String> eventIdCol = new TableColumn<Event, String>("Event ID");
//		eventIdCol.setCellValueFactory(new PropertyValueFactory<Event, String>("event_id"));
//		
//		TableColumn<Event, String> eventNameCol = new TableColumn<Event, String>("Event Name");
//		eventNameCol.setCellValueFactory(new PropertyValueFactory<Event, String>("event_name"));
//		
//		TableColumn<Event, String> eventDateCol = new TableColumn<Event, String>("Event Date");
//		eventDateCol.setCellValueFactory(new PropertyValueFactory<Event, String>("event_date"));
////		
////		TableColumn<Event, String> eventLocCol = new TableColumn<Event, String>("Event Location");
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
//		eventTv.getColumns().addAll(eventIdCol, eventNameCol, eventDateCol);
//		eventTv.setItems(events);
//	}
//	
//	private void setLayout() {
//		this.getChildren().addAll(eventNameTf, eventDateTf, eventLocTf, eventDescTf, submitBtn, userLbl, eventTv, viewEventDetailsBtn);
//	}
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
//		
//		
//		
//	}
//	private void selectEvent() {
//        Event selectedEvent = eventTv.getSelectionModel().getSelectedItem();
//        
//        if (selectedEvent != null) {
//        	ViewController.getInstance(null).navigateToEventDetails(ec.getEventById(selectedEvent.getEvent_id()));
//            
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an event.", ButtonType.OK);
//            alert.showAndWait();
//        }
//    }
//
//	
//	public EventView() {
//		init();
//		setUpTable();
//		
//		setLayout();
//	}
//	
//}