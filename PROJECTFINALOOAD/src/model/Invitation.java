package model;

import java.sql.ResultSet;
import java.util.Vector;

import controller.UserController;
import database.Connect;
public class Invitation {
    String invitation_id;
    String event_id;
    String user_id;
    String invitation_status;
    String invitation_role;

    UserController uc = new UserController();
    private static final String ID_PREFIX = "INV";

    // Generate the next invitation_id
    public String generateNextInvitationId() {
        String nextId = null;
        try {
            String query = "SELECT invitation_id FROM invitations ORDER BY invitation_id DESC LIMIT 1";
            ResultSet rs = Connect.getInstance().execQuery(query);

            if (rs.next()) {
                String lastId = rs.getString("invitation_id"); // e.g., "INV001"
                int numericPart = Integer.parseInt(lastId.substring(ID_PREFIX.length()));
                nextId = ID_PREFIX + String.format("%03d", numericPart + 1);
            } else {
                // Start with "INV001" if no invitations exist
                nextId = ID_PREFIX + "001";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextId;
    }

    // Create a new invitation
    public boolean createInvitation(String eventID, String userID, String invitationRole) {
    	System.out.println("boo");
        boolean isCreated = false;
        try {
            String invitationId = generateNextInvitationId(); // Generate unique ID
            String query = String.format(
                "INSERT INTO `invitations`(`invitation_id`, `event_id`, `user_id`, `invitation_status`, `invitation_role`) VALUES ('%s','%s','%s','%s','%s')",
                invitationId, eventID, userID, "Pending", invitationRole
            );
            int result = Connect.getInstance().execute(query);
            isCreated = result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isCreated;
    }

    // Send an invitation
    public boolean sendInvitation(String eventID, String email) {
        User user = uc.getUserByEmail(email);
        if (user == null) return false; // User must exist
        String userRole = user.getUser_role();
        return createInvitation(eventID, user.getUser_id(), userRole);
    }

    // Accept an invitation
    public boolean acceptInvitation(String eventID, String user_id) {
        boolean isUpdated = false;
        try {
            String query = String.format(
                "UPDATE `invitations` SET `invitation_status`='%s' WHERE event_id = '%s' AND user_id = '%s'",
                "Accepted", eventID, user_id
            );
            int result = Connect.getInstance().execute(query);
            isUpdated = result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    // Get invitations for a user
    public Vector<Invitation> getInvitations(String email) {
        User user = uc.getUserByEmail(email);
        if (user == null) return new Vector<>(); // Return an empty list if user doesn't exist

        Vector<Invitation> invitationList = new Vector<>();
        try {
            String query = String.format("SELECT * FROM invitations WHERE user_id = '%s'", user.getUser_id());
            ResultSet rs = Connect.getInstance().execQuery(query);
            while (rs.next()) {
                Invitation invitation = new Invitation();
                invitation.setInvitation_id(rs.getString("invitation_id"));
                invitation.setEvent_id(rs.getString("event_id"));
                invitation.setUser_id(rs.getString("user_id"));
                invitation.setInvitation_status(rs.getString("invitation_status"));
                invitation.setInvitation_role(rs.getString("invitation_role"));
                invitationList.add(invitation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invitationList;
    }

    // Getters and Setters
    public String getInvitation_id() {
        return invitation_id;
    }

    public void setInvitation_id(String invitation_id) {
        this.invitation_id = invitation_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getInvitation_status() {
        return invitation_status;
    }

    public void setInvitation_status(String invitation_status) {
        this.invitation_status = invitation_status;
    }

    public String getInvitation_role() {
        return invitation_role;
    }

    public void setInvitation_role(String invitation_role) {
        this.invitation_role = invitation_role;
    }
}
