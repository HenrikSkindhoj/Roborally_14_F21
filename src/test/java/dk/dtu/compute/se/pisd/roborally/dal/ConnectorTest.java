package dk.dtu.compute.se.pisd.roborally.dal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorTest {

    private static final String HOST     = "localhost";
    private static final int    PORT     = 3306;
    private static final String DATABASE = "pisu";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    @BeforeEach
    void setUp() {
        try {
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?serverTimezone=UTC";
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Test
    void testConnection() throws SQLException {
        boolean testConnection = false;

        if(!connection.isClosed())
        {
            testConnection = true;
        }

        assertTrue(testConnection);

    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }
}