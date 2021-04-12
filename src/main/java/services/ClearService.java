package services;

import dataAccess.Database;
import results.ClearResult;

public class ClearService {
    /**
     * Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data
     */
    public ClearResult deleteAllData() {
        Database db = new Database();
        //try {
        if (db.deleteTables()) {
            return new ClearResult("Successful clear", true);
        } else {
            return new ClearResult("ClearService error", false);
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
        “message”: “ClearService succeeded.”
    “success”:true		// Boolean identifier
    }
    Error Response Body:
    {
        “message”: “Error: [Description of the error]”
    “success”:false		// Boolean identifier
    }
    */
