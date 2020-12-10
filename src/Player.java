import java.util.HashMap;

/**
 * The Player class stores information about the player of the Zuul game, including their energy level, score and items
 * in possession.
 *
 * @author Jason Huggins
 * @version 10/12/2020
 */
public class Player
{
    private int energy;
    private int score;
    private Room currentRoom;
    private HashMap<String, Integer> items;
    private int maxWeight;

    /**
     * Constructor for a Player object.
     */
    public Player()
    {
        energy = 100;
        score = 0;
        items = new HashMap<>();
        maxWeight = 1500;
    }
}
