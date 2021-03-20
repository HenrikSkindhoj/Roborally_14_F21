package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Checkpoint;

import java.util.Arrays;
import java.util.Collections;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 * @version $Id: $Id
 */
public class CheckpointsView {

    /**
     * A array of all the checkpoints on a board.
     */
    private Checkpoint[] checkpoints;
    private Board board;

    /**
     * <p>Constructor for CheckpointsView.</p>
     *
     * @param numberOfCheckpoints a int.
     * @param board object to receive board length and height
     */
    public CheckpointsView(int numberOfCheckpoints, Board board)
    {
        Integer[] arrX = new Integer[board.width-1];
        Integer[] arrY = new Integer[board.height-1];

        for(int by = 0; by < arrY.length; by++) arrY[by] = by;
        for(int bx = 0; bx < arrX.length; bx++) arrX[bx] = bx;

        Collections.shuffle(Arrays.asList(arrX));
        Collections.shuffle(Arrays.asList(arrY));

        checkpoints = new Checkpoint[numberOfCheckpoints];

        for(int i = 0; i < numberOfCheckpoints; i++)
        {
            checkpoints[i] = new Checkpoint(arrX[i], arrY[i], i+1);
        }
    }

    /**
     * <p>Getter for the checkpoints.<code>getCheckpoints</code>.</p>
     *
     * @return all of the checkpoints in the Checkpoints array.
     */
    public Checkpoint[] getCheckpoints() {
        return checkpoints;
    }
}
