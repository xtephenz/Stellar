package model;

import java.sql.ResultSet;
import java.util.Vector;

import database.Connect;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;import java.sql.ResultSet;
import java.util.Vector;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Event {
    private String event_id;
    private String event_name;
    private String event_date;
    private String event_location;
    private String event_description;
    private String organizer_id;
    private BooleanProperty selected = new SimpleBooleanProperty(false);

    private static final String ID_PREFIX = "EVT";

    public BooleanProperty getSelected() {
        return selected;
    }

    public void setSelected(BooleanProperty selected) {
        this.selected = selected;
    }

    public Event() {
        // Default constructor
    }

    // Generate the next event ID
    public String generateNextEventId() {
        String nextId = null;
        try {
            String query = "SELECT event_id FROM events ORDER BY event_id DESC LIMIT 1";
            ResultSet rs = Connect.getInstance().execQuery(query);

            if (rs.next()) {
                String lastId = rs.getString("event_id"); // e.g., "EVT001"
                int numericPart = Integer.parseInt(lastId.substring(ID_PREFIX.length()));
                nextId = ID_PREFIX + String.format("%03d", numericPart + 1);
            } else {
                // Start with "EVT001" if no events exist
                nextId = ID_PREFIX + "001";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextId;
    }

    // Create a new event
    public boolean createEvent(String eventName, String date, String location, String description, String organizerID) {
        boolean isCreated = false;
        try {
            String eventId = generateNextEventId(); // Generate unique ID
            String query = String.format(
                "INSERT INTO `events`(`event_id`, `event_name`, `event_date`, `event_location`, `event_description`, `organizer_id`) " +
                "VALUES ('%s','%s','%s','%s','%s','%s')",
                eventId, eventName, date, location, description, organizerID
            );
            int result = Connect.getInstance().execute(query);
            isCreated = result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isCreated;
    }

    // View all events
    public Vector<Event> viewAllEvents() {
        Vector<Event> eventList = new Vector<>();
        try {
            String query = "SELECT * FROM events";
            ResultSet rs = Connect.getInstance().execQuery(query);
            while (rs.next()) {
                Event event = new Event();
                event.setEvent_id(rs.getString("event_id"));
                event.setEvent_name(rs.getString("event_name"));
                event.setEvent_date(rs.getString("event_date"));
                event.setEvent_location(rs.getString("event_location"));
                event.setEvent_description(rs.getString("event_description"));
                event.setOrganizer_id(rs.getString("organizer_id"));
                eventList.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventList;
    }

    // View event details by event ID
    public Event viewEventDetails(String eventID) {
        Event event = null;
        try {
            String query = String.format("SELECT * FROM events WHERE event_id = '%s'", eventID);
            ResultSet rs = Connect.getInstance().execQuery(query);
            if (rs.next()) {
                event = new Event();
                event.setEvent_id(rs.getString("event_id"));
                event.setEvent_name(rs.getString("event_name"));
                event.setEvent_date(rs.getString("event_date"));
                event.setEvent_location(rs.getString("event_location"));
                event.setEvent_description(rs.getString("event_description"));
                event.setOrganizer_id(rs.getString("organizer_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    // Delete an event by event ID
    public boolean deleteEvent(String eventID) {
        boolean isDeleted = false;
        try {
            String query = String.format("DELETE FROM events WHERE event_id = '%s'", eventID);
            int result = Connect.getInstance().execute(query);
            isDeleted = result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    // Update event details
    public boolean updateEvent(String eventID, String eventName, String date, String location, String description) {
        boolean isUpdated = false;
        try {
            String query = String.format(
                "UPDATE events SET event_name='%s', event_date='%s', event_location='%s', event_description='%s' " +
                "WHERE event_id='%s'",
                eventName, date, location, description, eventID
            );
            int result = Connect.getInstance().execute(query);
            isUpdated = result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    // Getters and Setters
    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getOrganizer_id() {
        return organizer_id;
    }

    public void setOrganizer_id(String organizer_id) {
        this.organizer_id = organizer_id;
    }
    public Event getEventById(String event_id) {
		Event event = null; 

	    try {
	        String query = String.format("SELECT * FROM events WHERE event_id = '%s'", event_id);
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        if (rs.next()) {

	            
	            event = new Event();
	            event.setEvent_id(rs.getString("event_id"));
                event.setEvent_name(rs.getString("event_name"));
                event.setEvent_date(rs.getString("event_date"));
                event.setEvent_location(rs.getString("event_location"));
                event.setEvent_description(rs.getString("event_description"));
                event.setOrganizer_id(rs.getString("organizer_id"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return event; 
	}
}

