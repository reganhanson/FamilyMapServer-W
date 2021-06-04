package services;

import dataAccess.Database;
import results.ClearResult;

public class ClearService {
    /**
     * Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data
     */
    public ClearResult deleteAllData() {
        Database db = new Database();       // connects to the database with new connection
        db.openConnection();
        //System.out.println("Database OPENED in CLEAR");

        if (db.clearAllTables()) {
            db.closeConnection(true);
            //System.out.println("Database CLOSED in CLEAR");
            return new ClearResult("Clear succeeded", true);
        } else {
            db.closeConnection(false);
            //System.out.println("Database CLOSED in CLEAR");
            return new ClearResult("Internal service error", false);
        }
    }
}

    /*/clear
    URL Path: /clear
    Description: Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data.
    HTTP Method: POST
    Auth Token Required: No
    Request Body: None
    Errors: Internal server error
    Success Response Body:
    {
        “message”: “ClearService succeeded.”
    “success”:true		// Boolean identifier
    }
    Error Response Body:
    {
        “message”: “Error: [Description of the error]”
    “success”:false		// Boolean identifier
    }
    */
