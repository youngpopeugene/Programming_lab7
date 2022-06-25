package main.java.commands;

import main.java.collection.HumanBeing;
import main.java.database.DBWorker;
import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;
import main.java.util.Text;

import java.util.List;

/**
 * Class for remove all elements from collection
 */
public class Clear extends CommandAbstract {

    public Clear(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Respond execute(Command command) {
        List<HumanBeing> elements = collectionManager.getAllElements();
        synchronized (elements) {
            for (HumanBeing humanBeing : elements) {
                if (DBWorker.removeHumanBeing(command.getUsername(), humanBeing)) {
                    collectionManager.remove(humanBeing);
                }
            }
            return new Respond(Text.getGreenText("Your objects were cleared successfully!"));
        }
    }
}
