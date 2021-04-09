package dk.dtu.compute.se.pisd.roborally.model;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 * @version $Id: $Id
 */
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Lasers
{
    /**
     * A array of the lasers on the board.
     */
    private Laser[] lasers;


    /**
     * <p>Constructor For LaserView</p>
     * @param numberOfLasers a int
     */
    public Lasers(int numberOfLasers)
    {
        lasers = new Laser[numberOfLasers];
    }

    /**
     * <p>getter for a array of the lasers<code>getLasers</code>.</p>
     * @return the lasers on the board
     */
    public Laser[] getLasers() {
        return lasers;
    }

    }

