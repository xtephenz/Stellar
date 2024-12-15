package main;

import controller.EventController;
import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import view_controller.ViewController;

public class Main extends Application{
	
	public static void main(String[] args) {
		try {
	        System.out.println("Classpath: " + System.getProperty("java.class.path"));
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("Driver MySQL dimuat dengan sukses!");
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    }
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewController vc = ViewController.getInstance(primaryStage);
		EventController ec = new EventController();
		vc.navigateToRegister();
	}

}
