package dataAccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    Database db;
    Connection testConn;
    UserDAO daoUser;
    User testUser;

    @BeforeEach
    void setUp() {
        db = new Database();
        testConn = db.openConnection();
        db.createTables();
        daoUser = new UserDAO(testConn);
        testUser = new User("password", "email@gmail.com",
                "Daisy", "Hitchcock", "f");
    }

    @AfterEach
    void tearDown() {
        // daoUser.deleteAllUsers();
        db.clearAllTables();
        db.closeConnection(true);
        db = null;
    }


    @Test
    void testInsertSuccess() throws DataAccessException{
        daoUser.insert(testUser);
        assertNotNull(daoUser.find(testUser.getUserName()));
    }

    @Test
    void testInsertFail() throws DataAccessException {
        daoUser.insert(testUser);

        // repeat with the same user
        assertThrows(DataAccessException.class, () -> daoUser.insert(testUser));
    }

    @Test
    void testFindSuccess() throws DataAccessException{
        daoUser.insert(testUser);
        assertNotNull(daoUser.find(testUser.getUserName()));
    }

    @Test
    void testFindFail() throws DataAccessException{
        daoUser.insert(testUser);
        assertNull(daoUser.find("random_user_id"));
    }

    @Test
    void testDeleteAllUsers() {
       daoUser.find(testUser.getUserName());
    }

}