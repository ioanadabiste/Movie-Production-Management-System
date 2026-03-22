package model.repository;

import java.sql.*;

public class DatabaseManager {

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver SQL Server nu a fost găsit!");
        }
    }

    public static Connection getConnection() throws SQLException {
        // Conexiune doar cu URL, fără username și password
        return DriverManager.getConnection(DatabaseConfig.getConnectionUrl());
    }
}