package services;

import dataAccess.AuthTokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDAO;
import model.*;
import results.GetPersonResult;

public class GetPerson {
    /**
     *
     * @param personID
     * @param authToken
     * @return Person
     */
    public GetPersonResult getPerson(String personID, String authToken) {
        Database db = new Database();
        db.getConnection();
        PersonDAO personAccess = new PersonDAO(db.getConnection());
        AuthTokenDAO tokenAccess = new AuthTokenDAO(db.getConnection());

        try {

            Person foundPerson = personAccess.find(personID);
            if (foundPerson!= null) {
                AuthToken foundToken = tokenAccess.find(authToken); // this line
                if (foundToken!= null) {
                    if (foundPerson.getAssociatedUsername().equals(foundToken.getUserName())) {
                        db.closeConnection(false);
                        return new GetPersonResult(foundPerson.getAssociatedUsername(), foundPerson.getPersonID(), foundPerson.getFirstName(),
                                foundPerson.getLastName(), foundPerson.getGender());
                    }
                }
                else {
                    db.closeConnection(false);
                    System.out.println("Database CLOSED in PERSON");
                    return new GetPersonResult("Error: No such authToken exists");
                }
            }
            else {
                db.closeConnection(false);
                System.out.println("Database CLOSED in PERSON");
                return new GetPersonResult("Error: No such person exists");
            }
            db.closeConnection(false);
            System.out.println("Database CLOSED in PERSON");

        } catch (DataAccessException | NullPointerException e) {
            db.closeConnection(false);
            System.out.println("Database CLOSED in PERSON");
            e.printStackTrace();
        }

        // db.getConnection();
        return new GetPersonResult("Error");
    }
}

/*/person/[personID]
URL Path: /person/[personID]
Example: /person/7255e93e
Description: Returns the single Person object with the specified ID.
HTTP Method: GET
Auth Token Required: Yes
Request Body: None
Errors: Invalid auth token, Invalid personID parameter, Requested person does not belong to this user, Internal server error
Success Response Body:
{
	"associatedUsername": "susan",	// Name of user account this person belongs to
	"personID": "7255e93e",	// Person’s unique ID
	"firstName": "Stuart",		// Person’s first name
	"lastName": "Klocke",		// Person’s last name
	"gender": "m",			// Person’s gender (“m” or “f”)
	“fatherID”: “7255e93e”		// ID of person’s father [OPTIONAL, can be missing]
	“motherID”: “d3gz214j”	// ID of person’s mother [OPTIONAL, can be missing]
	"spouseID":"f42126c8"	// ID of person’s spouse [OPTIONAL, can be missing]
“success”:true		// Boolean identifier
}
Error Response Body:
{
	“message”: “Error: [Description of the error]”
“success”:false		// Boolean identifier
}
*/