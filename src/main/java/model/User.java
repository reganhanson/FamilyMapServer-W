package model;

/**
 * User class: model for the database table of the same name
 */
public class User {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender; // can only be an "f" or "m"
    private String personID;

    /**
     * Constructor for the User class
     * @param userName
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @param personID
     */
    public User(String userName, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    /**
     * Equals method for this class
     * @param obj
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        User otherUser = (User) obj;
        if (!otherUser.getUserName().equals(this.userName)) {
            return false;
        }
        if (!otherUser.getPassword().equals(this.password)) {
            return false;
        }
        if (!otherUser.getEmail().equals(this.email)) {
            return false;
        }
        if (!otherUser.getFirstName().equals(this.email)) {
            return false;
        }
        if (!otherUser.getLastName().equals(this.lastName)) {
            return false;
        }
        if (!otherUser.getGender().equals(this.gender)) {
            return false;
        }
        return otherUser.getPersonID().equals(this.personID);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
