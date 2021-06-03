package dataAccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.*;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonDAOTest {
    Database db;
    Connection testConn;
    PersonDAO daoPerson;
    Person testPerson;

    @BeforeEach
    void setUp() {
        db = new Database();
        db.createTables();
        User testUser = new User("password", "email@gmail.com","Daisy", "Hitchcock", "f");
        testPerson = new Person(testUser.getUserName(), "Regan", "Hanson", "m");
        daoPerson = new PersonDAO(db.getConnection());
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(false);
        db = null;
    }

    @Test
    void testAddSuccess() throws DataAccessException {
        daoPerson.add(testPerson);
        Person compareTest = daoPerson.find(testPerson.getPersonID());
        assertNotNull(compareTest);

        // assertEquals(person, daoPerson.find(person.getPersonID()));
        // assertTrue(daoPerson.add(person));
    }

    @Test
    void testAddFail() throws DataAccessException {
        daoPerson.add(testPerson);
        // add two of the same person
        // should return false & give us the exception printed in personDAO
        assertThrows(DataAccessException.class, () -> daoPerson.add(testPerson));
    }

    @Test
    void testFindByUsernameSuccess() throws DataAccessException {
        daoPerson.add(testPerson);
        assertNotNull(daoPerson.findByUsername(testPerson.getAssociatedUsername()));
    }

    @Test
    void testFindByUsernameFail() {
        assertEquals(0, daoPerson.findByUsername("random_user_id").size());
    }

    @Test
    void testDeleteAllPeople() throws DataAccessException {
        daoPerson.add(testPerson);
        daoPerson.deleteAllPeople();
        assertNull(daoPerson.find(testPerson.getPersonID()));
    }
}