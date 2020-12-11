/**
 * The Map class stores an interconnected set of rooms used for the Zuul game.
 *
 * @author Jason Huggins
 * @version 11/12/2020
 */
public class Map
{
    private Room startRoom;

    /**
     * Constructor for a Map object.
     */
    public Map()
    {
        createRooms();
    }

    /**
     * Create all the rooms with descriptions and items, then link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;

        // create the rooms
        outside = new Room(01, "outside", Items.BACKPACK);
        theater = new Room(02, "theater", Items.COIN);
        pub = new Room(03, "pub", Items.WATCH);
        lab = new Room(04, "lab", Items.NOTEPAD);
        office = new Room(05, "office", Items.WATER);

        // initialise room descriptions
        outside.setDescription("outside the main entrance of the university");
        theater.setDescription("in a lecture theater");
        pub.setDescription("in the campus pub");
        lab.setDescription("in a computing lab");
        office.setDescription("in the computing admin office");

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        startRoom = outside;  // start game outside
    }

    /**
     * @return startRoom The starting room for the game.
     */
    public Room getStartRoom()
    {
        return startRoom;
    }
}
