package dataAccess;

import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDAOTest {
    Database db;
    AuthTokenDAO testDAO;
    AuthToken testToken;
    User testUser;

    @BeforeEach
    void setUp() {
        db = new Database();
        db.openConnection();
        db.createTables();
        testUser = new User("password", "email@email.com", "Bob", "Builder", "m");
        testToken = new AuthToken(testUser.getUsername());
        testDAO = new AuthTokenDAO(db.getConnection());
    }

    @AfterEach
    void tearDown() {
        db.clearAllTables();
        db.closeConnection(true);
        db = null;
    }

    @Test
    void testAddSuccess() throws DataAccessException {
        testDAO.add(testToken);
        AuthToken compareTest = testDAO.find(testToken.getAuthTokenID());
        assertNotNull(compareTest);
        assertEquals(testToken, compareTest);
    }

    @Test
    void testAddFail() {
        testDAO.add(testToken);
        assertFalse(testDAO.add(testToken));
    }

    @Test
    void testFindSuccess() throws DataAccessException {
        AuthToken testToken = new AuthToken(testUser.getUsername());
        testDAO.add(testToken);
        assertNotNull(testDAO.find(testToken.getAuthTokenID()));
    }

    @Test
    void testFindFail() throws DataAccessException {
        AuthToken testToken = new AuthToken(testUser.getUsername());
        assertNull(testDAO.find(testToken.getAuthTokenID()));
    }

    @Test
    void testDeleteAllAuthTokens() throws DataAccessException {
        AuthToken testToken = new AuthToken(testUser.getUsername());
        testDAO.add(testToken);
        assertNotNull(testDAO.find(testToken.getAuthTokenID()));
        assertTrue(testDAO.deleteAllAuthTokens());
        assertNull(testDAO.findByID(testUser.getUsername()));
    }
}