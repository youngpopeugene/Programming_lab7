package main.java.commands;

import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;
import main.java.util.Text;

public class Exit extends CommandAbstract {
    public Exit(CollectionManager collection) {
        super(collection);
    }

    @Override
    public Respond execute(Command command) {
        return new Respond(Text.getRedText("The client has exited the application"));
    }

}
