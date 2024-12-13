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
    Hyperlink link;

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
        link = new Hyperlink("Change profile");
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

//
//public class LoginView extends VBox{
//
//	Label statusLbl;
//	TextField emailTf, passwordTf;
//	Button submitBtn;
//	UserController uc;
//	Button registerBtn;
//	
//	public void init() {
//		emailTf = new TextField();
//		passwordTf = new TextField();
//		statusLbl = new Label();
//		submitBtn = new Button("Login");
//		uc = new UserController();
//		registerBtn = new Button("Register");
//		registerBtn.setOnMouseClicked(e->{
//			ViewController.getInstance(null).navigateToRegister();
//		});
//		submitBtn.setOnMouseClicked(e -> {
//			login();
//		});
//	}
//	
//	private void login() {
//		String email = emailTf.getText();
//		String password = passwordTf.getText();
//		String status = uc.login(email, password);
//		
//		if(status.equals("success")) {
//			Session.getInstance().setUserSession(uc.getUserByEmail(email));
//			String userRole = Session.getInstance().getUserSession().getUser_role();
//			if(userRole.equals("Admin")) {
//				ViewController.getInstance(null).navigateToAdmin();
//			} else if (userRole.equals("Guest")) {
//				ViewController.getInstance(null).navigateToUserInvite();
//			}
//		}else {
//			statusLbl.setText(status);
//		}
//	}
//	
//	public void setLayout() {
//		this.getChildren().addAll(emailTf, passwordTf, statusLbl,submitBtn,registerBtn);
//	}
//	
//	public LoginView() {
//		init();
//		setLayout();
//	}
//}
