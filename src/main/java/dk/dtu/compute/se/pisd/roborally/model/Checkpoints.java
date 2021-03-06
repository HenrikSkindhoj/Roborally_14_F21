package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Checkpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 * @version $Id: $Id
 */
public class Checkpoints {

    /**
     * A array of all the checkpoints on a board.
     */
    private ArrayList<Checkpoint> checkpoints;

    private Board board;

    /**
     * <p>Constructor for Checkpoints.</p>
     * The constructor of all the checkpoints on the board,
     * it does this by creating 2 arrays, of all the x and y values of the board.
     * It then creates one checkpoint at a time, using one value of each array.
     * The values which are selected from the arrays, cannot be used twice,
     * this way the method makes sure that out of the 4 checkpoints that at made, none of them are the same.
     * @param numberOfCheckpoints a int.
     * @param board object to receive board length and height
     */
    public Checkpoints(int numberOfCheckpoints, Board board)
    {
        this.board = board;

        Integer[] arrX = new Integer[board.width-1];
        Integer[] arrY = new Integer[board.height-1];

        for(int by = 0; by < arrY.length; by++) arrY[by] = by;
        for(int bx = 0; bx < arrX.length; bx++) arrX[bx] = bx;

        Collections.shuffle(Arrays.asList(arrX));
        Collections.shuffle(Arrays.asList(arrY));

        checkpoints = new ArrayList<>();

        int counter = 0;
        for(int i = 0; i < numberOfCheckpoints; i++)
        {
            Space space = board.getSpace(arrX[counter],arrY[counter]);
            if(space.getCheckpoint() == null && space.getGear() == null
            && space.getConveyorBelt() == null && (space.getLaser() == null || space.getLaser().getStartSpace() != space))
                checkpoints.add(new Checkpoint(arrX[counter], arrY[counter], i+1));
            else i--;
            counter++;
        }
    }

    /**
     * <p>Constructor for Checkpoints.</p>
     * @param board a {@link dk.dtu.compute.se.pisd.roborally.model.Board} object.
     */
    public Checkpoints(Board board)
    {
        this.board = board;
        checkpoints = new ArrayList<>();
    }

    /**
     * <p>add.</p>
     * @param checkpoint a {@link dk.dtu.compute.se.pisd.roborally.model.Checkpoint} object.
     */
    public void add(Checkpoint checkpoint)
    {
        checkpoints.add(checkpoint);
    }

    /**
     * <p>spawnCheckpoints.</p>
     * Creates checkpoints on the board.
     */
    public void spawnCheckpoints()
    {
        for (Checkpoint checkpoint : checkpoints)
        {
            board.getSpace(checkpoint.getX(),checkpoint.getY()).setCheckpoint(checkpoint);
        }
    }

    /**
     * <p>Getter for the checkpoints.<code>getCheckpoints</code>.</p>
     * @return all of the checkpoints in the Checkpoints array.
     */
    public ArrayList<Checkpoint> getCheckpoints() {
        return checkpoints;
    }
}
