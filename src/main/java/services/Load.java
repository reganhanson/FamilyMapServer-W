package services;

import java.util.Iterator;

import dataAccess.*;
import model.*;
import requests.LoadRequest;
import results.LoadResult;

public class Load {
    /**
     * Clears all data from the database and then loads the posted user, person, and event data into the database
     * @param request
     */
    public LoadResult load(LoadRequest request) {
        Database database = new Database();
        UserDAO userAccess = new UserDAO(database.getConnection());
        PersonDAO personAccess = new PersonDAO(database.getConnection());
        EventDAO eventAccess = new EventDAO(database.getConnection());

        database.clearAllTables();
        try {
            for (User user : request.getUsers()) {
                userAccess.insert(user);
            }
            for (Person person : request.getPeople()) {
                personAccess.add(person);
            }
            for (Event event : request.getEvents()) {
                eventAccess.insert(event);
            }

        return new LoadResult("Successfully added X users, Y persons, and Z events to the database.", true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new LoadResult("Failure to insert", false);
        }
    }
}

    /* /load
    URL Path: /load
    Description: Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
    HTTP Method: POST
    Auth Token Required: No
    Request Body: The “users” property in the request body contains an array of users to be created. The “persons” and “events” properties contain family history information for these users.  The objects contained in the “persons” and “events” arrays should be added to the server’s database.  The objects in the “users” array have the same format as those passed to the /user/register API with the addition of the personID.  The objects in the “persons” array have the same format as those returned by the /person/[personID] API.  The objects in the “events” array have the same format as those returned by the /event/[eventID] API.
    {
        “users”: [  /* Array of User objects  ],
            “persons”: [  /* Array of Person objects   ],
            “events”: [  /* Array of Event objects   ]
            }
            Errors: Invalid request data (missing values, invalid values, etc.), Internal server error
            Success Response Body:
            {
            “message”: “Successfully added X users, Y persons, and Z events to the database.”
            “success”:true		// Boolean identifier
            }
            Error Response Body:
            {
            “message”: “Error: [Description of the error]”
            “success”:false		// Boolean identifier
            }
            */