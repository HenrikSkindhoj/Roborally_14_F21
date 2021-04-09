package dk.dtu.compute.se.pisd.roborally.model;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;

/**
 * ...
 *
 * @author Kasper Falch Skov, s205429@student.dtu.dk
 * @version $Id: $Id
 */
public class Wall {

    int id;
    /**
     * The location of a wall on the x-axis
     */
    int x;
    /**
     * The location of a wall on the y-axis
     */
    int y;
    Heading heading;

    /**
     * <p>Constructor for Wall.</p>
     * @param x a int.
     * @param y a int.
     * @param heading a {@link dk.dtu.compute.se.pisd.roborally.model.Heading} object.
     */
    public Wall(int id, int x, int y, Heading heading){
        this.id = id;
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    /**
     * <p>Getter for the x value<code>getX</code>.</p>
     * @return the location of the wall on the x-axis.
     */
    public int getX() {
        return x;
    }

    /**
     * <p>Getter for the y value<code>getY</code>.</p>
     * @return the location of the wall on the y-axis.
     */
    public int getY() {
        return y;
    }

    /**
     * <p>Getter for which side of a space the wall is on<code>getHeading</code>.</p>
     * @return the way the wall is pointing.
     */
    public Heading getHeading() {
        return heading;
    }

    public int getId() {
        return id;
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
