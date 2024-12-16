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
import model.Guest;
import model.User;
import model.Vendor;
import view_controller.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class EventDetailsView extends VBox {

    private Label titleLabel, eventDetailsLabel, statusLabel;
    private Label eventIdLbl, eventNameLbl, eventDateLbl, eventLocLbl, eventDescLbl, orgIdLbl;
    private TextField changeEventNameTf;
    private Button changeEventNameBtn;
    private TableView<Guest> guestTv;
    private TableView<Vendor> vendorTv;
    private ObservableList<Guest> guests;
    private ObservableList<Vendor> vendors;
    private Event selectedEvent;
    private EventOrganizerController eoc;
    private UserController uc;
    private EventController ec;
    private Button inviteButton, backButton;
    private NavbarView navbarView;

    public EventDetailsView(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
        eoc = new EventOrganizerController();
        uc = new UserController();
        navbarView = new NavbarView();
        initializeComponents();
        configureComponents();
        setLayout();
        setGuestTable();
        setVendorTable();
        setEventDetails();
        
    }

    private void initializeComponents() {
        titleLabel = new Label("Event Details");
        eventDetailsLabel = new Label("Event Information");

        ec = new EventController();
        statusLabel = new Label();

        eventIdLbl = new Label();
        eventNameLbl = new Label();
        eventDateLbl = new Label();
        eventLocLbl = new Label();
        eventDescLbl = new Label();
        orgIdLbl = new Label();

        changeEventNameTf = new TextField();
        changeEventNameBtn = new Button ("Update Event Name");
        guestTv = new TableView<>();
        vendorTv = new TableView<>();
        
        changeEventNameTf.setPromptText("Edit event name...");
        changeEventNameBtn.setOnMouseClicked(e->{
        	if(changeEventNameTf.getText().equals("") || changeEventNameTf.getText().isEmpty()) {
        		Alert alert = new Alert(Alert.AlertType.ERROR, "Event Name cannot be empty!", ButtonType.OK);
                alert.showAndWait();
        	} else {
        		ec.updateEvent(selectedEvent.getEvent_id(), changeEventNameTf.getText(), selectedEvent.getEvent_date(), selectedEvent.getEvent_location(), selectedEvent.getEvent_description());
        		setEventDetails();
        	}

        });
        
        inviteButton = new Button("Invite Vendor/Guest");
        inviteButton.setOnMouseClicked(event -> {
            ViewController.getInstance(null).navigateToInvite(selectedEvent);
        });
        backButton = new Button("Back");
        backButton.setOnMouseClicked(event ->{
        	ViewController.getInstance(null).navigateToUserHome();
        });
    }

    private void configureComponents() {
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10;");
        eventDetailsLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5;");
        
        inviteButton.setStyle("-fx-padding: 10;");
    }

    private void setLayout() {
        VBox eventDetailsBox = new VBox(10, eventIdLbl, eventNameLbl, eventDateLbl, eventLocLbl, eventDescLbl, orgIdLbl);
        eventDetailsBox.setStyle("-fx-alignment: center-left; -fx-spacing: 10; -fx-padding: 10;");
        
        HBox buttonBox = new HBox(10, inviteButton);
        buttonBox.setStyle("-fx-alignment: center; -fx-padding: 10;");
        
        HBox buttonBox2 = new HBox(10, backButton);
        buttonBox2.setStyle("-fx-alignment: center; -fx-padding: 10;");

        VBox tableBox = new VBox(10, guestTv, vendorTv);
        tableBox.setStyle("-fx-alignment: center; -fx-padding: 10;");
        if (Session.getInstance().getUserSession().getUser_role().equals("Admin") || Session.getInstance().getUserSession().getUser_role().equals("Event Organizer")) {
        	this.getChildren().addAll(
        			navbarView,
                    titleLabel,
                    eventDetailsLabel,
                    eventDetailsBox,
                    tableBox,
                    buttonBox,
                    

                    changeEventNameTf,
                    changeEventNameBtn,buttonBox2
                );
        } else {
        	this.getChildren().addAll(
        			navbarView,
                    titleLabel,
                    eventDetailsLabel,
                    eventDetailsBox,
                    tableBox,
                    buttonBox2
                );
        }
        

        this.setSpacing(10);
        this.setStyle("-fx-padding: 20; -fx-alignment: center;");
    }

    private void setGuestTable() {
        guests = FXCollections.observableArrayList(eoc.getGuestByTransactionID(selectedEvent.getEvent_id()));

        TableColumn<Guest, String> guestIdCol = new TableColumn<>("Guest ID");
        guestIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<Guest, String> guestEmailCol = new TableColumn<>("Guest Email");
        guestEmailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));

        TableColumn<Guest, String> guestNameCol = new TableColumn<>("Guest Name");
        guestNameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        
        guestTv.getColumns().addAll(guestIdCol, guestEmailCol, guestNameCol);

        if (guests != null && !guests.isEmpty()) {
            guestTv.setItems(guests);
        }
    }

    private void setVendorTable() {
        vendors = FXCollections.observableArrayList(eoc.getVendorByTransactionID(selectedEvent.getEvent_id()));

        TableColumn<Vendor, String> vendorIdCol = new TableColumn<>("Vendor ID");
        vendorIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<Vendor, String> vendorEmailCol = new TableColumn<>("Vendor Email");
        vendorEmailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));

        TableColumn<Vendor, String> vendorNameCol = new TableColumn<>("Vendor Name");
        vendorNameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        TableColumn<Vendor, String> vendorProductCol = new TableColumn<>("Vendor Product");
        vendorProductCol.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        TableColumn<Vendor, String> vendorProductDescCol = new TableColumn<>("Vendor Product Description");
        vendorProductDescCol.setCellValueFactory(new PropertyValueFactory<>("product_description"));

        vendorTv.getColumns().addAll(vendorIdCol, vendorEmailCol, vendorNameCol,vendorProductCol,vendorProductDescCol);

        if (vendors != null && !vendors.isEmpty()) {
            vendorTv.setItems(vendors);
        }
    }

    private void setEventDetails() {
    	this.selectedEvent = ec.getEventById(selectedEvent.getEvent_id());
        eventIdLbl.setText(String.format("Event ID: %s", selectedEvent.getEvent_id()));
        eventNameLbl.setText(String.format("Event Name: %s", selectedEvent.getEvent_name()));
        eventDateLbl.setText(String.format("Event Date: %s", selectedEvent.getEvent_date()));
        eventLocLbl.setText(String.format("Event Location: %s", selectedEvent.getEvent_location()));
        eventDescLbl.setText(String.format("Event Description: %s", selectedEvent.getEvent_description()));
        orgIdLbl.setText(String.format("Organizer: %s - %s", selectedEvent.getOrganizer_id(),
                uc.getUserById(selectedEvent.getOrganizer_id()).getUser_name()));
    }
}
