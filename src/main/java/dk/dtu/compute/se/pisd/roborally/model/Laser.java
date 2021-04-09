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
     * The way a laser points.
     */
    private Heading heading;

    private int y;

    private int x;



    /**
     * <p>Constructor for Laser.</p>
     *
     * @param id a int.
     */

    public Laser(int id, int x, int y, Heading heading)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    /**
     * <p>Getter for the Id <code>getId</code>.</p>
     *
     * @return the id of the laser.
     */
    public int getId() {
        return id;
    }

    /**
     * <p>Getter for the way the laser is shooting <code>getHeading</code>.</p>
     * @return the heading of a laser.
     */
    public Heading getHeading() {
        return heading;
    }

    public int getOrdinal()
    {
        if(heading == SOUTH)
        {
            return 0;
        } else if(heading == WEST)
        {
            return 1;
        } else if(heading == NORTH)
        {
            return 2;
        } else
            {
                return 3;
            }
    }
}