package services;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
    Database db;
    User testUser;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        db.createTables();
        db.clearAllTables();

        UserDAO userAccess = new UserDAO(db.getConnection());
        testUser = new User("password", "email@email.com", "Bob", "Builder", "f");
        userAccess.insert(testUser);
        db.closeConnection(true);

        FillService fillService = new FillService();
        fillService.fill(testUser.getUsername(), 4);
    }

    @AfterEach
    void tearDown() {
        db.getConnection();
        db.clearAllTables();
        db.closeConnection(true);
    }

    @Test
    void deleteAllData() {
        db.getConnection();
        PersonDAO personAccess = new PersonDAO(db.getConnection());
        EventDAO eventAccess = new EventDAO(db.getConnection());
        UserDAO userAccess = new UserDAO(db.getConnection());

        assertEquals(testUser, userAccess.find(testUser.getUsername()));
        assertNotNull(personAccess.findByUsername(testUser.getUsername()));
        assertNotNull(eventAccess.findByUsername(testUser.getUsername()));
        db.closeConnection(false);

        ClearService clearService = new ClearService();
        clearService.deleteAllData();

        // Connection conn = db.getConnection();
        personAccess = new PersonDAO(db.getConnection());
        eventAccess = new EventDAO(db.getConnection());
        userAccess = new UserDAO(db.getConnection());

        ArrayList<Person> testTree = new ArrayList<>();
        ArrayList<Event> testEvent = new ArrayList<>();

        assertEquals(testEvent, eventAccess.findByUsername(testUser.getUsername()));
        assertNotEquals(testUser, userAccess.find(testUser.getUsername()));
        assertEquals(testTree, personAccess.findByUsername(testUser.getUsername()));
        db.closeConnection(false);
    }
}