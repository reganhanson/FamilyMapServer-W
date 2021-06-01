package services;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import dataAccess.Database;
import dataAccess.EventDAO;
import dataAccess.PersonDAO;
import dataAccess.UserDAO;
import model.Person;
import results.FillResult;

import java.io.*;
import java.util.ArrayList;

public class FillService {
    ArrayList<String> mNames, fNames, sNames;
    /**
     * @param username
     * @param generations
     * @return FillResult
     */
    public FillResult fill(String username, int generations) {
        Database db = new Database();
        UserDAO userAccess = new UserDAO(db.getConnection());
        EventDAO eventAccess = new EventDAO(db.getConnection());
        PersonDAO personAccess = new PersonDAO(db.getConnection());



        if (generations < 0) {
            return new FillResult("Invalid number of generations input", false);
        }
        if (userAccess.find(username) == null) {
            return new FillResult("User not found in the database", false);
        }
        // delete everything associated with the user
        eventAccess.deleteEventsByUserID(username);
        personAccess.deleteTreeByUserID(username);

        // fill x generations of person and event data
        addListOfNames();
        addListOfPlaces();

        for (int i = 0; i < generations; i++) {
            Person father = new Person(username, mNames.get(i), sNames.get(i), "m");

        }

        return new FillResult("Successfully added X persons and Y events to the database.", true);
    }

    public void addListOfNames() {
        File maleNamesFile = new File("json/mnames.json");
        File femaleNamesFile = new File("json/mnames.json");
        File surNamesFile = new File("json/mnames.json");

        try {
            FileReader in = new FileReader(maleNamesFile);
            JsonReader reader = new JsonReader(in);
            Gson gson = new Gson();
            mNames = gson.fromJson(reader, String.class);

            in = new FileReader(femaleNamesFile);
            reader = new JsonReader(in);
            gson = new Gson();
            fNames = gson.fromJson(reader, String.class);

            in = new FileReader(surNamesFile);
            reader = new JsonReader(in);
            gson = new Gson();
            sNames = gson.fromJson(reader, String.class);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addListOfPlaces() {


    }
}

    /* /fill/[username]/{generations}
    URL Path: /fill/[username]/{generations}
    Example: /fill/susan/3
    Description: Populates the server's database with generated data for the specified user name. The required "username" parameter must be a user already registered with the server. If there is any data in the database already associated with the given user name, it is deleted. The optional “generations” parameter lets the caller specify the number of generations of ancestors to be generated, and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
    HTTP Method: POST
        Auth Token Required: No
        Request Body: None
        Errors: Invalid username or generations parameter, Internal server error
        Success Response Body:
        {
        “message”: “Successfully added X persons and Y events to the database.”
        “success”:true		// Boolean identifier
        }
        Error Response Body:
        {
        “message”: “Error: [Description of the error]”
        “success”:false		// Boolean identifier
        }
        */