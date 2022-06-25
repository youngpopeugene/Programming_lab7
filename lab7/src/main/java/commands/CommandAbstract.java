package main.java.commands;

import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;

/**
 * Abstract class for commands
 */
public abstract class CommandAbstract implements CommandInterface {
    final CollectionManager collectionManager;

    public CommandAbstract(CollectionManager collection) {
        collectionManager = collection;
    }

    @Override
    public abstract Respond execute(Command commandInit);

}
