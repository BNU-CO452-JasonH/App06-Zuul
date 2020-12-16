/**
 * The Map class stores an interconnected set of rooms used for the Zuul game.
 *
 * @author Jason Huggins
 * @version 16/12/2020
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
        Room outside, theater, pub, lab, office, library, field, shop;

        // create the rooms
        outside = new Room(1, "outside", Items.BACKPACK);
        theater = new Room(2, "theater", Items.COIN);
        pub = new Room(3, "pub", Items.WATCH);
        lab = new Room(4, "lab", Items.NOTEPAD);
        office = new Room(5, "office", Items.WATER);
        library = new Room(6, "library", Items.NONE);
        field = new Room(7, "field", Items.NONE);
        shop = new Room(8, "shop", Items.KEY);

        // initialise room descriptions
        outside.setDescription("outside the main entrance of the university");
        theater.setDescription("in a lecture theater");
        pub.setDescription("in the campus pub");
        lab.setDescription("in a computing lab");
        office.setDescription("in the computing admin office");
        library.setDescription("in the library");
        field.setDescription("on the university fields");
        shop.setDescription("in the campus shop");

        // initialise room exits
        outside.setExit("north", field);
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        field.setExit("south", outside);
        field.setExit("east", shop);

        theater.setExit("west", outside);

        pub.setExit("east", outside);
        pub.setExit("west", shop);

        shop.setExit("west", field);
        shop.setExit("east", pub);

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
