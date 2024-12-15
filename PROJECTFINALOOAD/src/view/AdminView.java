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
	public static String CURRENT_LOCATION = "adminHP";

    private Label titleLabel, userLabel;
    private TableView<User> userTable;
    private ObservableList<User> users;
    private Button deleteButton, viewEventsButton;
    private UserController userController;
    private AdminController adminController;
    private NavbarView navbarView;

    public AdminView() {
        initializeComponents();
        configureComponents();
        setLayout();
        setUserTable();
    }

    private void initializeComponents() {
    	navbarView = new NavbarView();
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
        	navbarView,	
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