package dk.dtu.compute.se.pisd.roborally.model;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 * @version $Id: $Id
 */
public class Laser
{
    private int id;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

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
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
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

    /**
     * <p>Getter for the second x value.<code>getX2</code>.</p>
     *
     * @return return the end-point of the lasers x-axis.
     */
    public int getX2() {
        return x2;
    }

    /**
     * <p>Getter for the second y value.<code>getY2</code>.</p>
     *
     * @return return the end-point of the lasers y-axis.
     */
    public int getY2() {
        return y2;
    }
}