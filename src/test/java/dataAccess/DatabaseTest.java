package dataAccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database db;

    @BeforeEach
    void setUp() {
        db = new Database();
        db.openConnection();
    }

    @AfterEach
    void tearDown() {
        if (db.getConnection() != null) {
            db.closeConnection(false);
        }
        db = null;
    }

    @Test
    void testOpenConnection() {
        assertNotNull(db.getConnection());
    }

    @Test
    void testGetConnection() {
        assertNotNull(db.getConnection());
    }

    @Test
    void testCloseConnection() {
        Connection compareConn = db.getConnection();
        db.closeConnection(false);
        assertNotEquals(compareConn, db.getConnection());
    }

    @Test
    void testCreateTables() {
        db.createTables();
    }

    @Test
    void testClearAllTables() {
        assertTrue(db.clearAllTables());
    }
}