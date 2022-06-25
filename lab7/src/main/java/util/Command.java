package main.java.util;

import main.java.collection.HumanBeing;

import java.io.Serializable;

public class Command implements Serializable {
    private final String commandName;
    private final String argName;
    private HumanBeing human;
    private String username;

    public Command(String commandName, String argName, String username) {
        this.commandName = commandName;
        this.argName = argName;
        this.username = username;
    }

    public void setHuman(HumanBeing human) {
        this.human = human;
    }

    public HumanBeing getHuman() {
        return human;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgName() {
        return argName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String aUsername) {
        username = aUsername;
    }

    @Override
    public String toString() {
        return "CommandInit{" +
                "commandName='" + commandName + '\'' +
                ", argName='" + argName + '\'' +
                '}';
    }
}
