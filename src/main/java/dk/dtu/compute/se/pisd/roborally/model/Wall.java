package dk.dtu.compute.se.pisd.roborally.model;

/**
 * ...
 *
 * @author Kasper Falch Skov, s205429@student.dtu.dk
 * @version $Id: $Id
 */
public class Wall {
    int x;
    int y;
    Heading heading;

    /**
     * <p>Constructor for Wall.</p>
     *
     * @param x a int.
     * @param y a int.
     * @param heading a {@link dk.dtu.compute.se.pisd.roborally.model.Heading} object.
     */
    public Wall(int x, int y, Heading heading){
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    /**
     * <p>Getter for the x value.<code>getX</code>.</p>
     *
     * @return the location of the wall on the x-axis.
     */
    public int getX() {
        return x;
    }

    /**
     * <p>Getter for the y value.<code>getY</code>.</p>
     *
     * @return the location of the wall on the y-axis.
     */
    public int getY() {
        return y;
    }

    /**
     * <p>Getter for heading of the wall.<code>getHeading</code>.</p>
     *
     * @return the way the wall is pointing..
     */
    public Heading getHeading() {
        return heading;
    }
}
