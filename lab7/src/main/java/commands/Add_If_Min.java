package main.java.commands;

import main.java.collection.HumanBeing;
import main.java.database.DBWorker;
import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;
import main.java.util.Text;

/**
 * Class for add minimal element in collection
 */
public class Add_If_Min extends CommandAbstract {

    public Add_If_Min(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Respond execute(Command command) {
        HumanBeing humanBeing = command.getHuman();
        if (collectionManager.first().compareTo(humanBeing) > 0) {
                return new Add(collectionManager).execute(command);
        } else return new Respond(Text.getRedText("Object hasn't been added!"));
    }
}
