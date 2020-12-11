import java.util.ArrayList;

/**
 * The Player class stores information about the player of the Zuul game, including their energy level, score and items
 * in possession.
 *
 * @author Jason Huggins
 * @version 11/12/2020
 */
public class Player
{
    // Constants
    public static final int MAX_ENERGY = 100;
    public static final int WIN_SCORE = 1000;

    // Fields/attributes
    private String name;
    private int energy;
    private int score;
    private ArrayList<Items> inventory;
    private Room currentRoom;

    /**
     * Constructor for a Player object.
     */
    public Player(String name)
    {
        this.name = name;
        energy = MAX_ENERGY;
        score = 0;
        inventory = new ArrayList<>();
    }

    /**
     * Returns a string containing all items in the player's inventory.
     * @return A string with all items currently in the player's possession.
     */
    public String getInventory()
    {
        String inventoryString = "";

        for (Items item : inventory)
        {
            inventoryString += item.toString() + ", ";
        }

        return inventoryString;
    }
}
