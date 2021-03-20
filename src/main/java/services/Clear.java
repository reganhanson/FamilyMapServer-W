package services;

import dataAccess.Database;
import results.ClearResult;

public class Clear {
    /**
     * Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data
     */
    public ClearResult deleteAllData() {
        Database db = new Database();
        //try {
        if (db.deleteTables()) {
            return new ClearResult("Successful clear", true);
        } else {
            return new ClearResult("Clear error", false);
        }
        // }
        /*catch (SQLException e) {
            return new ClearResult(e.toString(), false);
        }*/
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
        “message”: “Clear succeeded.”
    “success”:true		// Boolean identifier
    }
    Error Response Body:
    {
        “message”: “Error: [Description of the error]”
    “success”:false		// Boolean identifier
    }
    */
