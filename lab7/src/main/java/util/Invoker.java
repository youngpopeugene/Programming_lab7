package main.java.util;

import main.java.commands.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Invoker {
    private final CollectionManager collectionManager;
    private final Map<String, CommandAbstract> command_map;

    public Invoker(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        command_map = Collections.synchronizedMap(new HashMap<>());
        initMap();
    }

    public Respond execute(Command newCommand) {
        CommandAbstract command = command_map.get(newCommand.getCommandName());
        return command.execute(newCommand);
    }

    public void initMap() {
        command_map.put("help", new Help(collectionManager));
        command_map.put("info", new Info(collectionManager));
        command_map.put("show", new Show(collectionManager));
        command_map.put("add", new Add(collectionManager));
        command_map.put("update", new Update(collectionManager));
        command_map.put("remove_by_id", new Remove_By_Id(collectionManager));
        command_map.put("clear", new Clear(collectionManager));
        command_map.put("add_if_min", new Add_If_Min(collectionManager));
        command_map.put("remove_greater", new Remove_Greater(collectionManager));
        command_map.put("remove_lower", new Remove_Lower(collectionManager));
        command_map.put("max_by_real_hero", new Max_By_Real_Hero(collectionManager));
        command_map.put("count_greater_than_weapon_type", new Count_Greater_Than_Weapon_Type(collectionManager));
        command_map.put("filter_by_impact_speed", new Filter_By_Impact_Speed(collectionManager));
        command_map.put("exit", new Exit(collectionManager));

    }
}

