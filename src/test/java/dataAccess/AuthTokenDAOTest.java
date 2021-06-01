package dataAccess;

import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDAOTest {
    Database db;
    AuthTokenDAO testDAO;
    AuthToken testToken;
    User testUser;

    @BeforeEach
    void setUp() {
        db = new Database();
        db.createTables();
        testUser = new User("password", "email@email.com", "Bob", "Builder", "m");
        testToken = new AuthToken(testUser.getUserName());
        testDAO = new AuthTokenDAO(db.getConnection());
    }

    @AfterEach
    void tearDown() {
        db.clearAllTables();
        db.closeConnection(true);
        db = null;
    }

    @Test
    void testAddSuccess() {
        testDAO.add(testToken);
        AuthToken compareTest = testDAO.find(testToken.getAuthTokenID());
        assertNotNull(compareTest);
        //'
        // assertEquals(testToken, compareTest);
        // assertTrue(testDAO.add(testToken));
    }

    @Test
    void testAddFail() {
        testDAO.add(testToken);
        assertFalse(testDAO.add(testToken));
    }

    @Test
    void testFindSuccess() {
        AuthToken testToken = new AuthToken(testUser.getUserName());
        testDAO.add(testToken);
        assertNotNull(testDAO.find(testToken.getAuthTokenID()));
    }

    @Test
    void testFindFail() {
    }

    @Test
    void testDeleteAllAuthTokens() {
        AuthToken testToken = new AuthToken(testUser.getUserName());
        testDAO.add(testToken);
        assertNotNull(testDAO.find(testToken.getAuthTokenID()));
        assertTrue(testDAO.deleteAllAuthTokens());
        assertNull(testDAO.findByID(testUser.getUserName()));
    }
}