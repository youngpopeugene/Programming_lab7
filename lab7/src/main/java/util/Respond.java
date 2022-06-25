package main.java.util;

import java.io.Serializable;

public class Respond implements Serializable {
    public String command;
    public String answer;

    public Respond(String aAnswer) {
        answer = aAnswer;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
