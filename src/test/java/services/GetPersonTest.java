package services;

import dataAccess.*;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import results.GetPersonResult;

import static org.junit.jupiter.api.Assertions.*;

class GetPersonTest {
    Person testPerson;
    User testUser;
    AuthToken testToken;
    Database database;

    @BeforeEach
    void setUp() throws DataAccessException {
        database = new Database();
        database.getConnection();
        database.clearAllTables();
        database.createTables();

        testUser = new User("password", "email@email.com", "Bob", "Builder", "m");
        testPerson = new Person(testUser.getPersonID(), testUser.getUsername(), testUser.getFirstName(), testUser.getLastName(),  testUser.getGender());
        testToken = new AuthToken(testUser.getUsername());

        PersonDAO personAccess = new PersonDAO(database.getConnection());
        UserDAO userAccess = new UserDAO(database.getConnection());
        AuthTokenDAO tokenAccess = new AuthTokenDAO(database.getConnection());

        userAccess.insert(testUser);
        personAccess.add(testPerson);
        tokenAccess.add(testToken);

        database.closeConnection(true);
    }

    @AfterEach
    void tearDown() {
        database.getConnection();
        database.clearAllTables();
        database.closeConnection(true);
    }

    @Test
    void getPersonPass() {
        //database.getConnection();
        //database.closeConnection(false);
        GetPerson handledPerson = new GetPerson();
        GetPersonResult result = handledPerson.getPerson(testPerson.getPersonID(), testToken.getAuthTokenID());
        assertTrue(result.isSuccess());
    }

    @Test
    void getPersonFail() {
        // Errors: Invalid auth token, Invalid personID parameter, Requested person does not belong to this user, Internal server error
        GetPerson handledPerson = new GetPerson();
        assertFalse(handledPerson.getPerson(testPerson.getPersonID(), "fake_auth_token").isSuccess());
    }
}