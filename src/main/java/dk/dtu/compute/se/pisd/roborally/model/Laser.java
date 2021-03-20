package dk.dtu.compute.se.pisd.roborally.model;

import java.util.ArrayList;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 * @version $Id: $Id
 */
public class Laser
{
    private int id;
    private int x;
    private int y;
    private int maxX;
    private int maxY;
    private Heading heading;

    /**
     * <p>Constructor for Laser.</p>
     *
     * @param x a int.
     * @param y a int.
     * @param id a int.
     */

    public Laser(int id, int x, int y, Heading heading, int maxX, int maxY)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.maxX = maxX;
        this.maxY = maxY;
        this.heading = heading;

    }

    public boolean hit(int givenX, int givenY)
    {
        boolean isHit = false;

        if(heading == NORTH)
        {
            if(givenX == x && givenY <= y)
            {
                isHit = true;
            }
        } else if(heading == EAST)
        {
            if(givenX >= x && givenY == y)
            {
                isHit = true;
            }
        } else if(heading == SOUTH)
        {
            if(givenX == x && givenY >= y)
            {
                isHit = true;
            }
        } else if(heading == WEST)
        {
            if(givenX <= x && givenY == y)
            {
                isHit = true;
            }
        }
        return isHit;
    }

    /**
     * <p>Getter for the Id <code>getId</code>.</p>
     *
     * @return the id of the laser.
     */
    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Heading getHeading() {
        return heading;
    }
}