package dataAccess;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class EventDAOTest {
    Database db;
    EventDAO daoEvent;

    @BeforeEach
    void setUp() {
        db = new Database();
        // newConn = db.openConnection();
        db.clearAllTables();
        db.createTables();
        daoEvent = new EventDAO(db.getConnection());
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(true);
        db = null;
    }

    @Test
    void insertTestSuccess() throws DataAccessException {
        Event testEvent = new Event();
        testEvent.setPersonID("daviesr3");
        testEvent.setEventType("wedding");
        testEvent.setAssociatedUsername("davies");
        testEvent.setCity("Draper");
        testEvent.setCountry("USA");
        testEvent.setYear(2021);
        daoEvent.insert(testEvent);
        assertNotNull(daoEvent.findByID(testEvent.getEventID()));
    }

    @Test
    void insertTestFail() throws DataAccessException {
        // empty event with null fields can't be inserted
        Event testEvent = new Event();
        assertThrows(DataAccessException.class, () -> daoEvent.insert(testEvent));
    }

    @Test
    void findByIDTestSuccess() throws DataAccessException {
        Event event = new Event();
        event.setPersonID("daviesr3");
        event.setEventType("wedding");
        event.setAssociatedUsername("davies");
        event.setCity("Draper");
        event.setCountry("USA");
        event.setYear(2021);
        daoEvent.insert(event);
        assertNotNull(daoEvent.findByID(event.getEventID()));
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

    @Test
    void insert() {
    }

    @Test
    void findByID() {
    }

    @Test
    void findByUsername() {
    }

    @Test
    void deleteEventsByUserID() {
    }

    @Test
    void deleteAllEvents() {
    }
}