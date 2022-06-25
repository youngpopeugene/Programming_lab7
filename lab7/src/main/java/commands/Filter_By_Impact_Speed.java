package main.java.commands;

import main.java.collection.HumanBeing;
import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;
import main.java.util.Text;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for print elements which have specific impact speed
 */
public class Filter_By_Impact_Speed extends CommandAbstract {

    public Filter_By_Impact_Speed(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Respond execute(Command command) {
        List<HumanBeing> elements = collectionManager.getAllElements();
        synchronized (elements) {
            String respond = elements.stream()
                    .filter(x -> x.getImpactSpeed().equals(Float.valueOf(command.getArgName())))
                    .map(HumanBeing::toString)
                    .collect(Collectors.joining("\n"));

            if (respond.length() == 0) return new Respond(Text.getGreenText("No one has the same impact speed :("));

            return new Respond(respond);
        }

    }
}


