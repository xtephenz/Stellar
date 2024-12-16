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

	private void setLayout() {
		this.getChildren().addAll(basicLbl,inviteTv,submitBtn);
	}

	private void AcceptInvite() {
	    // Loop through all rows to find selected ones
	    for (Invitation selectedInvite : inviteTv.getItems()) {
	        if (selectedInvite.isSelected()) {
	            // Perform the logic for each selected invitation
	            ic.acceptInvitation(selectedInvite.getEvent_id(), Session.getInstance().getUserSession().getUser_id());
	        }
	    }
	}


	public UserInviteView() {
		init();
		setLayout();
	}
	
}