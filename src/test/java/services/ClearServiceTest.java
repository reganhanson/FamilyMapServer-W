package services;

import dataAccess.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    @BeforeEach
    void setUp() {
        Database database = new Database();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void deleteAllData() {
    }
}