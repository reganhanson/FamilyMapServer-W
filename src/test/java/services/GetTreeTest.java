package services;

import dataAccess.Database;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.UserRegisterRequest;
import results.GetTreeResult;
import results.UserRegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class GetTreeTest {
    User testUser;
    UserRegisterResult result;

    @BeforeEach
    void setUp() {
        // Database db = new Database();
        testUser = new User("password", "email@email.com", "Pablo", "Picasso", "m");
        UserRegisterRequest request = new UserRegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(), testUser.getFirstName(), testUser.getLastName(), testUser.getGender());
        UserRegister service = new UserRegister();
        result = service.registerUser(request);
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
    void getTreePass() {
        GetTree tree = new GetTree();
        GetTreeResult treeResult = tree.getTree(result.getAuthtoken());
        assertEquals(31, treeResult.getData().size());
    }

    @Test
    void getTreeFail() {
        GetTree tree = new GetTree();
        GetTreeResult treeResult = tree.getTree("fake_auth_token");
        assertFalse(treeResult.isSuccess());
        assertTrue(treeResult.getMessage().contains("Error"));
        assertEquals("Error: Couldn't find the requested user with the given token", treeResult.getMessage());
    }
}