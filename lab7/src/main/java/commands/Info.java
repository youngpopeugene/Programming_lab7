package main.java.commands;

import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;

/**
 * Class for displaying all commands with explanations
 */
public class Info extends CommandAbstract {

    public Info(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Respond execute(Command command) {
        return new Respond(collectionManager.getInfo());
    }
}
