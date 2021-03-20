package dk.dtu.compute.se.pisd.roborally.view;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 * @version $Id: $Id
 */
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Laser;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class LaserView
{
    private Laser[] lasers;
    private Board board;
    private int amountLasers;

    public LaserView(int numberOfLasers, Board board)
    {
        amountLasers = numberOfLasers;
        this.board = board;
        lasers = new Laser[numberOfLasers];
        spawnLasers();
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

    private void spawnLasers()
    {

    }


}
