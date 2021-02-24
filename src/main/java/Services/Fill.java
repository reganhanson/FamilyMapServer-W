package Services;

import model.*;

public class Fill {
    /**
     * Populates the server's database with generated data for the specified user name.
     * User with given username must be already registered with the server.
     * in this case, generations is set to the default 4
     * @param username
     */
    public void fill(String username) {
    }

    /**
     * Same as above, except overloaded with whatever # of generations you choose
     * @param username
     * @param generations (optional) overloaded in this case
     */
    public void fill(String username, int generations) {
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