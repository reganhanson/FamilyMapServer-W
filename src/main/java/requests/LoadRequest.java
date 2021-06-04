package requests;

import java.util.ArrayList;
import model.*;

/**
 *  Load Request is an object that is received from the client
 *  Must contain:
 *  User array
 *  Events array
 *  A persons array
 */
public class LoadRequest {
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    public LoadRequest() {
        this.users = null;
        this.persons = null;
        this.events = null;
    }

    public LoadRequest(ArrayList<User> users, ArrayList<Person> people, ArrayList<Event> events) {
        this.users = users;
        this.persons = people;
        this.events = events;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
