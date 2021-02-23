package Services;

public class Clear {
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
