import java.util.ArrayList;

/**
 * The Player class stores information about the player of the Zuul game, including their energy level, score and items
 * in possession.
 *
 * @author Jason Huggins
 * @version 29/12/2020
 */
public class Player
{
    // Constants
    public static final int MAX_ENERGY = 100;
    public static final int WIN_SCORE = 200;

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
     * Finds an item in the player's inventory based on an inputted string by the user.
     * @param target The item name inputted by the user to be found.
     * @return The item stored in inventory if it matches the inputted name or none otherwise.
     */
    public Items findItem(String target)
    {
        for (Items item : inventory)
        {
            if (item.toString().equals(target))
            {
                return item;
            }
        }

        return Items.NONE;
    }

    /**
     * Removes an item from the player's inventory (e.g. when they drop it in the game).
     * @param item The item stored in inventory to be removed.
     */
    public void removeItem(Items item)
    {
        inventory.remove(item);
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
     * Increases the player's energy level by a given amount.
     * @param amount The amount to increase the player's energy level by.
     */
    public void increaseEnergy(int amount)
    {
        this.energy += amount;
    }

    /**
     * Decreases the player's energy level by a given amount.
     * @param amount The amount to decrease the player's energy level by.
     */
    public void decreaseEnergy(int amount)
    {
        this.energy -= amount;
    }

    /**
     * Increases the player's score by a given amount.
     * @param score The amount to increase the player's score by.
     */
    public void increaseScore(int score)
    {
        this.score += score;
    }

    /**
     * Decreases the player's score by a given amount.
     * @param score The amount to decrease the player's score by.
     */
    public void decreaseScore(int score)
    {
        this.score -= score;
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
    public void take(Items item)
    {
        System.out.println();
        inventory.add(item);
    }

    /**
     * Drops an item stored in the player's inventory.
     * @param item The item to be dropped by the player.
     */
    public void drop(Items item)
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

    /**
     * Prints the status of the player including their name, current score, energy level
     * as well as instructions on their score and energy level changes.
     */
    public void printStatus()
    {
        System.out.println("\nCurrent Status");
        System.out.println("Name: " + name);
        System.out.println("Score: " + score);
        System.out.println("Energy: " + energy + "\n");
    }
}
