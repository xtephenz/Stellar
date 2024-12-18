package controller;
import main.Session;
import model.User;
public class UserController {
	private User user = new User();
	public String register(String email, String name, String password, String role) {
		if (getUserByEmail(email)!=null) {
        	return "Email is already Taken!";
        } 
		if (getUserByUsername(name)!=null) {
        	return "Username is already Taken!";
        }
		String registerInputStatus = checkRegisterInput(email, name, password);
		Boolean roleInputStatus = checkRoleInput(role); //TAMBAHAN ASUMSI
		if (registerInputStatus.equals("valid")) {
			if(roleInputStatus) {
				if(user.register(email, name, password, role)) {
					return "success";
				} else {
					return "Register Failed!";
				}
			} else {
				return "Please select a role!";
			}
		} else {
			return registerInputStatus;
		}
	}
	public String login(String email, String password) {
		String status = user.login(email, password);
		if (email.equals("") || password.equals("")) {
			return "Fields can not be Empty!";
		} else {
			return status;
		}
	}
	public String changeProfile(String email, String name, String oldPassword, String newPassword) {
		if(getUserByEmail(name)!=null && getUserByEmail(email)!=Session.getInstance().getUserSession()) {
			System.out.println("email already taken!");
			return "Email Already Taken!";
		} 
		String inputValidation = checkChangeProfileInput(email, name, oldPassword, newPassword);
		if (inputValidation!="valid") {
			System.out.println(inputValidation);
			return inputValidation;
		} else {
			if(user.changeProfile(email, name, oldPassword, newPassword)) {
				return "Profile Succesfully Updated!";
			} else {
				System.out.println("profile failed");
				return "Profile Update Failed!";
			}
		}
	}
	public User getUserByEmail(String email) {
		return user.getUserByEmail(email);
	}
	public User getUserByUsername(String name) {
		return user.getUserByUsername(name);
	}
	public User getUserById(String id) {
		return user.getUserById(id);
	}
	public String checkRegisterInput(String email, String name, String password) {
		return user.checkRegisterInput(email, name, password);
	}
	public String checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
	return user.checkChangeProfileInput(email, name, oldPassword, newPassword);
	}
	private boolean checkRoleInput(String role) {
		if(role==null) {
			return false;
		} else {
			return true;
		}
	}
}
