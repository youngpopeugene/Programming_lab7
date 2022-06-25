package main.java.commands;

import main.java.collection.HumanBeing;
import main.java.util.CollectionManager;
import main.java.util.Command;
import main.java.util.Respond;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for displaying all commands with explanations
 */
public class Help extends CommandAbstract {

    public Help(CollectionManager collectionManager) {
        super(collectionManager);
    }

    private static final List<Object> help = Collections.synchronizedList(new ArrayList<>());

    static {
        help.add("help : display help on available commands\n");
        help.add("info : print information about the collection to standard output\n");
        help.add("show : write to standard output all elements of the collection in string representation\n");
        help.add("add {element} : add new element in collection\n");
        help.add("update id {element} : update the value of the collection element whose id is equal to the given one\n");
        help.add("remove_by_id id : remove element from collection by its id\n");
        help.add("clear : clear collection\n");
        help.add("execute_script file_name : read and execute the script from the specified file\n");
        help.add("exit : complete the program\n");
        help.add("add_if_min {element} : add new element in collection if it's value is less than value of the smallest element in collection\n");
        help.add("remove_greater {element} : remove from the collection all elements greater than the given one\n");
        help.add("remove_lower {element} : remove from the collection all elements lower than the given one\n");
        help.add("max_by_real_hero : print any object from the collection, whose realHero field value is the maximum\n");
        help.add("count_greater_than_weapon_type weaponType : display the number of elements whose weaponType field value is greater than the given one\n");
        help.add("filter_by_impact_speed impactSpeed : display elements whose value of the impactSpeed field is equal to the given one");
    }

    @Override
    public Respond execute(Command command) {
        synchronized (help) {
            StringBuilder stringBuilder = new StringBuilder();
            help.stream().forEach(stringBuilder::append);
            return new Respond(stringBuilder.toString());
        }
    }
}
