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

    public Laser(int id, int x, int y, Heading heading, int maxX, int maxY)
    /**
     * <p>Constructor for Laser.</p>
     *
     * @param x1 a int.
     * @param x2 a int.
     * @param y1 a int.
     * @param y2 a int.
     * @param id a int.
     */
    public Laser(int x1, int y1, int x2, int y2, int id)
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
    /**
     * <p>Getter for the first x value.<code>getX1</code>.</p>
     *
     * @return return the start-point of the lasers x-axis.
     */
    public int getX1() {
        return x1;
    }

    /**
     * <p>Getter for the first y value.<code>getY1</code>.</p>
     *
     * @return return the start-point of the lasers y-axis.
     */
    public int getY1() {
        return y1;
    }

    public int getY() {
        return y;
    /**
     * <p>Getter for the second x value.<code>getX2</code>.</p>
     *
     * @return return the end-point of the lasers x-axis.
     */
    public int getX2() {
        return x2;
    }

    public Heading getHeading() {
        return heading;
    /**
     * <p>Getter for the second y value.<code>getY2</code>.</p>
     *
     * @return return the end-point of the lasers y-axis.
     */
    public int getY2() {
        return y2;
    }
}