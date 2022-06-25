package main.java.commands;

import main.java.collection.HumanBeing;
import main.java.database.DBWorker;
import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;
import main.java.util.Text;

/**
 * Class to update element in collection by id
 */
public class Update extends CommandAbstract {

    public Update(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Respond execute(Command command) {
        HumanBeing humanBeing = command.getHuman();
        HumanBeing humanBeingToUpdate = collectionManager.getById(Long.parseLong(command.getArgName()));
        if (humanBeingToUpdate == null) return new Respond(Text.getRedText("Object hasn't been updated!"));
        if (DBWorker.updateHumanBeing(command.getUsername(), humanBeing, Long.parseLong(command.getArgName()))) {
            humanBeing.setId(Long.parseLong(command.getArgName()));
            collectionManager.remove(humanBeingToUpdate);
            collectionManager.add(humanBeing);
            return new Respond(Text.getGreenText("Object has been updated!"));
        } else {
            return new Respond(Text.getRedText("Object hasn't been updated!"));
        }
    }
}

