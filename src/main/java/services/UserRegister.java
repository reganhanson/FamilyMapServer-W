package services;

import dataAccess.AuthTokenDAO;
import dataAccess.Database;
import dataAccess.UserDAO;
import model.AuthToken;
import model.User;
import requests.UserRegisterRequest;
import results.UserRegisterResult;

import java.util.UUID;

public class UserRegister {
    /**
     * @param request
     * @return UserRegisterResult
     */
    public UserRegisterResult registerUser(UserRegisterRequest request) {
        Database database = new Database();
        UserDAO accessUser = new UserDAO(database.getConnection());
        User newUser = new User(request.getUserName(), request.getPassword(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getGender());

        if (accessUser.insert(newUser)) {
            AuthToken sessionToken = new AuthToken(UUID.randomUUID().toString(), request.getUserName(), request.getPassword());
            AuthTokenDAO accessToken = new AuthTokenDAO(database.getConnection());
            accessToken.add(sessionToken);
            return new UserRegisterResult(sessionToken.getAuthTokenID(), request.getUserName(), newUser.getPersonID());
        }
        else {
            return new UserRegisterResult("DAO insert failed");
        }
    }
}
