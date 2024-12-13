package view;

import controller.UserController;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.Session;
import model.Admin;
import model.User;
import view_controller.ViewController;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegisterView extends VBox {

    private Label titleLabel, statusLabel;
    private TextField emailField, nameField;
    private PasswordField passwordField;
    private Button submitButton, loginButton;
    private ComboBox<String> roleComboBox;
    private UserController userController;

    public RegisterView() {
        initializeComponents();
        configureComponents();
        setLayout();
    }

    private void initializeComponents() {
        titleLabel = new Label("Register Page");
        emailField = new TextField();
        nameField = new TextField();
        passwordField = new PasswordField();
        statusLabel = new Label();
        submitButton = new Button("Register");
        loginButton = new Button("Login");
        roleComboBox = new ComboBox<>();
        userController = new UserController();
    }

    private void configureComponents() {
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10;");

        emailField.setPromptText("Email");
        nameField.setPromptText("Name");
        passwordField.setPromptText("Password");

        roleComboBox.getItems().addAll("Guest", "Admin", "Vendor", "Event Organizer");
        roleComboBox.setPromptText("Select Role");

        loginButton.setOnMouseClicked(event -> 
            ViewController.getInstance(null).navigateToLogin()
        );

        submitButton.setOnMouseClicked(event -> register());
    }

    private void setLayout() {
        HBox buttonBox = new HBox(10, submitButton, loginButton);
        buttonBox.setStyle("-fx-alignment: center;");

        this.getChildren().addAll(
            titleLabel,
            emailField,
            nameField,
            passwordField,
            roleComboBox,
            buttonBox,
            statusLabel
        );

        this.setSpacing(10);
        this.setStyle("-fx-padding: 20; -fx-alignment: center;");
    }

    private void register() {
        String email = emailField.getText();
        String username = nameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || role == null) {
            statusLabel.setText("Please fill in all fields.");
            return;
        }

        String status = userController.register(email, username, password, role);

        if ("success".equals(status)) {
            Session.getInstance().setUserSession(userController.getUserByEmail(email));
            ViewController.getInstance(null).navigateToLogin();
        } else {
            statusLabel.setText(status);
        }
    }
}
//
//public class RegisterView extends VBox{
//
//	Label statusLbl;
//	TextField emailTf, nameTf, passwordTf;
//	Button submitBtn;
//	ComboBox<String> roleComboBox;
//	Button loginBtn;
//	UserController uc;
//	
//	public void init() {
//		emailTf = new TextField("Email");
//		nameTf = new TextField("Name");
//		passwordTf = new TextField("Password");
//		statusLbl = new Label();
//		submitBtn = new Button("Register");
//		uc = new UserController();
//		roleComboBox = new ComboBox<>();
//		loginBtn = new Button("Login");
//		loginBtn.setOnMouseClicked(e->{
//			ViewController.getInstance(null).navigateToLogin();
//		});
//		submitBtn.setOnMouseClicked(e -> {
//			register();
//		});
//	}
//
//	public void setLayout() {
//		this.getChildren().add(emailTf);
//		this.getChildren().addAll(nameTf, passwordTf, statusLbl, roleComboBox, submitBtn, loginBtn);
//	}
//	
//	public RegisterView() {
//		init();
//		setLayout();
//		setComboBox();
//	}
//	private void setComboBox() {
//		roleComboBox.getItems().addAll("Guest", "Admin", "Vendor", "Event Organizer");
//		roleComboBox.setPromptText("Select Role");
//	}
//	
//	private void register() {
//		String email;
//		String username;
//		String password;
//		try {
//			email = emailTf.getText();
//			username = nameTf.getText();
//			password = passwordTf.getText();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			email = "";
//			username = "";
//			password = "";
//			//e.printStackTrace();
//		}
//		String role = roleComboBox.getValue(); 
//		String status = uc.register(email, username, password, role);
//		
//		if(status.equals("success")) {
//			Session.getInstance().setUserSession(uc.getUserByEmail(email));
//			ViewController.getInstance(null).navigateToLogin();			
//		}else {
//			statusLbl.setText(status);
//		}
//	}
//	
//}
