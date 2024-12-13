package view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppView {
	BorderPane bp;
	
	
	private void init() {
		bp = new BorderPane();
	}
	
	public AppView(Stage stage) {
		init();
		Scene scene = new Scene(this.bp, 600, 800);
		stage.setScene(scene);
		stage.show();
	}
	
	public BorderPane getContainer() {
		return this.bp;
	}
}
