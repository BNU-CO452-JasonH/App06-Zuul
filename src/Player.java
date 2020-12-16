import java.util.ArrayList;

/**
 * The Player class stores information about the player of the Zuul game, including their energy level, score and items
 * in possession.
 *
 * @author Jason Huggins
 * @version 16/12/2020
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
     * @return The player's currently set name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The player's current energy level.
     */
    public int getEnergy()
    {
        return energy;
    }

    /**
     * @return The player's current score.
     */
    public int getScore()
    {
        return score;
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

    /**
     * Changes the player's current name to another name.
     * @param name The new name for the player.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Changes the player's energy level to a given amount.
     * @param energy The amount to set the player's energy level to.
     */
    public void setEnergy(int energy)
    {
        this.energy = energy;
    }

    /**
     * Changes the player's score to a given amount.
     * @param score The amount to set the player's score to.
     */
    public void setScore(int score)
    {
        this.score = score;
    }

    /**
     * Picks up an item in the current room and stores it in the player's inventory.
     * @param item The item to be picked up by the player.
     */
    public void takeItem(Items item)
    {
        inventory.add(item);
    }

    /**
     * Drops an item stored in the player's inventory.
     * @param item The item to be dropped by the player.
     */
    public void dropItem(Items item)
    {
        if (!inventory.contains(item))
        {
            System.out.println("Invalid choice: This item does not exist in your inventory.");
        }
        else
        {
            System.out.println("The following item has been dropped: " + item);
            inventory.remove(item);
        }
    }
}
