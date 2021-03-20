package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Checkpoint;

import java.util.Arrays;
import java.util.Collections;

public class CheckpointsView {

    private Checkpoint[] checkpoints;

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

    public Checkpoint[] getCheckpoints() {
        return checkpoints;
    }
}
