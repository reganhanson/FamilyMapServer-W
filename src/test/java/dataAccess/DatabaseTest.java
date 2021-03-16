package dataAccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database db;

    @BeforeEach
    void setUp() {
        db = new Database();
        db.openConnection();
        db.createTables();
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(true);
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
    }

    @Test
    void testCreateTables() {
    }

    @Test
    void testDeleteTables() {

    }
}