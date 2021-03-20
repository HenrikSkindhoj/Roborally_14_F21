package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Laser;

import java.util.ArrayList;
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
            lasers[i] = new Laser(arrX[i], arrY[i], random());
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
    }
}
