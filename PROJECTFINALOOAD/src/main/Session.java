package main;
import model.User;

public class Session {
    private static Session instance;
    private User UserSession = new User();
    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }
	public User getUserSession() {
		return UserSession;
	}

	public void setUserSession(User userSession) {
		UserSession = userSession;
	}
}
