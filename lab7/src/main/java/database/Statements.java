package main.java.database;

public enum Statements { //try to + clear
    addHumanBeing("INSERT INTO humans " +
            "(id,name,xCoordinate,yCoordinate,creationDate,creationTime,realHero," +
            "hasToothpick,impactSpeed,minutesOfWaiting,weaponType,mood,carCool,username) " +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"),
    generateID("SELECT nextval('sequence')"),
    removeHumanBeing("DELETE FROM humans where " +
            "name = ? AND xCoordinate = ? AND yCoordinate = ? AND creationDate = ? and creationTime = ? AND realHero = ? AND hasToothpick = ? AND " +
            "impactSpeed = ? AND minutesOfWaiting = ? AND weaponType = ? AND mood = ? AND carCool = ? AND username = ?"),
    checkPassword("SELECT * FROM users WHERE username = ? AND hashPassword = ?"),
    checkUsername("SELECT * FROM users where username = ?"),
    checkNullPassword("SELECT * FROM users where username = ? AND hashPassword is NULL"),
    addUser("INSERT INTO users " +
            "(username,hashPassword) " +
            "VALUES(?,?)"),
    takeAll("SELECT * FROM humans"),
    getById("SELECT * FROM humans WHERE id = ?"),
    updateHumanBeing("UPDATE humans SET " +
            "name=?, xCoordinate=?, yCoordinate=?, creationDate=?, creationTime=?, realHero=?, " +
            "hasToothpick=?, impactSpeed=?, minutesOfWaiting=?, weaponType=?, mood=?, carCool=? " +
            "WHERE id = ?");

    private final String statement;

    Statements(String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }
}
