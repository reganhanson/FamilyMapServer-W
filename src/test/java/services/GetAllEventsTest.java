package services;

import dataAccess.AuthTokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.UserRegisterRequest;
import results.GetAllEventsResult;
import results.UserRegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class GetAllEventsTest {
    User testUser;
    UserRegisterResult result;
    AuthToken userToken;

    @BeforeEach
    void setUp() throws DataAccessException {
        testUser = new User("password", "email@email.com", "Pablo", "Picasso", "m");
        UserRegisterRequest request = new UserRegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(), testUser.getFirstName(), testUser.getLastName(), testUser.getGender());
        UserRegister service = new UserRegister();
        result = service.registerUser(request);

        Database db = new Database();
        AuthTokenDAO tokenAccess = new AuthTokenDAO(db.getConnection());
        userToken = tokenAccess.find(result.getAuthtoken());
        db.closeConnection(false);
    }

    @AfterEach
    void tearDown() {
        Database db = new Database();
        db.getConnection();
        db.clearAllTables();
        db.createTables();
        db.closeConnection(true);
    }

    @Test
    void getAllEventsPass() {
        GetAllEvents service = new GetAllEvents();
        GetAllEventsResult eventsResult = service.getAllEvents(userToken);
        assertTrue(eventsResult.isSuccess());
        assertNull(eventsResult.getMessage());
        assertEquals(91, eventsResult.getData().size());
    }

    @Test
    void getAllEventsFail() {
        AuthToken wrongAuthToken = new AuthToken(testUser.getUsername());
        GetAllEvents service = new GetAllEvents();
        GetAllEventsResult eventsResult = service.getAllEvents(wrongAuthToken);
        assertNotEquals(userToken, wrongAuthToken);
        assertFalse(eventsResult.isSuccess());
        assertTrue(eventsResult.getMessage().contains("Error"));
        assertEquals("Error: Bad token", eventsResult.getMessage());
    }
}