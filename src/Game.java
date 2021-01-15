import java.util.ArrayList;
import java.util.HashMap;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * 
 * Originally modified and extended by Derek and Andrei
 * Modified by Jason Huggins (dated: 15/01/2021)
 */

public class Game 
{
    private Map map;
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private HashMap<Items, Integer> consumables;
    private HashMap<Items, Integer> valuables;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        map = new Map();
        currentRoom = map.getStartRoom();
        player = new Player("Jason");
        consumables = new HashMap<>();
        valuables = new HashMap<>();

        // Initialising items with values (taking consumables will increase energy, valuables will increase score).
        consumables.put(Items.WATER, 25);
        consumables.put(Items.COLA, 25);
        consumables.put(Items.ENERGY_BAR, 50);
        consumables.put(Items.BISCUIT, 20);

        valuables.put(Items.KEY, 50);
        valuables.put(Items.WATCH, 35);
        valuables.put(Items.NECKLACE, 45);
        valuables.put(Items.ID_CARD, 25);
        valuables.put(Items.PHONE, 45);

        play();
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        
        while (!finished)
        {
            Command command = parser.getCommand();
            finished = processCommand(command);

            // If the player's score reaches or exceeds the winning score threshold, they have won.
            if (player.getScore() >= Player.WIN_SCORE)
            {
                System.out.println("Congratulations! You have successfully beaten the game.");
                System.out.println("Your final player status for this session will be shown below.");
                player.printStatus();
                finished = true;
            }
            // If the player's energy level drops to 0, they have lost.
            else if (player.getEnergy() <= 0)
            {
                System.out.println("You have run out of energy and can't proceed any further.");
                System.out.println("Because of this, you have unfortunately lost and the game has ended.");
                System.out.println("Better luck next time!");
                finished = true;
            }
        }
        
        System.out.println("The game has now been exited. Thank you for playing! Goodbye!");
    }

    // TODO: Change the introductory message so it actually introduces the game, rather saying it's just boring.
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        player.printStatus();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord)
        {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;

            case LOOK:
                printItems();
                break;

            case TAKE:
                takeItem(command);
                break;

            case DROP:
                dropItem(command);
                break;

            case ITEMS:
                System.out.println("Items in your inventory: " + player.getInventory());
                break;

            case STATUS:
                player.printStatus();
                break;
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * A cryptic message is printed, as well as a list of commands and instructions on how to play
     * the game.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println("\nYour energy level will decrease by 5 each time you go into a room.");
        System.out.println("If your energy level reaches 0, you can no longer proceed and the game will end.");
        System.out.println("However, you can increase energy after picking up snacks/drinks you find (e.g. water).");
        System.out.println("\nPicking up certain items will increase your score.");
        System.out.println("Should you reach the score of 200 points, you will complete the game.");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        // Used to check if the directions entered by the user are valid.
        ArrayList<String> validDirections = new ArrayList<>();
        validDirections.add("north");
        validDirections.add("south");
        validDirections.add("east");
        validDirections.add("west");

        if (!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        else if (!validDirections.contains(command.getSecondWord()))
        {
            // If the direction entered is invalid, an error message will be displayed.
            System.out.println("Invalid direction entered. Please try again.");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        // Flag if the next room is locked (the classroom).
        boolean isLocked = nextRoom.getName().equals("lockedClassroom");
        Items keyItem = player.findItem("KEY");

        if (nextRoom == null)
        {
            System.out.println("There is no door!");
        }
        // If the next room is locked and the player doesn't have a key in their inventory, they can't proceed.
        else if ((isLocked) && (keyItem == Items.NONE))
        {
            System.out.println("The door to the classroom is locked. You need a key in your inventory to open it.");
        }
        else
        {
            // If the next room (i.e. the classroom) is locked but the player has a key, they can unlock it.
            if (isLocked)
            {
                System.out.println("You use the key to unlock the classroom door.");
            }

            currentRoom = nextRoom;
            player.decreaseEnergy(10);
            System.out.println("Energy level decreased by 10. Energy remaining: " + player.getEnergy());
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * Prints a list of items inside the room the player is currently in.
     */
    private void printItems()
    {
        currentRoom.printItems();
    }

    /**
     * Pick up an item in the current room and store in inventory.
     */
    private void takeItem(Command command)
    {
        // if there is no second word, don't know what item to take.
        if (!command.hasSecondWord())
        {
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord().toUpperCase();
        Items roomItem = currentRoom.getItem();

        // If the item in the current room matches the name given by the user, pick it up.
        if (roomItem.toString().equals(itemName))
        {
            System.out.println("Item picked up: " + roomItem);

            // If the item picked up is a consumable, increase the player's energy level.
            if (consumables.containsKey(roomItem))
            {
                player.increaseEnergy(consumables.get(roomItem));
                System.out.println("Energy increased by " + consumables.get(roomItem)
                        + ". Energy remaining: " + player.getEnergy());
            }
            // Else if the item picked up is a valuable, increase the player's score.
            else if (valuables.containsKey(roomItem))
            {
                player.increaseScore(valuables.get(roomItem));
                System.out.println("Score increased by " + valuables.get(roomItem)
                        + ". Current score: " + player.getScore());
            }

            player.take(roomItem);
            currentRoom.removeItem();
            // black box test - pick up item twice
        }
        else
        {
            System.out.println("Invalid choice. This item does not exist in this room.");
        }
    }

    /**
     * Drop an item stored in the player's inventory.
     */
    private void dropItem(Command command)
    {
        // if there is no second word, don't know what item to drop.
        if (!command.hasSecondWord())
        {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord().toUpperCase();
        Items item = player.findItem(itemName);

        if (item != Items.NONE)
        {
            System.out.println("Item dropped: " + item);
            player.removeItem(item);
            currentRoom.setItem(item);
        }
        else
        {
            System.out.println("Don't recognise that item.");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if (command.hasSecondWord())
        {
            System.out.println("Quit what?");
            return false;
        }
        else
        {
            return true;  // signal that we want to quit
        }
    }
}
