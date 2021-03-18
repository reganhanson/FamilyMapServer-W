package dataAccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;
import java.sql.Connection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    Database db;
    Connection testConn;
    UserDAO daoUser;

    @BeforeEach
    void setUp() {
        db = new Database();
        testConn = db.openConnection();
        db.createTables();
        daoUser = new UserDAO(testConn);
    }

    @AfterEach
    void tearDown() {
        daoUser.deleteAllUsers();
        db.closeConnection(true);
        db = null;
    }


    @Test
    void testInsertSuccess() {
        User testUser = new User(UUID.randomUUID().toString(), "password", "hay@gmail.com",
                "Daisy", "Hitchcock", "f", "a4");
        assertTrue(daoUser.insert(testUser));
    }

    @Test
    void testInsertFail() {
        User testUser = new User(UUID.randomUUID().toString(), "password", "hay@gmail.com",
                "Daisy", "Hitchcock", "f", "a2");
        daoUser.insert(testUser);
        // repeat with the same user
        assertFalse(daoUser.insert(testUser));
    }

    @Test
    void testFindSuccess() {
        User testUser = new User("hay17", "password", "hay@gmail.com",
                "Daisy", "Hitchcock", "f", "a4");
        daoUser.insert(testUser);
        assertNotNull(daoUser.find("hay17"));
    }

    @Test
    void testFindFail() {
        User testUser = new User(UUID.randomUUID().toString(), "password", "hay@gmail.com",
                "Daisy", "Hitchcock", "f", "a1");
        daoUser.insert(testUser);
        assertNull(daoUser.find("hay2"));
    }

    @Test
    void testDeleteAllUsers() {
        assertTrue(daoUser.deleteAllUsers());
    }

}