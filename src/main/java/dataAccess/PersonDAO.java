package dataAccess;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that confers between the model Person and the database table of the same name
 */
public class PersonDAO {
    private final Connection conn;

    /**
     * Constructor for PersonDAO
     */
    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * add a person to the database table with sql statements
     * @param person
     */
    public void add(Person person) throws DataAccessException {
        String sql = "INSERT into Person(personID, userName, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException();
        }
    }

    /**
     * find the Person with the given personID in the Person database table
     * @param personID
     * @return person
     */
    public Person find(String personID) throws DataAccessException {
        ResultSet rs = null;
        Person person;
        String sql = "SELECT * FROM Person WHERE PersonID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("UserName"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("FatherID"),
                        rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new DataAccessException();
                }
            }
        }
        return null;
    }

    /**
     * find a person by their username
     * @param userName username
     * @return person with said username
     */
    public ArrayList<Person> findByUsername(String userName) {
        ArrayList<Person> relatives = new ArrayList<>();
        ResultSet rs = null;
        Person person;
        String sql = "SELECT * from Person where UserName = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("UserName"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("FatherID"),
                        rs.getString("MotherID"), rs.getString("SpouseID"));
                relatives.add(person);
            }
            return relatives;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * Delete all person objects affiliated with ID
     */
    public boolean deleteTreeByUserID(String username) {
        String sql = "DELETE FROM person WHERE UserName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete all person objects in the database
     */
    public boolean deleteAllPeople() {
        String sql = "DELETE FROM person";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}