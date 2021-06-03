package services;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import results.FillResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class FillService {
    ArrayList<String> mNames, fNames, sNames;
    Locations locations;
    Database db;
    int numEvents;
    int numPeople;

    /**
     * @param username
     * @param generations
     * @return FillResult
     */
    public FillResult fill(String username, int generations) {
        numEvents = 0;
        numPeople = 0;

        db = new Database();
        db.openConnection();
        System.out.println("Database OPENED in FILL");
        UserDAO userAccess = new UserDAO(db.getConnection());
        EventDAO eventAccess = new EventDAO(db.getConnection());
        PersonDAO personAccess = new PersonDAO(db.getConnection());

        try {
            if (generations < 1) {
                db.closeConnection(false);
                System.out.println("Database CLOSED in FILL");
                return new FillResult("Error: Invalid number of generations input", false);
            }
            if (username.equals("")) {
                db.closeConnection(false);
                System.out.println("Database CLOSED in FILL");
                return new FillResult("Error: empty username", false);
            }
            User primeUser = userAccess.find(username);
            if (primeUser == null) {
                db.closeConnection(false);
                System.out.println("Database CLOSED in FILL");
                return new FillResult("Error: User not found in the database", false);
            }
            // delete everything associated with the user
            eventAccess.deleteEventsByUserID(username);
            personAccess.deleteTreeByUserID(username);
            // db.closeConnection(true);

            // fill x generations of person and event data
            addListOfPlaces();
            addListOfNames();

            Person originalPerson = new Person(primeUser.getPersonID(), primeUser.getUserName(), primeUser.getFirstName(), primeUser.getLastName(), primeUser.getGender());
            Event birthEvent = new Event(username, originalPerson.getPersonID(), locations.getData()[0].latitude,
                    locations.getData()[0].longitude, locations.getData()[0].getCountry(),
                    locations.getData()[0].getCity(), "birth", getRandomNumber(1990, 2020));

            eventAccess.insert(birthEvent);
            numEvents++;

            createTree(originalPerson, generations, birthEvent.getYear());
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            System.out.println("Database CLOSED in FILL");
            return new FillResult("ERROR: Internal Server Error", false);

        }
        db.closeConnection(true);
        System.out.println("Database CLOSED in FILL");
        return new FillResult("Successfully added X persons and Y events to the database.", true);
    }

    private void createTree(Person person, int generations, int birthYear) {
        Random randomNumber = new Random();

        try {

            Person father = new Person(person.getAssociatedUsername(), mNames.get(randomNumber.nextInt(mNames.size())), sNames.get(randomNumber.nextInt(sNames.size())), "m");
            Person mother = new Person(person.getAssociatedUsername(), fNames.get(randomNumber.nextInt(fNames.size())), father.getLastName(), "f");

            PersonDAO personAccess = new PersonDAO(db.getConnection());

            person.setMotherID(mother.getPersonID());
            person.setFatherID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());

            int randNum = getRandomNumber(0, locations.getData().length);
            Event motherBirth = new Event(person.getAssociatedUsername(), mother.getPersonID(),
                    locations.getData()[randNum].latitude, locations.getData()[randNum].longitude,
                    locations.getData()[randNum].country, locations.getData()[randNum].city,
                    "birth", getRandomNumber(birthYear - 35, birthYear - 15));
            Event fatherBirth = new Event(person.getAssociatedUsername(), father.getPersonID(),
                    locations.getData()[randNum].latitude, locations.getData()[randNum].longitude,
                    locations.getData()[randNum].country, locations.getData()[randNum].city,
                    "birth", getRandomNumber(birthYear - 40, birthYear - 25));
            Event mDeath = new Event(person.getAssociatedUsername(), mother.getPersonID(),
                    locations.getData()[randNum].latitude, locations.getData()[randNum].longitude,
                    locations.getData()[randNum].country, locations.getData()[randNum].city,
                    "death", getRandomNumber(birthYear - 100, birthYear - 35));
            Event fDeath = new Event(person.getAssociatedUsername(), father.getPersonID(),
                    locations.getData()[randNum].latitude, locations.getData()[randNum].longitude,
                    locations.getData()[randNum].country, locations.getData()[randNum].city,
                    "death", getRandomNumber(birthYear - 100, birthYear - 40));
            randNum = randNum = getRandomNumber(0, locations.getData().length);
            Event mWedding = new Event(person.getAssociatedUsername(), mother.getPersonID(),
                    locations.getData()[randNum].latitude, locations.getData()[randNum].longitude,
                    locations.getData()[randNum].country, locations.getData()[randNum].city,
                    "wedding", getRandomNumber(motherBirth.getYear() + 18, motherBirth.getYear() + 25));
            Event fWedding = new Event(person.getAssociatedUsername(), father.getPersonID(),
                    locations.getData()[randNum].latitude, locations.getData()[randNum].longitude,
                    locations.getData()[randNum].country, locations.getData()[randNum].city,
                    "wedding", mWedding.getYear());

            EventDAO eventAccess = new EventDAO(db.getConnection());
            eventAccess.insert(motherBirth);
            eventAccess.insert(fatherBirth);
            eventAccess.insert(mDeath);
            eventAccess.insert(fDeath);
            eventAccess.insert(mWedding);
            eventAccess.insert(fWedding);
            numEvents = numEvents + 6;

            personAccess.add(person);
            numPeople++;

            generations -= 1;
            if (generations == 0) {
                personAccess.add(father);
                personAccess.add(mother);
                numPeople = numPeople + 2;
                return;
            }

            createTree(father, generations, fatherBirth.getYear());
            createTree(mother, generations, motherBirth.getYear());

        } catch (DataAccessException e) {
            db.closeConnection(false);
            System.out.println("Database CLOSED in FILL");
            e.printStackTrace();
        }

    }

    private int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    public void addListOfNames() {
        File maleNamesFile = new File("json/mnames.json");
        File femaleNamesFile = new File("json/mnames.json");
        File surNamesFile = new File("json/mnames.json");

        try {
            FileReader in = new FileReader(maleNamesFile);
            JsonReader reader = new JsonReader(in);
            Gson gson = new Gson();
            Name maleNames = gson.fromJson(reader, Name.class);
            mNames = maleNames.getNames();

            in = new FileReader(femaleNamesFile);
            reader = new JsonReader(in);
            gson = new Gson();
            Name femaleNames = gson.fromJson(reader, Name.class);
            fNames = femaleNames.getNames();

            in = new FileReader(surNamesFile);
            reader = new JsonReader(in);
            gson = new Gson();
            Name surNames = gson.fromJson(reader, Name.class);
            sNames = surNames.getNames();


        } catch (FileNotFoundException e) {
            db.closeConnection(false);
            System.out.println("Database CLOSED in FILL");
            e.printStackTrace();
        }

    }

    public void addListOfPlaces() {

        try {
            File locationsFile = new File("json/locations.json");
            FileReader in = new FileReader(locationsFile);
            Gson gson = new Gson();
            locations = gson.fromJson(in, Locations.class);

        } catch (FileNotFoundException e) {
            db.closeConnection(false);
            System.out.println("Database CLOSED in FILL");
            e.printStackTrace();
        }

    }

    private class Name {
        public Name(ArrayList<String> data) {
            this.data = data;
        }

        private ArrayList<String> data;

        public ArrayList<String> getNames() {
            return data;
        }

        public void setNames(ArrayList<String> data) {
            this.data = data;
        }
    }

    private class Locations {
        public Place[] getData() {
            return data;
        }

        public void setData(Place[] data) {
            this.data = data;
        }

        Place[] data;
    }

    private class Place {
        private String country;
        private String city;
        private float latitude;
        private float longitude;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }
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