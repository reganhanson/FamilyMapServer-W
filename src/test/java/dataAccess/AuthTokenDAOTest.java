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

    @BeforeEach
    void setUp() {
        db = new Database();
        db.createTables();
        testToken = new AuthToken(UUID.randomUUID().toString(),
                "daviesr3","rdh");
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
        AuthToken compareTest = testDAO.find(testToken.getUserName());
        assertNotNull(compareTest);
        //'
        // assertEquals(testToken, compareTest);
        // assertTrue(testDAO.add(testToken));
    }

    @Test
    void testAddFail() {
        AuthToken testToken = new AuthToken(UUID.randomUUID().toString(),
                "daviesr3", "rdh");
        testDAO.add(testToken);
        assertFalse(testDAO.add(testToken));
    }

    @Test
    void testFindSuccess() {
        AuthToken testToken = new AuthToken(UUID.randomUUID().toString(),
                "hay20", "a2");
        testDAO.add(testToken);
        assertNotNull(testDAO.find("hay20"));
    }

    @Test
    void testFindFail() {
    }

    @Test
    void testDeleteAllAuthTokens() {
        AuthToken testToken = new AuthToken(UUID.randomUUID().toString(),
                "hay20", "a2");
        testDAO.add(testToken);
        assertNotNull(testDAO.find("hay20"));
        assertTrue(testDAO.deleteAllAuthTokens());
    }
}