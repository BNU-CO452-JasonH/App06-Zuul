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
 * Modified by Jason Huggins (dated: 18/12/2020)
 */

public class Game 
{
    private Map map;
    private Parser parser;
    private Room currentRoom;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        map = new Map();
        currentRoom = map.getStartRoom();
        player = new Player("Jason");
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
        }
        
        System.out.println("Thank you for playing.  Good bye.");
    }

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
        if(!command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null)
        {
            System.out.println("There is no door!");
        }
        else
        {
            currentRoom = nextRoom;
            player.decreaseEnergy(5);
            System.out.println("Energy level decreased by 5. Energy remaining: " + player.getEnergy());
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

    // TODO: Add functionality for specific items to increase score and energy level.
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

        // if the item in the current room matches the name given by the user, pick the item up.
        if (roomItem.toString().equals(itemName))
        {
            System.out.println("Item picked up: " + roomItem);
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
        String itemName = command.getSecondWord().toUpperCase();

        // if there is no second word, don't know what item to drop.
        if (!command.hasSecondWord())
        {
            System.out.println("Drop what?");
        }
        else
        {
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
    }

    // TODO: Add methods when the player wins or loses in the game.

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
