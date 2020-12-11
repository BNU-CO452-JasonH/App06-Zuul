import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * Modified by Jason Huggins (dated 11/12/2020)
 */

public class Room 
{
    private Items item;
    private int id;
    private String name;
    private String description;
    // String is the key to a room in that direction
    // east would be an exit that goes to the Room
    private HashMap<String, Room> exits;

    /**
     * Creates a room with a given ID, name and item it contains.
     * @param id The ID of the room.
     * @param name The name of the room.
     * @param item The item inside the room.
     */
    public Room(int id, String name, Items item)
    {
        this.id = id;
        this.name = name;
        this.item = item;
        exits = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        
        for(String exit : keys) 
        {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Sets the description of the room.
     * @param description The room's description.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Sets the item found inside the room.
     * @param item The item in the room.
     */
    public void setItem(Items item)
    {
        this.item = item;
    }

    /**
     * @return The item inside the room.
     */
    public Items getItem()
    {
        return item;
    }

    /**
     * @return The name of the room.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Prints a list of items inside the room.
     */
    public void printItems()
    {
        System.out.println("Items in this room: " + item);
    }
}

