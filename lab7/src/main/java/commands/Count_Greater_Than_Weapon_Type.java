package main.java.commands;

import main.java.collection.HumanBeing;
import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;
import main.java.util.Text;

import java.util.List;

/**
 * Class for count elements which greater than specified weapon type
 */
public class Count_Greater_Than_Weapon_Type extends CommandAbstract {

    public Count_Greater_Than_Weapon_Type(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Respond execute(Command command) {
        List<HumanBeing> elements = collectionManager.getAllElements();
        synchronized (elements) {
            return new Respond(Text.getGreenText("Count : " +
                    elements.stream()
                            .filter(x -> x.getWeaponType().getStringWeaponType().compareTo(command.getArgName()) > 0)
                            .count()));
        }
    }
}
