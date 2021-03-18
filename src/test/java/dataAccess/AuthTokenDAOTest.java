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
    Connection testConn;
    AuthTokenDAO testDAO;

    @BeforeEach
    void setUp() {
        db = new Database();
        testConn = db.openConnection();
        db.createTables();
        testDAO = new AuthTokenDAO(testConn);
    }

    @AfterEach
    void tearDown() {
        db.deleteTables();
        db.closeConnection(true);
        db = null;
    }

    @Test
    void testAddSuccess() {
        AuthToken testToken = new AuthToken(UUID.randomUUID().toString(),
                "daviesr3","rdh");
        assertTrue(testDAO.add(testToken));
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