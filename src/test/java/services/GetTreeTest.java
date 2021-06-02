package services;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.UserRegisterRequest;
import results.GetTreeResult;
import results.UserRegisterResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetTreeTest {
    User testUser;
    UserRegisterResult result;

    @BeforeEach
    void setUp() {
        // Database db = new Database();
        testUser = new User("password", "email@email.com", "Pablo", "Picasso", "m");
        UserRegisterRequest request = new UserRegisterRequest(testUser.getUserName(), testUser.getPassword(), testUser.getEmail(), testUser.getFirstName(), testUser.getLastName(), testUser.getGender());
        UserRegister service = new UserRegister();
        result = service.registerUser(request);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTreePass() {
        GetTree tree = new GetTree();
        GetTreeResult treeResult = tree.getTree(result.getPersonID(), result.getAuthtoken());
        assertEquals(31, treeResult.getFamilyTree().size());
    }

    @Test
    void getTreeFail() {

    }
}