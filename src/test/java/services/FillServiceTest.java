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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {
    Database db = new Database();
    UserDAO userAccess;
    User sampleUser;

    @BeforeEach
    void setUp() throws DataAccessException {
        db.openConnection();
        db.createTables();

        userAccess = new UserDAO(db.getConnection());
        sampleUser = new User("password", "email@email.com", "Bob", "Builder", "m");
        userAccess.insert(sampleUser);
        db.closeConnection(true);
    }

    @AfterEach
    void tearDown() {
        db.openConnection();
        db.clearAllTables();
        db.closeConnection(false);
        userAccess = null;
    }

    @Test
    void testFillFail() {
        FillService fillService = new FillService();
        FillResult result = fillService.fill(sampleUser.getUserName(), 0);
        assertFalse(result.isSuccess());
        // result = fillService.fill(sampleUser.getUserName(), 3);
        // assertFalse(result.isSuccess());

    }

    @Test
    void testFillPass() {

        FillService fillService = new FillService();
        FillResult fillResult = fillService.fill(sampleUser.getUserName(), 4);
        assertTrue(fillResult.isSuccess());

        PersonDAO personAccess = new PersonDAO(db.getConnection());
        ArrayList<Person> tree = personAccess.findByUsername(sampleUser.getUserName());
        assertNotNull(tree);
        assertEquals(31, tree.size());
        db.closeConnection(false);
        /*for (Person person : tree) {
            assertNotNull(person);
        }*/

        /*Check events*/
    }
}