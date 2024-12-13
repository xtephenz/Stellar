package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class NavbarView {
	public MenuBar navbar= new MenuBar();
	public Menu Login = new Menu("Login");
	public void setButton() {
		Login.setOnAction(e -> {
			
		});
	}
}
