package results;

/**
 * /person/[personID] command
 * Gets a single person, requires:
 * associatedUsername, personID, first and last name, gender
 * --optionally--
 * fatherID, motherID, spouseID
 * --or--
 * if unsuccessful, error message
 * --and--
 * success boolean
 */
public class GetPersonResult {
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    private boolean success;
    private String message;

    /*========================= Constructors =============================*/

    /**
     * Constructor without related IDs
     * @param associatedUsername user name
     * @param personID person id
     * @param firstName first name of person
     * @param lastName last name of person
     * @param gender person's gender
     */
    public GetPersonResult(String associatedUsername, String personID, String firstName, String lastName, String gender) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
        this.success = true;
        this.message = null;
    }

    /**
     * Constructor with related IDs
     * @param associatedUsername user name
     * @param personID person id
     * @param firstName first name of person
     * @param lastName last name of person
     * @param gender person's gender
     * @param fatherID person's dad
     * @param motherID person's mom
     * @param spouseID person's spouse
     */
    public GetPersonResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = true;
        this.message = null;
    }

    /**
     * Error message constructor
     * @param message error message
     */
    public GetPersonResult(String message) {
        this.message = message;
        this.success = false;
        this.associatedUsername = null;
        this.personID = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
    }

    /*====================== Getters and Setters =========================*/

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
