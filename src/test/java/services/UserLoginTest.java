package services;

import dataAccess.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.UserLoginRequest;
import results.UserLoginResult;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginTest {
    Database db;
    UserDAO userAccess;
    UserLoginResult result;
    UserLoginRequest request;
    UserLogin service;

    @BeforeEach
    void setUp() {
        db = new Database();
        db.createTables();
        service = new UserLogin();
        userAccess = new UserDAO(db.getConnection());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void loginPass() {

    }

    @Test
    void loginFail() {

    }
}