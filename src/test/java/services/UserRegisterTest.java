package services;

import dataAccess.Database;
import dataAccess.UserDAO;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.UserRegisterRequest;
import results.UserRegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterTest {
    User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("password", "email@email.com", "Bob", "Builder", "m");
    }

    @AfterEach
    void tearDown() {
        Database db = new Database();
        db.openConnection();
        db.clearAllTables();
        db.closeConnection(true);
    }

    @Test
    void registerUserPass() {

        UserRegisterRequest request = new UserRegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(), testUser.getFirstName(), testUser.getLastName(), testUser.getGender());
        UserRegister service = new UserRegister();
        UserRegisterResult result = service.registerUser(request);

        Database db = new Database();
        db.openConnection();

        UserDAO userAccess = new UserDAO(db.getConnection());

        // test to see that UserRegister added the user to the database
        User compareUser = userAccess.find(testUser.getUsername());
        testUser.setPersonID(result.getPersonID());
        assertEquals(compareUser, testUser);

        // test to see that UserRegister correctly called fill and put in 4 generations of data


        db.closeConnection(false);
    }

    @Test
    void registerUserFail() {
        // Request property missing or has invalid value
        UserRegisterRequest request = new UserRegisterRequest(null, testUser.getPassword(), testUser.getEmail(), testUser.getFirstName(), testUser.getLastName(), testUser.getGender());
        UserRegister service = new UserRegister();
        assertNull(service.registerUser(request));

        // username taken already
        request = new UserRegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(), testUser.getFirstName(), testUser.getLastName(), testUser.getGender());
        service = new UserRegister();
        UserRegisterResult result = service.registerUser(request);

        UserRegisterRequest secondRequest = new UserRegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(), testUser.getFirstName(), testUser.getLastName(), testUser.getGender());
        UserRegister secondService = new UserRegister();
        assertNull(service.registerUser(request));

    }
}

/*
/user/register
URL Path: /user/register
Description: Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
HTTP Method: POST
Auth Token Required: No
Request Body:
{
	"username": "susan",		// Non-empty string
	"password": "mysecret",	// Non-empty string
	"email": "susan@gmail.com",	// Non-empty string
	"firstName": "Susan",		// Non-empty string
	"lastName": "Ellis",		// Non-empty string
 "gender": "f"			// “f” or “m”
}
Errors: Request property missing or has invalid value, Username already taken by another user, Internal server error
Success Response Body:
{
	"authtoken": "cf7a368f",	// Non-empty auth token string
	"username": "susan",		// Username passed in with request
	"personID": "39f9fe46"		// Non-empty string containing the Person ID of the
			//  user’s generated Person object
“success”:true		// Boolean identifier
}
Error Response Body:
{
	“message”: “Error: [Description of the error]”
“success”:false		// Boolean identifier
}

 */