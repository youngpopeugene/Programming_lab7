package main.java.database;

import main.java.Server;
import main.java.collection.HumanBeing;
import main.java.util.Session;
import main.java.util.Text;

import java.sql.*;
import java.util.logging.Logger;

public class DBWorker {
    final static Logger logger = Logger.getLogger(Server.class.getName());
    private final static Connection connection = new DBConnector().connect();

    public static void init() {
        try {
            new DBInit(connection).init();
        } catch (SQLException e) {
            logger.warning(Text.getRedText("Cannot create tables!"));
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet getCollection() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Statements.takeAll.getStatement());
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.warning(Text.getRedText("Problems with SQL!"));
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean checkUser(Session session) {
        ResultSet resultSet;
        try {
            PreparedStatement st = connection.prepareStatement(Statements.checkUsername.getStatement());
            st.setString(1, session.getUsername());
            resultSet = st.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.warning(Text.getRedText("Problems with SQL!"));
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean checkPassword(Session session) {
        try {
            ResultSet resultSet;
            PreparedStatement st;
            if (session.getPassword() == null) {
                st = connection.prepareStatement(Statements.checkNullPassword.getStatement());
            } else {
                st = connection.prepareStatement(Statements.checkPassword.getStatement());
                st.setString(2, session.getPassword());
            }
            st.setString(1, session.getUsername());
            resultSet = st.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.warning(Text.getRedText("Problems with SQL!"));
            return false;
        }
    }

    public static void addUser(Session session) {
        String addUserStatement = Statements.addUser.getStatement();
        try {
            PreparedStatement st = connection.prepareStatement(addUserStatement);
            st.setString(1, session.getUsername());
            st.setString(2, session.getPassword());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.warning(Text.getRedText("Problems with SQL!"));
            System.out.println(e.getMessage());
        }
    }

    public static boolean getByID(String username, Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Statements.getById.getStatement())) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) return false;
            if (!resultSet.getString("username").equals(username)) return false;
            return true;
        } catch (SQLException e) {
            logger.warning(Text.getRedText("Problems with SQL!"));
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean addHumanBeing(String username, HumanBeing humanBeing) {
        String addStatement = Statements.addHumanBeing.getStatement();
        try {
            PreparedStatement st = connection.prepareStatement(addStatement);
            setHumanBeingStatement(st, username, humanBeing, "add");
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.warning(Text.getRedText("Problems with SQL!"));
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean updateHumanBeing(String username, HumanBeing humanBeing, Long id){
        if (!getByID(username, id)) return false;
        String updateStatement = Statements.updateHumanBeing.getStatement();
        try{
            PreparedStatement st = connection.prepareStatement(updateStatement);
            setUpdateHumanBeingStatement(st, humanBeing, id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.warning(Text.getRedText("Problems with SQL!"));
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean removeHumanBeing(String username, HumanBeing humanBeing) {
        try {
            Statement st = connection.createStatement();
            ResultSet resultSet;

            resultSet = st.executeQuery("SELECT count(*) from humans");
            resultSet.next();
            int count = resultSet.getInt("count");

            PreparedStatement prst = connection.prepareStatement(Statements.removeHumanBeing.getStatement());
            setHumanBeingStatement(prst, username, humanBeing, "remove");
            prst.execute();

            resultSet = st.executeQuery("SELECT count(*) from humans");
            resultSet.next();
            int countAfterDelete = resultSet.getInt("count");

            return count != countAfterDelete;
        } catch (SQLException e) {
            logger.warning(Text.getRedText("Problems with SQL!"));
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static Long generateId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Statements.generateID.getStatement());
            if (resultSet.next()) {
                return resultSet.getLong("nextval");
            }
            return null;
        } catch (SQLException e) {
            logger.warning(Text.getRedText("Problems with SQL!"));
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static void setHumanBeingStatement(PreparedStatement st, String username, HumanBeing humanBeing,
                                               String commandName) throws SQLException {
        int i = 1;
        if (commandName.equals("add")) {
            humanBeing.setId(generateId());
            st.setLong(i++, humanBeing.getId());
        }

        st.setString(i++, humanBeing.getName());
        st.setLong(i++, humanBeing.getCoordinates().getX());
        st.setLong(i++, humanBeing.getCoordinates().getY());
        st.setDate(i++, Date.valueOf(humanBeing.getCreationDate().toLocalDate()));
        st.setTime(i++, Time.valueOf(humanBeing.getCreationDate().toLocalTime()));
        st.setBoolean(i++, humanBeing.isRealHero());
        st.setBoolean(i++, humanBeing.isHasToothpick());
        st.setFloat(i++, humanBeing.getImpactSpeed());
        st.setLong(i++, humanBeing.getMinutesOfWaiting());
        st.setString(i++, humanBeing.getWeaponType().getStringWeaponType());
        st.setString(i++, humanBeing.getMood().getMood());
        st.setBoolean(i++, humanBeing.getCar().getCool());
        st.setString(i, username);

    }

    private static void setUpdateHumanBeingStatement(PreparedStatement st, HumanBeing humanBeing, Long id) throws SQLException {
        int i = 1;
        st.setString(i++, humanBeing.getName());
        st.setLong(i++, humanBeing.getCoordinates().getX());
        st.setLong(i++, humanBeing.getCoordinates().getY());
        st.setDate(i++, Date.valueOf(humanBeing.getCreationDate().toLocalDate()));
        st.setTime(i++, Time.valueOf(humanBeing.getCreationDate().toLocalTime()));
        st.setBoolean(i++, humanBeing.isRealHero());
        st.setBoolean(i++, humanBeing.isHasToothpick());
        st.setFloat(i++, humanBeing.getImpactSpeed());
        st.setLong(i++, humanBeing.getMinutesOfWaiting());
        st.setString(i++, humanBeing.getWeaponType().getStringWeaponType());
        st.setString(i++, humanBeing.getMood().getMood());
        st.setBoolean(i++, humanBeing.getCar().getCool());
        st.setLong(i, id);
    }
}
