package services;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import results.FillResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {
    Database db = new Database();
    UserDAO userAccess;
    User sampleUser;

    @BeforeEach
    void setUp() throws DataAccessException {
        db.openConnection();
        db.createTables();

        userAccess = new UserDAO(db.getConnection());
        sampleUser = new User("password", "email@email.com", "Bob", "Builder", "m");
        userAccess.insert(sampleUser);
        db.closeConnection(true);
    }

    @AfterEach
    void tearDown() {
        db.openConnection();
        db.clearAllTables();
        db.closeConnection(false);
        userAccess = null;
    }

    @Test
    void testFillFail() {
        FillService fillService = new FillService();
        FillResult result = fillService.fill(sampleUser.getUsername(), 0);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Error"));
        assertEquals("Error: Invalid number of generations input", result.getMessage());
    }

    @Test
    void testFillPass() {

        FillService fillService = new FillService();
        FillResult fillResult = fillService.fill(sampleUser.getUsername(), 4);
        assertTrue(fillResult.isSuccess());

        PersonDAO personAccess = new PersonDAO(db.getConnection());
        ArrayList<Person> tree = personAccess.findByUsername(sampleUser.getUsername());
        assertNotNull(tree);
        assertEquals(31, tree.size());

        EventDAO eventAccess = new EventDAO(db.getConnection());
        ArrayList<Event> eventTree = eventAccess.findByUsername(sampleUser.getUsername());
        assertNotNull(eventTree);
        assertEquals(91, eventTree.size());
        db.closeConnection(false);
    }
}