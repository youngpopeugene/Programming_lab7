package main.java.commands;

import main.java.collection.HumanBeing;
import main.java.database.DBWorker;
import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;
import main.java.util.Text;


/**
 * Class for read study group from console and add this in collection
 */
public class Add extends CommandAbstract {

    public Add(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Respond execute(Command command) {
        HumanBeing humanBeing = command.getHuman();
        if (DBWorker.addHumanBeing(command.getUsername(), humanBeing)) {
            collectionManager.add(humanBeing);
            return new Respond(Text.getGreenText("Object has been added!"));
        } else {
            return new Respond(Text.getRedText("Object hasn't been added!"));
        }
    }

}
