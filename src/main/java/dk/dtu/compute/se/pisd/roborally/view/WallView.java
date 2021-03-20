package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Wall;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;

/**
 * ...
 *
 * @author Kasper Falch Skov, s205429@student.dtu.dk
 * @version $Id: $Id
 */
public class WallView {

    /**
     * Array of walls
     */
    private Wall[] walls;

    /**
     * <p>Constructor for CheckpointsView.</p>
     *
     * @param numberOfWalls a int.
     * @param boardSizeX a int.
     * @param boardSizeY a int.
     */
    public WallView(int numberOfWalls, int boardSizeX, int boardSizeY){
        walls = new Wall[numberOfWalls];
        walls[0] = new Wall(0,0, WEST);
        walls[1] = new Wall(0,1, WEST);
        walls[2] = new Wall(0,2, NORTH);
        walls[3] = new Wall(1,2, NORTH);
        walls[4] = new Wall(2,2, NORTH);
        walls[5] = new Wall(3,2, NORTH);
        walls[6] = new Wall(4,2, NORTH);
        walls[7] = new Wall(5,2, NORTH);
        walls[8] = new Wall(2,4, NORTH);
        walls[9] = new Wall(3,4, WEST);
        walls[10] = new Wall(3,5, NORTH);
        walls[11] = new Wall(4,5, WEST);
        walls[12] = new Wall(4,6, NORTH);
        walls[13] = new Wall(5,6, WEST);
        walls[14] = new Wall(5,7, NORTH);
        walls[15] = new Wall(6,7, WEST);
    }

    /**
     * <p>Getter for the walls.<code>getWalls</code>.</p>
     *
     * @return all of the walls in the Wall array.
     */
    public Wall[] getWalls() {
        return walls;
    }
}
