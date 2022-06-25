package main.java.commands;

import main.java.collection.HumanBeing;
import main.java.database.DBWorker;
import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;
import main.java.util.Text;

import java.util.List;

/**
 * Class for remove from the collection all elements lower than the given on
 */
public class Remove_Lower extends CommandAbstract {

    public Remove_Lower(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Respond execute(Command command) {
        List<HumanBeing> elements = collectionManager.getAllElements();
        synchronized (elements) {
            HumanBeing humanBeing = command.getHuman();
            Object[] objects = elements.stream()
                    .filter(x -> x.compareTo(humanBeing) > 0).toArray();
            for (Object human : objects) {
                if (DBWorker.removeHumanBeing(command.getUsername(), (HumanBeing) human)) {
                    collectionManager.remove((HumanBeing) human);
                }
            }
            return new Respond(Text.getGreenText("All your greater elements have been removed!"));
        }
    }
}
