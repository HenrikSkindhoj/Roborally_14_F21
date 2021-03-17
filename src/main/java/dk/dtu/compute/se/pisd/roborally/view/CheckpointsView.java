package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Checkpoint;

import java.util.Arrays;
import java.util.Collections;

public class CheckpointsView {

    public Checkpoint[] checkpoints;

    public CheckpointsView(int numberOfCheckpoints, int boardSizeX, int boardSizeY)
    {
        Integer[] arrX = new Integer[boardSizeX];
        Integer[] arrY = new Integer[boardSizeY];

        Collections.shuffle(Arrays.asList(arrX));
        Collections.shuffle(Arrays.asList(arrY));

        for(int i = 0; i < numberOfCheckpoints; i++)
        {
            checkpoints[i] = new Checkpoint(arrX[i], arrY[i], i+1);
        }
    }
}
