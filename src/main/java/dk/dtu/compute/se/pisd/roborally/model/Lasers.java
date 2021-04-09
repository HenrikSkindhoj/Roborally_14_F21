package dk.dtu.compute.se.pisd.roborally.model;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 * @version $Id: $Id
 */
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Lasers
{
    /**
     * A array of the lasers on the board.
     */
    private Laser[] lasers;
    private Board board;
    /**
     * A int, for the amount of lasers on the board.
     */
    private int amountLasers;
    /**
     * A array of all the spaces who also have walls.
     */
    private ArrayList<Wall> spacesWithWalls;
    /**
     * A boolean which makes sure, that the placement of the lasers is random.
     */
    private boolean shuffled = false;

    /**
     * <p>Constructor For LaserView</p>
     * @param numberOfLasers a int
     * @param board a {@link dk.dtu.compute.se.pisd.roborally.model.Board} object.
     */
    public Lasers(int numberOfLasers, Board board)
    {
        amountLasers = numberOfLasers;
        this.board = board;
        lasers = new Laser[numberOfLasers];
    }

    public Lasers(Board board)
    {
        lasers = new Laser[2];
        this.board = board;
    }

    /**
     * <p>getter for a array of the lasers<code>getLasers</code>.</p>
     * @return the lasers on the board
     */
    public Laser[] getLasers() {
        return lasers;
    }

    /**
     * <p>random</p>
     * Generates a random int with the max value of 4, and from the number generates, the laser gets assigned a heading.
     * @return heading a {@link dk.dtu.compute.se.pisd.roborally.model.Heading} object.
     */
    public Heading random() {
        Heading heading;
        Random random = new Random();
        int randomInt = random.nextInt(4);
        if (randomInt == 0) heading = Heading.NORTH;
        else if (randomInt == 1) heading = Heading.EAST;
        else if (randomInt == 2) heading = Heading.SOUTH;
        else heading = Heading.WEST;
        return heading;
    }

    /**
     * <p>spawnLasers</p>
     * Checks if the lasers are shuffled, if not, it shuffles the spacesWithWalls, and sets the shuffled boolean to true.
     * It then creates lasers, on walls.
     */
    public void spawnLasers()
    {
        if(!shuffled)
        {
            Collections.shuffle(spacesWithWalls);
            shuffled = true;
        }
        for(int i = 0; i < amountLasers; i++)
        {
            lasers[i] = new Laser(i+1,board.getSpace(spacesWithWalls.get(i).getX(),spacesWithWalls.get(i).getY()),
                    spacesWithWalls.get(i).getHeading().next().next());
            lasers[i].setEndSpace();
        }
    }

    /**
     * <p>Setter for the spaces who also have walls<code>setSpacesWithWalls</code>.</p>
     * @param spacesWithWalls
     */
    public void setSpacesWithWalls(ArrayList<Wall> spacesWithWalls) {
        this.spacesWithWalls = spacesWithWalls;
    }

    public void add(Laser laser)
    {
        for (int i = 0; i < lasers.length; i++)
        {
            if(lasers[i] == null)
            {
                lasers[i] = laser;
            }
        }
    }
}
