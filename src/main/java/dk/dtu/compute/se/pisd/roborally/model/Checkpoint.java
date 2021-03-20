package dk.dtu.compute.se.pisd.roborally.model;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 * @version $Id: $Id
 */
public class Checkpoint {

    /**
     * The id of the checkpoint is a int from 1 to 4, which determines the order the player should land on the checkpoints in.
     */
    private int id;
    /**
     * The int x is the checkpoints location on the x-axis on the board.
     */
    private int x;
    /**
     * the int y is the checkpoints location on the y-axis on the board.
     */
    private int y;

    /**
     * <p>Constructor for Checkpoint.</p>
     *
     * @param x a int.
     * @param y a int.
     * @param id a int.
     */
    public Checkpoint(int x, int y, int id)
    {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * <p>Getter for the Id <code>getId</code>.</p>
     *
     * @return a int from 1 to 4.
     */
    public int getId() {
        return id;
    }

    /**
     * <p>Getter for the X.<code>getX</code>.</p>
     *
     * @return a int x, which is a spot on the x-axis of the board.
     */
    public int getX() {
        return x;
    }

    /**
     * <p>Getter for the Y.<code>getY</code>.</p>
     *
     * @return a int y, which is a spot on the y-axis of the board.
     */
    public int getY() {
        return y;
    }
}
