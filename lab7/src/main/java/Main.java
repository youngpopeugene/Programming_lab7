package main.java;

import main.java.database.DBConnector;
import main.java.database.DBInit;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Connection connection = new DBConnector().connect();
        new DBInit(connection).init();
    }
}
