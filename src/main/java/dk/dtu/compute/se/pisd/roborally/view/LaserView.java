package dk.dtu.compute.se.pisd.roborally.view;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 * @version $Id: $Id
 */
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Laser;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class LaserView
{
    Laser[] lasers;

    public LaserView(int numberOfLasers, int boardSizeX, int boardSizeY)
    {
        Integer[] arrX = new Integer[boardSizeX-1];
        Integer[] arrY = new Integer[boardSizeY-1];

        for(int by = 0; by < arrY.length; by++) arrY[by] = by;
        for(int bx = 0; bx < arrX.length; bx++) arrX[bx] = bx;

        Collections.shuffle(Arrays.asList(arrX));
        Collections.shuffle(Arrays.asList(arrY));

        lasers = new Laser[numberOfLasers];

        for(int i = 0; i < numberOfLasers; i++)
        {
            lasers[i] = new Laser(i, arrX[i], arrY[i], random(), boardSizeX, boardSizeY);
        }
    }

    public Laser[] getLasers() {
        return lasers;
    }

    public Heading random()
    {
        Heading heading;
        Random random = new Random();
        int randomInt = random.nextInt(4);
        if(randomInt == 0) heading = Heading.NORTH;
        else if(randomInt == 1) heading = Heading.EAST;
        else if(randomInt == 2) heading = Heading.SOUTH;
        else heading = Heading.WEST;
        return heading;
    /**
     * <p>Constructor for LaserView.</p>
     *
     * @param boardSizeX a int.
     * @param boardSizeY a int.
     */
    public LaserView(int boardSizeX, int boardSizeY)
    {

    }


}
