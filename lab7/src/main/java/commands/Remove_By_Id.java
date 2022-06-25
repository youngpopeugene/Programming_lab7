package main.java.commands;

import main.java.collection.HumanBeing;
import main.java.database.DBWorker;
import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;
import main.java.util.Text;

/**
 * Class for remove element by id
 */
public class Remove_By_Id extends CommandAbstract {

    public Remove_By_Id(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Respond execute(Command command) {
        HumanBeing humanBeing = collectionManager.getById(Long.parseLong(command.getArgName()));
        if (humanBeing != null) {
            if (DBWorker.removeHumanBeing(command.getUsername(), humanBeing)) collectionManager.remove(humanBeing);
            else return new Respond(Text.getRedText("No permission for editing!"));
        } else return new Respond(Text.getRedText("An object with this ID does not exist!"));
        return new Respond(Text.getGreenText("Object has been removed!"));
    }
}
