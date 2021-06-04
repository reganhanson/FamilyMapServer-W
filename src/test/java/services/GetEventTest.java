package services;

import dataAccess.*;
import model.AuthToken;
import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import results.GetEventResult;

import static org.junit.jupiter.api.Assertions.*;

class GetEventTest {
    Event testEvent;
    Database db;
    AuthToken testToken;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        db.getConnection();
        db.clearAllTables();
        db.createTables();

        User testUser = new User("password", "email@email.com", "Bob", "Builder", "m");
        testEvent = new Event("300lol", testUser.getUsername(), testUser.getPersonID(), (float)93.1, (float) 161.2, "Namibia", "zimbeol", "wedding", 1935);
        testToken = new AuthToken(testUser.getUsername());

        UserDAO userAccess = new UserDAO(db.getConnection());
        AuthTokenDAO tokenAccess = new AuthTokenDAO(db.getConnection());
        EventDAO eventAccess = new EventDAO(db.getConnection());
        userAccess.insert(testUser);
        eventAccess.insert(testEvent);
        tokenAccess.add(testToken);

        db.closeConnection(true);
    }

    @AfterEach
    void tearDown() {
        db.getConnection();
        db.clearAllTables();
        db.closeConnection(true);
    }

    @Test
    void getEventPass() {
        GetEvent service = new GetEvent();
        GetEventResult result = service.getEvent(testEvent.getEventID(), testToken.getAuthTokenID());
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());
        assertEquals(testEvent.getPersonID(), result.getPersonID());
    }

    @Test
    void getEventFail() {
        GetEvent service = new GetEvent();
        GetEventResult result = service.getEvent("fake_event_id", testToken.getAuthTokenID());
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Error"));
        assertEquals("Error: No such event exists or the authToken is bad", result.getMessage());

        service = new GetEvent();
        result = service.getEvent(testEvent.getEventID(), "fake_authToken");
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Error"));
        assertEquals("Error: No such event exists or the authToken is bad", result.getMessage());
    }
}