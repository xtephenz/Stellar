package controller;
import java.util.Vector;
import model.Invitation;

public class InvitationController {
	private Invitation invitation = new Invitation();
	UserController uc = new UserController();
	public InvitationController() {
		// TODO Auto-generated constructor stub
	}
	public boolean sendInvitation(String eventID, String email) {
		return invitation.sendInvitation(eventID, email);
	}
	public boolean acceptInvitation(String eventID, String user_id) {
		return invitation.acceptInvitation(eventID, user_id);
	}
	public Vector<Invitation> getInvitations(String email) {
		return invitation.getInvitations(email);
	}
	public boolean createInvitation(String eventID, String userID, String invitationRole) {
		return invitation.createInvitation(eventID, userID, invitationRole);
	}

}
