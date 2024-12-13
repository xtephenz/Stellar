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
    private TableView<Guest> guestTv;
    private TableView<Vendor> vendorTv;
    private ObservableList<Guest> guests;
    private ObservableList<Vendor> vendors;
    private Event selectedEvent;
    private EventOrganizerController eoc;
    private UserController uc;
    private Button inviteButton;

    public EventDetailsView(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
        eoc = new EventOrganizerController();
        uc = new UserController();
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

        statusLabel = new Label();

        eventIdLbl = new Label();
        eventNameLbl = new Label();
        eventDateLbl = new Label();
        eventLocLbl = new Label();
        eventDescLbl = new Label();
        orgIdLbl = new Label();

        guestTv = new TableView<>();
        vendorTv = new TableView<>();
        
        inviteButton = new Button("Invite Vendor/Guest");
        inviteButton.setOnMouseClicked(event -> {
            ViewController.getInstance(null).navigateToInvite(selectedEvent);
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

        VBox tableBox = new VBox(10, guestTv, vendorTv);
        tableBox.setStyle("-fx-alignment: center; -fx-padding: 10;");
        if (Session.getInstance().getUserSession().getUser_role().equals("Admin") || Session.getInstance().getUserSession().getUser_role().equals("Event Organizer")) {
        	this.getChildren().addAll(
                    titleLabel,
                    eventDetailsLabel,
                    eventDetailsBox,
                    tableBox,
                    buttonBox
                );
        } else {
        	this.getChildren().addAll(
                    titleLabel,
                    eventDetailsLabel,
                    eventDetailsBox,
                    tableBox
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
        eventIdLbl.setText(String.format("Event ID: %s", selectedEvent.getEvent_id()));
        eventNameLbl.setText(String.format("Event Name: %s", selectedEvent.getEvent_name()));
        eventDateLbl.setText(String.format("Event Date: %s", selectedEvent.getEvent_date()));
        eventLocLbl.setText(String.format("Event Location: %s", selectedEvent.getEvent_location()));
        eventDescLbl.setText(String.format("Event Description: %s", selectedEvent.getEvent_description()));
        orgIdLbl.setText(String.format("Organizer: %s - %s", selectedEvent.getOrganizer_id(),
                uc.getUserById(selectedEvent.getOrganizer_id()).getUser_name()));
    }
}

//
//public class EventDetailsView extends VBox{
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
//	Button InviteBtn;
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
//		InviteBtn = new Button("Invite Vendor/Guest");
//		InviteBtn.setOnMouseClicked(e->{
//			ViewController.getInstance(null).navigateToInvite(selectedEvent);
//		});
////		viewEventDetailsBtn = new Button("View Event Details");
////		viewEventDetailsBtn.setOnMouseClicked(e->{
////			deleteSelectedRow();
////		});
//	}
//	private void setGuestTable() {
//		guests = FXCollections.observableArrayList(eoc.getGuestByTransactionID(selectedEvent.getEvent_id()));
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
//		vendors = FXCollections.observableArrayList(eoc.getVendorByTransactionID(selectedEvent.getEvent_id()));
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
//		eventIdLbl.setText(String.format("Event ID: %s",selectedEvent.getEvent_id()));
//		eventNameLbl.setText(String.format("Event Name: %s",selectedEvent.getEvent_name()));
//		eventDateLbl.setText(String.format("Event Date: %s",selectedEvent.getEvent_date()));
//		eventLocLbl.setText(String.format("Event Location: %s", selectedEvent.getEvent_location()));
//		eventDescLbl.setText(String.format("Event Description: %s", selectedEvent.getEvent_description()));
//		orgIdLbl.setText(String.format("Organizer: %s - %s", selectedEvent.getOrganizer_id(),uc.getUserById(selectedEvent.getOrganizer_id()).getUser_name()));
//		this.getChildren().addAll(eventIdLbl,eventNameLbl,eventDateLbl,eventLocLbl,eventDescLbl,orgIdLbl,guestTv,vendorTv, InviteBtn);
//	}
//	public EventDetailsView(Event selectedEvent) {
//		this.selectedEvent = selectedEvent;
//		System.out.println(selectedEvent.getEvent_name());
//		init();
//		setGuestTable();
//		setVendorTable();
//		setLayout();
//	}
//}
