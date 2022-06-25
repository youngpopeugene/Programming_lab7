package main.java.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInit {
    private final Connection dbConnection;

    public DBInit(Connection aConnection) {
        dbConnection = aConnection;
    }

    public void init() throws SQLException {
        Statement st = dbConnection.createStatement();

        st.executeUpdate("CREATE TABLE IF NOT EXISTS humans (" +
                "id bigint PRIMARY KEY," +
                "name varchar(255) NOT NULL CHECK(name<>'')," +
                "xCoordinate bigint NOT NULL CHECK(xCoordinate<=695)," +
                "yCoordinate bigint NOT NULL," +
                "creationDate date DEFAULT (current_date)," +
                "creationTime time DEFAULT (current_time)," +
                "realHero boolean," +
                "hasToothpick boolean," +
                "impactSpeed float," +
                "minutesOfWaiting bigint," +
                "weaponType varchar(8)," +
                "mood varchar(8) NOT NULL CHECK(mood='SADNESS' OR mood='APATHY' OR mood='CALM' OR mood='RAGE' OR mood='FRENZY')," +
                "carCool boolean NOT NULL," +
                "username varchar(255) NOT NULL" +
                ")"
        );

        st.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                "username varchar(255) PRIMARY KEY," +
                "hashPassword varchar(255) DEFAULT (NULL)" +
                ")");

        st.executeUpdate("CREATE SEQUENCE IF NOT EXISTS sequence START 1");

    }

}
