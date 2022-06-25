package main.java.commands;

import main.java.collection.HumanBeing;
import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;
import main.java.util.Text;

import java.util.Comparator;
import java.util.List;

/**
 * Class for print any element with the max realHero field
 */
public class Max_By_Real_Hero extends CommandAbstract {

    public Max_By_Real_Hero(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Respond execute(Command command) {
        List<HumanBeing> elements = collectionManager.getAllElements();
        synchronized (elements) {
            if (collectionManager.size() == 0) return new Respond(Text.getRedText("Collection is empty!"));
            return new Respond(elements.stream()
                    .max(Comparator.comparing(HumanBeing::isRealHero))
                    .get()
                    .toString());
        }
    }
}
