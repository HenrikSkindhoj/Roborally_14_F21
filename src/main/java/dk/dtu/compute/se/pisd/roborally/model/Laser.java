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
    /**
     * The id of a specific laser
     */
    private int id;
    /**
     * The location of the laser on the x-axis.
     */
    private int x;
    /**
     * The location of the laser on the y-axis.
     */
    private int y;
    /**
     * The max length the laser can shoot out of the x-axis.
     */
    private int maxX;
    /**
     * The max length the laser can shoot out of the y-axis.
     */
    private int maxY;
    /**
     * The way a laser points.
     */
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