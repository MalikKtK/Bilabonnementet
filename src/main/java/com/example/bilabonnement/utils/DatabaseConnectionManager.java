package com.example.bilabonnement.utils;
//Udarbejdet af Malik Kütük

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.*;

public class DatabaseConnectionManager {
    private static Connection con;

    private DatabaseConnectionManager() {
    }


    public static Connection getConnection() {
        if (con != null) {
            return con;
        }

        try (InputStream propertiesFile = new FileInputStream("src/main/resources/application.properties")) {
            Properties props = new Properties();
            props.load(propertiesFile);
            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        con = null;
    }
}
