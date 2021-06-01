package services;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDAO;
import dataAccess.UserDAO;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import results.FillResult;

import javax.xml.crypto.Data;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {
    Database db;
    UserDAO userAccess;
    User sampleUser;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        userAccess = new UserDAO(db.getConnection());
        sampleUser = new User("password", "email@email.com", "Bob", "Builder", "m");
        userAccess.insert(sampleUser);
        db.closeConnection(true);
    }

    @AfterEach
    void tearDown() {
        if (db.getConnection() == null) {
            db.openConnection();
        }
        db.clearAllTables();
        db.closeConnection(true);
    }

    @Test
    void testFillFail() {
        // db = new Database();
        db.openConnection();

        FillService fillService = new FillService();
        FillResult result = fillService.fill(sampleUser.getUserName(), 0);
        assertFalse(result.isSuccess());

        db.clearAllTables();
        db.closeConnection(true);
        result = fillService.fill(sampleUser.getUserName(), 3);
        assertFalse(result.isSuccess());

    }

    @Test
    void testFillPass() {

        FillService fillService = new FillService();
        FillResult fillResult = fillService.fill(sampleUser.getUserName(), 4);

        db.openConnection();
        PersonDAO personAccess = new PersonDAO(db.getConnection());
        ArrayList<Person> tree = personAccess.findByUsername(sampleUser.getUserName());
        assertNotNull(tree);
        assertEquals(31, tree.size());
        for (Person person : tree) {
            assertNotNull(person);
        }

        /*Check events*/
    }
}