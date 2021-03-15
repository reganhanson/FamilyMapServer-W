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
        assertEquals("davies", eventAccess.findByID("a1").getAssociatedUsername());
    }

    @Test
    void findByIDTest() {
        // assertNotNull(eventAccess.findByID("a2"));
    }

    @Test
    void findByUsernameTest() {
    }

    @Test
    void deleteAllEventsTest() {
    }
}