package dk.dtu.compute.se.pisd.roborally.view;

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

    private Checkpoint[] checkpoints;

    /**
     * <p>Constructor for CheckpointsView.</p>
     *
     * @param numberOfCheckpoints a int.
     * @param boardSizeX a int.
     * @param boardSizeY a int.
     */
    public CheckpointsView(int numberOfCheckpoints, int boardSizeX, int boardSizeY)
    {
        Integer[] arrX = new Integer[boardSizeX-1];
        Integer[] arrY = new Integer[boardSizeY-1];

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
