package view;

import controller.UserController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.Session;
import view_controller.ViewController;

public class ChangeProfileView extends VBox{
	public static String CURRENT_LOCATION = "ChangeProfile";
	private Label titleLabel, nameLabel, emailLabel, oldPasswordLabel, newPasswordLabel, messageLabel;
	private TextField nameField, emailField;
    
    private PasswordField oldPasswordField, newPasswordField;
    private Button saveButton, backButton;
	public ChangeProfileView() {
		init();
		setLayout();
		setAction();
	}
	private void setLayout() {
		this.getChildren().addAll(titleLabel, nameLabel, nameField,
	            emailLabel, emailField,
	            oldPasswordLabel, oldPasswordField,
	            newPasswordLabel, newPasswordField,
	            saveButton,
	            messageLabel,
	            backButton
	    );
		this.setSpacing(10); // Add spacing between elements
        this.setStyle("-fx-padding: 20; -fx-alignment: center;"); // Add some styling
	}
	private void init() {
		titleLabel= new Label("Change Profile");
		// Labels and input fields
        nameLabel = new Label("Name:");
        nameField = new TextField();
        nameField.setPromptText("Enter your name");

        emailLabel = new Label("Email:");
        emailField = new TextField();
        emailField.setPromptText("Enter your email");

        oldPasswordLabel = new Label("Old Password:");
        oldPasswordField = new PasswordField();
        oldPasswordField.setPromptText("Enter your current password");

        newPasswordLabel = new Label("New Password:");
        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Enter your new password");

        // Message label for feedback
        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");

        // Buttons
        saveButton = new Button("Save Changes");
		backButton = new Button("Back to Homepage");
	        
	        // Set action for back button
	       
	}
	
	private void setAction() {
		saveButton.setOnAction(e -> saveChanges());
		if(Session.getInstance().getUserSession().getUser_role().equals("Admin")) {
			backButton.setOnAction(e -> {
	            ViewController.getInstance(null).navigateToAdmin();
	        });
		}else if(Session.getInstance().getUserSession().getUser_role().equals("Event Organizer")) {
			backButton.setOnAction(e -> {
	            ViewController.getInstance(null).navigateToEvent();
	        });
		}else {
			backButton.setOnAction(e -> {
	            ViewController.getInstance(null).navigateToUserHome();
	        });
		}
	}
	private void saveChanges() {
		 String name = nameField.getText().trim();
	        String email = emailField.getText().trim();
	        String oldPassword = oldPasswordField.getText().trim();
	        String newPassword = newPasswordField.getText().trim();

	        if (name.isEmpty() || email.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty()) {
	            messageLabel.setText("All fields are required.");
	            return;
	        }

	        // Invoke the `changeProfile` method
	        String result = changeProfile(email, name, oldPassword, newPassword);

	        // Update messageLabel based on the result
	        if (result.equals("Profile Succesfully Updated!")) {
	            messageLabel.setStyle("-fx-text-fill: green;");
	            messageLabel.setText(result);
	        } else {
	            messageLabel.setStyle("-fx-text-fill: red;");
	            messageLabel.setText(result);
	        }
    }

	private String changeProfile(String email, String name, String oldPassword, String newPassword) {
        // Replace this with the actual backend call
        UserController profileController = new UserController();
        return profileController.changeProfile(email, name, oldPassword, newPassword);
    }
}

