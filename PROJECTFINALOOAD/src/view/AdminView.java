package view;

import java.util.Vector;

import controller.AdminController;
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

public class AdminView extends VBox {

    private Label titleLabel, userLabel;
    private TableView<User> userTable;
    private ObservableList<User> users;
    private Button deleteButton, viewEventsButton;
    private UserController userController;
    private AdminController adminController;

    public AdminView() {
        initializeComponents();
        configureComponents();
        setLayout();
        setUserTable();
    }

    private void initializeComponents() {
        titleLabel = new Label("Admin Panel");
        userLabel = new Label("Users List");
        userTable = new TableView<>();
        deleteButton = new Button("Delete User");
        viewEventsButton = new Button("View Events");
        userController = new UserController();
        adminController = new AdminController();
    }

    private void configureComponents() {
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10;");
        userLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        deleteButton.setOnMouseClicked(event -> deleteSelectedRow());

        viewEventsButton.setOnMouseClicked(event -> 
            ViewController.getInstance(null).navigateToEvent()
        );
    }

    private void setLayout() {
        HBox buttonBox = new HBox(10, deleteButton, viewEventsButton);
        buttonBox.setStyle("-fx-alignment: center;");

        this.getChildren().addAll(
            titleLabel,
            userLabel,
            userTable,
            buttonBox
        );

        this.setSpacing(10);
        this.setStyle("-fx-padding: 20; -fx-alignment: center;");
    }

    private void setUserTable() {
        if (!"Admin".equals(Session.getInstance().getUserSession().getUser_role())) {
            return;
        }

        users = FXCollections.observableArrayList(adminController.getAllUsers());

        TableColumn<User, String> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email"));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));

        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("user_password"));

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("user_role"));

        userTable.getColumns().addAll(userIdColumn, emailColumn, usernameColumn, passwordColumn, roleColumn);
        userTable.setItems(users);
    }

    private void deleteSelectedRow() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            users.remove(selectedUser);
            String deleteQuery = String.format("DELETE FROM users WHERE user_id = '%s'", selectedUser.getUser_id());
            try {
                Connect.getInstance().execute(deleteQuery);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a user to delete.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
//public class AdminView extends VBox{
//	TextField nameTf, passwordTf;
//	Button submitBtn;
//	Label userLbl;
//	TableView<User> userTv;
//	TableView<Event> eventTv;
//	ObservableList<User> users;
//	UserController uc = new UserController();
//	AdminController ac = new AdminController();
//	Button deleteBtn;
//	Button eventBtn;
//	
//	private void init() {
////		nameTf = new TextField();
////		passwordTf = new TextField();
////		submitBtn = new Button("Add User");
//		userTv = new TableView<User>();
//		userLbl = new Label("Users");
//		deleteBtn = new Button("Delete");
//		deleteBtn.setOnMouseClicked(e->{
//			deleteSelectedRow();
//		});
//		eventBtn = new Button("View Events");
//		eventBtn.setOnMouseClicked(e->{
//			ViewController.getInstance(null).navigateToEvent();
//		});
//	}
//	
//	private void setUserTable() {
//		if(Session.getInstance().getUserSession().getUser_role().equals("Admin")) {
//			users = FXCollections.observableArrayList(ac.getAllUsers());
//			System.out.println("aaaaa");
//		} else {
//			return;
//		}
//		TableColumn<User, String> userIdCol = new TableColumn<User, String>("User ID");
//		userIdCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_id"));
//		
//		TableColumn<User, String> emailCol = new TableColumn<User, String>("User Email");
//		emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_email"));
//		
//		TableColumn<User, String> usernameCol = new TableColumn<User, String>("Username");
//		usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_name"));
//		
//		TableColumn<User, String> passwordCol = new TableColumn<User, String>("Password");
//		passwordCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_password"));
//
//		TableColumn<User, String> roleCol = new TableColumn<User, String>("Role");
//		roleCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_role"));
//		//TableColumn<User, Boolean> selectCol = new TableColumn<>("Select");
//		//selectCol.setCellValueFactory(data -> data.getValue().selectedProperty()); // Binding to BooleanProperty
//		//selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));
//        
//		userTv.getColumns().addAll(userIdCol, emailCol, usernameCol, passwordCol, roleCol);
//		userTv.setItems(users);
//	}
//	
//	private void setLayout() {
//		this.getChildren().addAll(userLbl, userTv, deleteBtn, eventBtn);
//	}
//	private void deleteSelectedRow() {
//        User selectedEvent = userTv.getSelectionModel().getSelectedItem();
//        if (selectedEvent != null) {
//            // Remove from TableView
//            users.remove(selectedEvent);
//
//            // Delete from Database
//            String deleteQuery = String.format("DELETE FROM users WHERE user_id = '%s'", selectedEvent.getUser_id());
//            try {
//                Connect.getInstance().execute(deleteQuery); // Adjust for your database utility class
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a row to delete.", ButtonType.OK);
//            alert.showAndWait();
//        }
//    }
//
//	
//	public AdminView() {
//		init();
//		setUserTable();
//		
//		setLayout();
//	}
//	
//}
