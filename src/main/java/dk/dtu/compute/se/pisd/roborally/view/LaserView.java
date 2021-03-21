package dk.dtu.compute.se.pisd.roborally.view;

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

public class LaserView
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
    private Wall[] spacesWithWalls;
    private boolean shuffled = false;

    public LaserView(int numberOfLasers, Board board)
    {
        amountLasers = numberOfLasers;
        this.board = board;
        lasers = new Laser[numberOfLasers];
    }

    public Laser[] getLasers() {
        return lasers;
    }

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

    public void spawnLasers()
    {
        if(!shuffled)
        {
            Collections.shuffle(Arrays.asList(spacesWithWalls));
            shuffled = true;
        }
        for(int i = 0; i < amountLasers; i++)
        {
            lasers[i] = new Laser(i+1,board.getSpace(spacesWithWalls[i].getX(),spacesWithWalls[i].getY()),
                    spacesWithWalls[i].getHeading().next().next(),board);
            lasers[i].setEndSpace();
        }
    }

    public void setSpacesWithWalls(Wall[] spacesWithWalls) {
        this.spacesWithWalls = spacesWithWalls;
    }
}
