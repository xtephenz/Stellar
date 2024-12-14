package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import main.Session;

public class User {
	private String user_id;
	private String user_email;
	private String user_name;
	private String user_password;
	private String user_role;
	private String product_name;
	private String product_description;
	private BooleanProperty selected;
	
	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	private static final String ID_PREFIX = "USR";

	public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

	public BooleanProperty getSelected() {
		return selected;
	}
	public void setSelected(BooleanProperty selected) {
		this.selected = selected;
	}
	public User() {
		super();
	}
//	
	public boolean register(String email, String name, String password, String role) {
		boolean isCreated = createUser(email, name, password, role);
	    return isCreated; 
	}
	public String login(String email, String password) {
		return loginInputValidation(email, password);
	}
	
	public boolean changeProfile(String email, String name, String oldPassword, String newPassword) {
		 boolean isUpdated = false;

		    String query = "UPDATE users SET user_email = ?, user_name = ?, user_password = ? WHERE user_id = ?";
		    try (Connection con = DriverManager.getConnection(Connect.getCONNECTION(), Connect.getUSERNAME(), Connect.getPASSWORD());
		         PreparedStatement pstmt = con.prepareStatement(query)) {

		        pstmt.setString(1, email);
		        pstmt.setString(2, name);
		        pstmt.setString(3, newPassword);
		        pstmt.setString(4, Session.getInstance().getUserSession().getUser_id());

		        int result = pstmt.executeUpdate();
		        isUpdated = result > 0;

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return isUpdated;
	}
	public User getUserByEmail(String email) {
		User user = null; 

	    try {
	        String query = String.format("SELECT * FROM users WHERE user_email = '%s'", email);
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        if (rs.next()) {

	            
	            user = new User();
	            user.setUser_id(rs.getString("user_id"));
	            user.setUser_email(rs.getString("user_email"));
	            user.setUser_password(rs.getString("user_password"));
	            user.setUser_name(rs.getString("user_name"));
	            user.setUser_role(rs.getString("user_role"));
	            user.setProduct_name(rs.getString("product_name"));
	            user.setProduct_description(rs.getString("product_description"));
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return user; 
	}
	public User getUserByUsername(String name) {
		User user = null;

	    try {
	        String query = String.format("SELECT * FROM users WHERE user_name = '%s'", name);
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        if (rs.next()) {
	            user = new User();
	            user.setUser_id(rs.getString("user_id"));
	            user.setUser_email(rs.getString("user_email"));
	            user.setUser_password(rs.getString("user_password"));
	            user.setUser_name(rs.getString("user_name"));
	            user.setUser_role(rs.getString("user_role"));
	            user.setProduct_name(rs.getString("product_name"));
	            user.setProduct_description(rs.getString("product_description"));
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return user; 
	}
	public User getUserById(String id) { //TAMBAHAN ASUMSI
		User user = null;

	    try {
	        String query = String.format("SELECT * FROM users WHERE user_id = '%s'", id);
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        if (rs.next()) {

	    
	            user = new User();
	            user.setUser_id(rs.getString("user_id")); 
	            user.setUser_email(rs.getString("user_email"));
	            user.setUser_password(rs.getString("user_password"));
	            user.setUser_name(rs.getString("user_name"));
	            user.setUser_role(rs.getString("user_role"));
	            user.setProduct_name(rs.getString("product_name"));
	            user.setProduct_description(rs.getString("product_description"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return user; 
	}
	public String checkRegisterInput(String email, String name, String password) {
		if(email.equals("")) {
			return "Email can not be Empty!";
		} else if (name.equals("")) {
			return "Name can not be Empty!";
		} else if(password.equals("")) {
			return "Password can not be Empty!";
		} else if(password.length()<5) {
			return "Password must at least be 5 characters long!";
		} else if(!email.contains("@") && !email.contains(".")) {
			return "Email is Invalid!";
		}
		return "valid";
	}
	public String checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		User user = Session.getInstance().getUserSession();
		if(email.equals(user.user_email)) {
			return "Email Must be different!";
		} else if (name.equals(user.user_name)) {
			return "Name must be different!";
		} else if (oldPassword==user.user_password) {
			return "Old Password does not match!";
		} else if(newPassword.length()<5) {
			return "New Password must be longer than 5 Characters!";
		} else {
			return "valid";
		}
	}
	public boolean createUser(String email, String name, String password, String role) {
	    boolean isCreated = false;
	    try {
	        // Generate the next user ID
	        String userId = generateNextUserId();

	        // Construct the SQL query
	        String query = String.format(
	            "INSERT INTO users (user_id, user_email, user_password, user_name, user_role) VALUES ('%s', '%s', '%s', '%s', '%s')",
	            userId, email, password, name, role
	        );

	        // Execute the query
	        int result = Connect.getInstance().execute(query);
	        isCreated = result > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return isCreated;
	}

	public String loginInputValidation(String email, String password) {
		User user = getUserByEmail(email);
		if (user==null) {
			return "User does not Exist!";
		}
		if (user.getUser_password().equals(password)) {
			return "success";
		} else {
			return "Password is Incorrect!";
		}
	}
	public String generateNextUserId() {
	    String nextId = null;
	    try {
	        // Query to get the last inserted user_id, ordered by descending user_id
	        String query = "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1";
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        
	        if (rs.next()) {
	            String lastId = rs.getString("user_id"); // e.g., "USR001"
	            
	            // Extract the numeric part from the last ID
	            int numericPart = Integer.parseInt(lastId.substring(ID_PREFIX.length()));
	            
	            // Increment the numeric part and format it back to a zero-padded string
	            nextId = ID_PREFIX + String.format("%03d", numericPart + 1);
	        } else {
	            // If there are no IDs yet in the database, start with "USR001"
	            nextId = ID_PREFIX + "001";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return nextId;
	}

	
	
	
	
	
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String string) {
		this.user_id = string;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_role() {
		return user_role;
	}
	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
	
	
}
