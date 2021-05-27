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

    @BeforeEach
    void setUp() {
        db = new Database();
        testConn = db.openConnection();
        db.createTables();
        daoPerson = new PersonDAO(testConn);
    }

    @AfterEach
    void tearDown() {
        db.deleteTables();
        db.closeConnection(true);
        db = null;
    }

    @Test
    void testAddSuccess() {
        Person person = new Person("3", "daveyr3", "Regan", "Hanson", "m", null, null, null);
        assertTrue(daoPerson.add(person));
    }

    @Test
    void testAddFail() {
        Person person = new Person("64724", "daveyr3", "Regan", "Hanson", "m", null, null, null);
        daoPerson.add(person);
        // add two of the same person
        // should return false & give us the exception printed in personDAO
        assertFalse(daoPerson.add(person));
    }

    @Test
    void testFindByUsernameSuccess() {
        Person person = new Person("64724", "daveyr3", "Regan", "Hanson", "m", null, null, null);
        daoPerson.add(person);
        assertNotNull(daoPerson.findByUsername("daveyr3"));
    }

    @Test
    void testFindByUsernameFail() {
        assertNull(daoPerson.findByUsername("daveyr3"));
    }

    @Test
    void testDeleteAllPeople() {
        assertTrue(daoPerson.deleteAllPeople());
    }
}