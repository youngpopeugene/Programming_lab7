package main.java.commands;

import main.java.collection.HumanBeing;
import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;
import main.java.util.Text;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to print all elements from collection
 */
public class Show extends CommandAbstract {

    public Show(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Respond execute(Command command) {
        List<HumanBeing> elements = collectionManager.getAllElements();
        synchronized (elements) {
            if (collectionManager.size() == 0) return new Respond(Text.getRedText("Collection is empty!"));
            return new Respond(elements.stream()
                    .map(HumanBeing::toString)
                    .collect(Collectors.joining("\n")));
        }
    }
}
