package main.java.commands;

import main.java.util.Command;
import main.java.util.Respond;

/**
 * Interface for commands
 */
public interface CommandInterface {


    Respond execute(Command commandInit);

}
