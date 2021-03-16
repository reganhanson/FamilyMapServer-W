package dataAccess;

import model.Event;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.sql.Connection;

class EventDAOTest {
    Database db;
    Connection newConn;

    @BeforeEach
    void setUp() {
        db = new Database();
        newConn = db.openConnection();
        db.createTables();
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(true);
        db = null;
    }

    @Test
    void insertTest() {
        EventDAO eventAccess = new EventDAO(newConn);
        Event event = new Event();
        event.setPersonID("daviesr3");
        event.setEventType("wedding");
        event.setAssociatedUsername("davies");
        event.setCity("Draper");
        event.setCountry("USA");
        event.setYear(2021);
        eventAccess.insert(event);
        assertEquals("daviesr3", eventAccess.findByUsername("davies").getPersonID());
    }

    @Test
    void findByIDTest() {
        EventDAO eventAccess = new EventDAO(newConn);
        Event event = new Event();
        event.setPersonID("daviesr3");
        event.setEventType("wedding");
        event.setAssociatedUsername("davies");
        event.setCity("Draper");
        event.setCountry("USA");
        event.setYear(2021);
        eventAccess.insert(event);
        String id = eventAccess.findByUsername("davies").getEventID();
        assertNotNull(eventAccess.findByID(id));
    }

    @Test
    void findByUsernameTest() {
    }

    @Test
    void deleteAllEventsTest() {
    }
}