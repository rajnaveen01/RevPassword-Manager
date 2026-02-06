package com.revpm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "scott";
    private static final String PASSWORD = "tiger";

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // FIXED: Always return a new connection instance
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC Driver not found!");
            throw new SQLException("Driver missing", e);
        }
    }
}