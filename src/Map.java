/**
 * The Map class stores an interconnected set of rooms used for the Zuul game.
 *
 * @author Jason Huggins
 * @version 04/12/2020
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
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

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
