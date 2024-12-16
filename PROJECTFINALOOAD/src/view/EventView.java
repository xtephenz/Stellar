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
    private Button addEventButton, viewEventDetailsButton,deleteBtn;
    private EventController eventController;
    private EventOrganizerController eoc;
    private AdminController ac;
    private NavbarView navbarView;

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
        deleteBtn = new Button("Delete Event");

        eventController = new EventController();
        eoc = new EventOrganizerController();
        ac = new AdminController();
        
        navbarView = new NavbarView();
    }

    private void configureComponents() {
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10;");
        eventLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        eventNameField.setPromptText("Event Name");
        eventDateField.setPromptText("Event Date");
        eventLocationField.setPromptText("Event Location");
        eventDescriptionField.setPromptText("Event Description");

        addEventButton.setOnMouseClicked(event -> addEvent());
        deleteBtn.setOnMouseClicked(e->deleteEvent());
        viewEventDetailsButton.setOnMouseClicked(event -> selectEvent());
    }

    private void setLayout() {
        VBox inputBox = new VBox(10, eventNameField, eventDateField, eventLocationField, eventDescriptionField, addEventButton, statusLabel);
        inputBox.setStyle("-fx-alignment: center-left; -fx-spacing: 10; -fx-padding: 10;");

        HBox buttonBox = new HBox(10, viewEventDetailsButton);
        buttonBox.setStyle("-fx-alignment: center; -fx-padding: 10;");
        if (Session.getInstance().getUserSession().getUser_role().equals("Admin")) {
        	buttonBox = new HBox(10, viewEventDetailsButton, deleteBtn);
        }
        this.getChildren().addAll(
        	navbarView,
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
        eventTable.getColumns().clear();
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
    private void deleteEvent() {
    	Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
    	if (selectedEvent != null) {
    		ac.deleteEvent(selectedEvent.getEvent_id());
    		setEventTable();
    	} else {
    		Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an event.", ButtonType.OK);
    		alert.showAndWait();
    	}
    }
}