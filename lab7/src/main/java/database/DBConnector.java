package main.java.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    String login = "evgenijtulyakov";
    String password = "";
    String host = "jdbc:postgresql://localhost:5432/studs";

    public Connection connect() {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("DB driver not found!");
            return null;
        }

        try {
            conn = DriverManager.getConnection(host, login, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return conn;
    }
}
