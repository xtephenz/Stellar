	package view;

import controller.AdminController;
import javafx.scene.control.Hyperlink;
import controller.EventController;
import controller.EventOrganizerController;
import controller.GuestController;
import controller.InvitationController;
import controller.UserController;
import controller.VendorController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import main.Session;
import model.Event;
import model.Guest;
import model.Invitation;
import model.User;
import model.Vendor;
import view_controller.ViewController;


public class HomeView extends VBox{
	public static String CURRENT_LOCATION = "HomePage";
	
	TextField eventNameTf, eventDateTf, eventLocTf, eventDescTf;
	Label productLbl, productDescLbl;
	Button submitBtn;
	Label basicLbl, statusLbl;
	TableView<Invitation> inviteTv;
	TableView<Event> acceptInviteTv;
	ObservableList<Invitation> invites;
	ObservableList<Event> acceptInvites;
	NavbarView nv = new NavbarView();
	UserController uc = new UserController();
	GuestController gc = new GuestController();
	VendorController vc = new VendorController();
	EventController ec = new EventController();
	AdminController ac = new AdminController();
	InvitationController ic = new InvitationController();
	Button viewEventDetailsBtn = new Button("View Event Details");
	Button manageVendorBtn = new Button("Manage Product");
	Event selectedEvent;
	Hyperlink link;
	Scene scene;
	
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
		invites = FXCollections.observableArrayList(
		        ic.getInvitations(Session.getInstance().getUserSession().getUser_email())
		);
		
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
		manageVendorBtn.setOnMouseClicked(e -> {
	        ViewController.getInstance(null).navigateToVendor();
	    });
	    submitBtn.setOnMouseClicked(e -> {
	        AcceptInvite();
	    });
	    viewEventDetailsBtn.setOnMouseClicked(e -> {
	        selectEvent();
	    });

	    setUpTable();
	    setAcceptTable();
	}
	private void setUpProductLbl() {
		productLbl.setText("Your Product: "+Session.getInstance().getUserSession().getProduct_name());
		productDescLbl.setText("Product Description: "+Session.getInstance().getUserSession().getProduct_description());
	}

	private void setUpTable() {
	    // Ambil data undangan untuk pengguna saat ini
	    invites = FXCollections.observableArrayList(
	        ic.getInvitations(Session.getInstance().getUserSession().getUser_email())
	    );

	    // Inisialisasi kolom checkbox
	    TableColumn<Invitation, Boolean> selectColumn = new TableColumn<>("Select");
	    selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
	    selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
	    selectColumn.setOnEditCommit(event -> {
	        Invitation invitation = event.getRowValue();
	        boolean selected = event.getNewValue();
	        invitation.setSelected(selected);
	    });

	    // Kolom lainnya (Invitation ID, Event ID, dll)
	    TableColumn<Invitation, String> invitationIdCol = new TableColumn<>("Invitation ID");
	    invitationIdCol.setCellValueFactory(new PropertyValueFactory<>("invitation_id"));

	    TableColumn<Invitation, String> eventIdCol = new TableColumn<>("Event ID");
	    eventIdCol.setCellValueFactory(new PropertyValueFactory<>("event_id"));

	    // Kolom Event Name (gunakan data Event terkait)
	    TableColumn<Invitation, String> eventNameCol = new TableColumn<>("Event Name");
	    eventNameCol.setCellValueFactory(cellData -> {
	        String eventId = cellData.getValue().getEvent_id();
	        Event event = ec.getEventById(eventId);
	        return new SimpleStringProperty(event != null ? event.getEvent_name() : "Unknown Event");
	    });

	    TableColumn<Invitation, String> eventLocCol = new TableColumn<>("Event Location");
	    eventLocCol.setCellValueFactory(cellData -> {
	        String eventId = cellData.getValue().getEvent_id();
	        Event event = ec.getEventById(eventId);
	        return new SimpleStringProperty(event != null ? event.getEvent_location() : "Unknown Location");
	    });

	    // Kolom Invitation Status
	    TableColumn<Invitation, String> invitationStatusCol = new TableColumn<>("Invitation Status");
	    invitationStatusCol.setCellValueFactory(new PropertyValueFactory<>("invitation_status"));

	    // Tambahkan semua kolom ke TableView
	    inviteTv.getColumns().clear();
	    inviteTv.getColumns().addAll(selectColumn, invitationIdCol, eventIdCol, eventNameCol, eventLocCol, invitationStatusCol);

	    // Set data untuk TableView
	    inviteTv.setItems(invites);
	}


	private void setAcceptTable() {
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
		this.getChildren().add(0, nv);
		this.setSpacing(0); 
		//Pisahkan, buat controllernya sendiri!
		if(Session.getInstance().getUserSession().getUser_role().equals("Guest")) {
			this.getChildren().addAll(basicLbl,inviteTv,submitBtn,acceptInviteTv, viewEventDetailsBtn);
		}else if(Session.getInstance().getUserSession().getUser_role().equals("Vendor")) {
			this.getChildren().addAll(productLbl, productDescLbl, manageVendorBtn, basicLbl,inviteTv,submitBtn,acceptInviteTv, viewEventDetailsBtn);
		}
		
	}

	private void AcceptInvite() {
	    // Cari semua undangan yang checkbox-nya tercentang
	    ObservableList<Invitation> selectedInvites = FXCollections.observableArrayList();
	    for (Invitation invite : invites) {
	        if (invite.isSelected()) { // Anda perlu menambahkan properti isSelected di model Invitation
	            selectedInvites.add(invite);
	        }
	    }

	    if (!selectedInvites.isEmpty()) {
	        // Proses semua undangan yang dipilih
	        for (Invitation invite : selectedInvites) {
	            ic.acceptInvitation(invite.getEvent_id(), Session.getInstance().getUserSession().getUser_id());
	        }

	        // Refresh tampilan tabel
	        refresh();
	        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selected invitations accepted successfully!", ButtonType.OK);
	        alert.showAndWait();
	    } else {
	        // Jika tidak ada yang dipilih, tampilkan pesan peringatan
	        Alert alert = new Alert(Alert.AlertType.WARNING, "Please select at least one invitation to accept.", ButtonType.OK);
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
	    if (Session.getInstance().getUserSession().getUser_role().equals("Vendor")) {
	        setUpProductLbl();
	    }
	    inviteTv.setEditable(true);
	    setLayout();
	}

	
}
