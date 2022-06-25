package main.java.util;

import main.java.collection.HumanBeing;

import java.util.*;

/**
 * Class to work with collection
 */
public class CollectionManager {
//    private static TreeSet<HumanBeing> treeSet;
    private static NavigableSet<HumanBeing> collection;
    private final String initTime;

    public CollectionManager() {
        collection = Collections.synchronizedNavigableSet(new TreeSet<>());
        initTime = new Date().toString();
    }

    public static NavigableSet<HumanBeing> getCollection() {
        return collection;
    }


    public String getInfo() {
        return "Type of collection" + " : " + "TreeSet" + "\n" +
                "Type of collection items" + " : " + "Humans" + "\n" +
                "Initialization date" + " : " + initTime + "\n" +
                "Number of items in the collection" + " : " + collection.size();
    }

    /**
     * Add element to collection
     *
     * @see main.java.commands.Add#execute
     * @see main.java.commands.Add_If_Min#execute
     */
    public void add(HumanBeing human) {
        collection.add(human);
    }

    /**
     * Remove element from collection
     *
     * @see main.java.commands.Remove_By_Id#execute
     */
    public void remove(HumanBeing human) {
        collection.remove(human);
    }

    /**
     * Remove all elements of collection
     *
     * @see main.java.commands.Clear#execute
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Method for return the element of collection by id
     *
     * @return the element of collection by id
     */
    public HumanBeing getById(Long id) {
        for (HumanBeing human : collection) {
            if (human.getId().equals(id)) return human;
        }
        return null;
    }

    public List<HumanBeing> getAllElements() {
        return Collections.synchronizedList(new ArrayList<>(collection));
    }

    /**
     * Method for return the first element of collection
     *
     * @return the first element of collection
     */
    public HumanBeing first() {
        return collection.first();
    }

    /**
     * Method for return the size od collection
     *
     * @return the size od collection
     */
    public int size() {
        return collection.size();
    }
}
