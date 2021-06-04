package services;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import requests.LoadRequest;
import results.ClearResult;
import results.LoadResult;

public class Load {
    /**
     * Clears all data from the database and then loads the posted user, person, and event data into the database
     * @param request
     */
    public LoadResult load(LoadRequest request) {
        Database database = new Database();
        try {
            ClearService clearService = new ClearService();
            ClearResult result = clearService.deleteAllData();

            if (!result.isSuccess()) {
                return new LoadResult("Clear error", false);
            }
            database.openConnection();
            //System.out.println("Database OPENED in LOAD");
            UserDAO userAccess = new UserDAO(database.getConnection());
            PersonDAO personAccess = new PersonDAO(database.getConnection());
            EventDAO eventAccess = new EventDAO(database.getConnection());
            // ArrayList<User> userList = request.getUsers();
            // ArrayList<Person> personList = request.getPeople();
            // ArrayList<Event> eventList = request.getEvents();
            int numUser = 0;
            int numPerson = 0;
            int numEvent = 0;

            for (User user : request.getUsers()) {
                //checkForInvalidUser(user);
                userAccess.insert(user);
                numUser++;
            }
            for (Person person : request.getPersons()) {
                //checkForInvalidPerson(person);
                personAccess.add(person);
                numPerson++;
            }
            for (Event event : request.getEvents()) {
                // checkForInvalidEvent(event);
                eventAccess.insert(event);
                numEvent++;
            }
            database.closeConnection(true);
            //System.out.println("Database CLOSED in LOAD");
            return new LoadResult("Successfully added " + numUser + " users, " + numPerson +" persons, and " + numEvent + " events to the database.", true);
        } catch (Exception e) {
            database.closeConnection(false);
            //System.out.println("Database CLOSED in LOAD");
            e.printStackTrace();
            return new LoadResult("Error: failure to insert", false);
        }
    }

    /*public void checkForInvalidUser(User user) throws DataAccessException {
        if (user.getUserName() == null || user.getUserName().equals("")) {
            throw new DataAccessException();
        } else if (user.getPersonID() == null || user.getPersonID().equals("")) {
            throw new DataAccessException();

        } else if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new DataAccessException();

        } else if (user.getEmail() == null || user.getEmail().equals("")) {
            throw new DataAccessException();

        } else if (!(user.getGender().equals("f") || user.getGender().equals("m"))) {
            throw new DataAccessException();

        } else if (user.getFirstName() == null || user.getFirstName().equals("")) {
            throw new DataAccessException();

        } else if (user.getLastName() == null || user.getLastName().equals("")) {
            throw new DataAccessException();
        }
    }

    public void checkForInvalidPerson(Person person) throws DataAccessException {
        if (person.getAssociatedUsername() == null || person.getAssociatedUsername().equals("")) {
            throw new DataAccessException();
        } else if (person.getPersonID() == null || person.getPersonID().equals("")) {
            throw new DataAccessException();

        } else if (!(person.getGender().equals("f") || person.getGender().equals("m"))) {
            throw new DataAccessException();

        } else if (person.getFirstName() == null || person.getFirstName().equals("")) {
            throw new DataAccessException();

        } else if (person.getLastName() == null || person.getLastName().equals("")) {
            throw new DataAccessException();
        }
    }

    public void checkForInvalidEvent(Event event) throws DataAccessException {
        if (event.getAssociatedUsername() == null || event.getAssociatedUsername().equals("")) {
            throw new DataAccessException();
        } else if (event.getPersonID() == null || event.getPersonID().equals("")) {
            throw new DataAccessException();
        } else if (!(event.getEventID().equals("f") || event.getEventID().equals("m"))) {
            throw new DataAccessException();
        } else if (event.getEventType() == null || event.getEventType().equals("")) {
            throw new DataAccessException();
        } else if (event.getCity() == null || event.getCity().equals("")) {
            throw new DataAccessException();
        } else if (event.getCountry() == null || event.getCountry().equals("")) {
            throw new DataAccessException();
        } else if (event.getLatitude() > 360 || event.getLatitude() < 0) {
            throw new DataAccessException();
        }
    }
*/

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