package view;

import controller.UserController;
import controller.VendorController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.Session;
import view_controller.ViewController;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ManageVendorView extends VBox {

    private Label titleLabel, statusLabel;
    private TextField productNameField;
    private TextField productDescField;
    private Button submitButton, backButton;
    private UserController userController;
    private VendorController vc;

    public ManageVendorView() {
        initializeComponents();
        configureComponents();
        setLayout();
    }

    private void initializeComponents() {
        titleLabel = new Label("Manage Product Page");
        productNameField = new TextField();
        productDescField = new TextField();
        statusLabel = new Label();
        submitButton = new Button("Update");
        backButton = new Button("Back");
        userController = new UserController();
        vc = new VendorController();
    }

    private void configureComponents() {
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10;");

        productNameField.setPromptText("Product Name");
        productDescField.setPromptText("Product Description");

        backButton.setOnMouseClicked(event -> 
            ViewController.getInstance(null).goBack()
        );

        submitButton.setOnMouseClicked(event -> login());
    }

    private void setLayout() {
        HBox buttonBox = new HBox(10, submitButton, backButton);
        buttonBox.setStyle("-fx-alignment: center;");

        this.getChildren().addAll(
            titleLabel,
            productNameField,
            productDescField,
            buttonBox,
            statusLabel
        );
        this.setStyle("-fx-padding: 20; -fx-alignment: center;");
    }

    private void login() {
        String name = productNameField.getText();
        String desc = productDescField.getText();
        String status = vc.manageVendor(desc, name);
        if (status.equals("valid")) {
            ViewController.getInstance(null).goBack();
        } else {
            statusLabel.setText(status);
        }
    }
}