package services;

import dataAccess.Database;
import dataAccess.UserDAO;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.UserLoginRequest;
import requests.UserRegisterRequest;
import results.UserLoginResult;
import results.UserRegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginTest {
    Database db;
    UserDAO userAccess;
    UserLoginResult result;
    UserLoginRequest request;
    UserLogin service;
    User testUser;

    @BeforeEach
    void setUp() {
        db = new Database();
        db.getConnection();
        db.clearAllTables();
        db.createTables();
        db.closeConnection(true);

        testUser = new User("password", "email@email.com", "Bob", "Builder", "m");

        UserRegisterRequest request = new UserRegisterRequest(testUser.getUserName(), testUser.getPassword(), testUser.getEmail(), testUser.getFirstName(), testUser.getLastName(), testUser.getGender());
        UserRegister registerService = new UserRegister();
        UserRegisterResult result = registerService.registerUser(request);
    }

    @AfterEach
    void tearDown() {
        db.getConnection();
        db.clearAllTables();
        db.createTables();
        db.closeConnection(true);
    }

    @Test
    void loginPass() {
        UserLoginRequest request = new UserLoginRequest(testUser.getUserName(), testUser.getPassword());
        UserLogin loginService = new UserLogin();
        UserLoginResult loginResult = loginService.login(request);
        assertTrue(loginResult.isSuccess());
        assertNull(loginResult.getErrorMessage());
        assertEquals(testUser.getUserName(), loginResult.getUsername());
        // assertEquals(testUser.getPersonID(), loginResult.getPersonID());
    }

    @Test
    void loginFail() {

    }
}