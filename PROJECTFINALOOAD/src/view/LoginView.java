package view;

import controller.UserController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.Session;
import view_controller.ViewController;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginView extends VBox {

    private Label titleLabel, statusLabel;
    private TextField emailField;
    private PasswordField passwordField;
    private Button submitButton, registerButton;
    private UserController userController;

    public LoginView() {
        initializeComponents();
        configureComponents();
        setLayout();
    }

    private void initializeComponents() {
        titleLabel = new Label("Login Page");
        emailField = new TextField();
        passwordField = new PasswordField();
        statusLabel = new Label();
        submitButton = new Button("Login");
        registerButton = new Button("Register");
        userController = new UserController();
    }

    private void configureComponents() {
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10;");

        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");

        registerButton.setOnMouseClicked(event -> 
            ViewController.getInstance(null).navigateToRegister()
        );

        submitButton.setOnMouseClicked(event -> login());
    }

    private void setLayout() {
        HBox buttonBox = new HBox(10, submitButton, registerButton);
        buttonBox.setStyle("-fx-alignment: center;");

        this.getChildren().addAll(
            titleLabel,
            emailField,
            passwordField,
            buttonBox,
            statusLabel
        );

        this.setSpacing(10);
        this.setStyle("-fx-padding: 20; -fx-alignment: center;");
    }

    private void login() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String status = userController.login(email, password);

        if (status.equals("success")) {
            Session.getInstance().setUserSession(userController.getUserByEmail(email));
            String userRole = Session.getInstance().getUserSession().getUser_role();

            if ("Admin".equals(userRole)) {
                ViewController.getInstance(null).navigateToAdmin();
            } else if ("Guest".equals(userRole)||"Vendor".equals(userRole)) {
                ViewController.getInstance(null).navigateToUserHome();
            } else {
            	ViewController.getInstance(null).navigateToEvent();
            }
        } else {
            statusLabel.setText(status);
        }
    }
}
