package dataAccess;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class EventDAOTest {
    Database db;
    Connection newConn;
    EventDAO daoEvent;

    @BeforeEach
    void setUp() {
        db = new Database();
        newConn = db.openConnection();
        db.createTables();
        daoEvent = new EventDAO(newConn);
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(true);
        db = null;
    }

    @Test
    void insertTestSuccess() {
        Event testEvent = new Event();
        testEvent.setPersonID("daviesr3");
        testEvent.setEventType("wedding");
        testEvent.setAssociatedUsername("davies");
        testEvent.setCity("Draper");
        testEvent.setCountry("USA");
        testEvent.setYear(2021);
        assertTrue(daoEvent.insert(testEvent));
    }

    @Test
    void insertTestFail() {
        // empty event with null fields can't be inserted
        Event testEvent = new Event();
        assertFalse(daoEvent.insert(testEvent));
    }

    @Test
    void findByIDTestSuccess() {
        Event event = new Event();
        event.setPersonID("daviesr3");
        event.setEventType("wedding");
        event.setAssociatedUsername("davies");
        event.setCity("Draper");
        event.setCountry("USA");
        event.setYear(2021);
        daoEvent.insert(event);
        String id = daoEvent.findByUsername("davies").getEventID();
        assertNotNull(daoEvent.findByID(id));
    }

    @Test
        // Invalid ID returns null
    void findByIDTestFail() {
        Event event = new Event();
        event.setPersonID("daviesr3");
        event.setEventType("wedding");
        event.setAssociatedUsername("davies");
        event.setCity("Draper");
        event.setCountry("USA");
        event.setYear(2021);
        assertNull(daoEvent.findByID("daviesr3"));
    }

    @Test
    void deleteAllEventsTest() {
        Event event = new Event();
        event.setPersonID("daviesr3");
        event.setEventType("wedding");
        event.setAssociatedUsername("davies");
        event.setCity("Draper");
        event.setCountry("USA");
        event.setYear(2021);

        assertTrue(daoEvent.deleteAllEvents());

    }
}