package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.model.Wall;

import java.util.ArrayList;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;

/**
 * ...
 *
 * @author Kasper Falch Skov, s205429@student.dtu.dk
 * @version $Id: $Id
 */
public class Walls {

    /**
     * Array of walls
     */
    private ArrayList<Wall> walls;
    private Board board;

    /**
     * <p>Constructor for CheckpointsView.</p>
     * @param numberOfWalls a int.
     * @param boardSizeX a int.
     * @param boardSizeY a int.
     */
    public Walls(int numberOfWalls, int boardSizeX, int boardSizeY, Board board){
        this.board = board;
        walls = new ArrayList<>();
        walls.add(new Wall(1,0,0, WEST));
        walls.add(new Wall(2,0,1, WEST));
        walls.add(new Wall(3,0,2, NORTH));
        walls.add(new Wall(4,1,2, NORTH));
        walls.add(new Wall(5,2,2, NORTH));
        walls.add(new Wall(6,3,2, NORTH));
        walls.add(new Wall(7,4,2, NORTH));
        walls.add(new Wall(8,5,2, NORTH));
        walls.add(new Wall(9,2,4, NORTH));
        walls.add(new Wall(10,3,4, WEST));
        walls.add(new Wall(11,3,5, NORTH));
        walls.add(new Wall(12,4,5, WEST));
        walls.add(new Wall(13,4,6, NORTH));
        walls.add(new Wall(14,5,6, WEST));
        walls.add(new Wall(15,5,7, NORTH));
        walls.add(new Wall(16,6,7, WEST));
    }

    public Walls(Board board)
    {
        walls = new ArrayList<>();
        this.board = board;
    }

    /**
     * <p>Getter for the walls.<code>getWalls</code>.</p>
     * @return all of the walls in the Wall array.
     */
    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void add(Wall wall)
    {
        for (int i = 0; i < walls.size(); i++)
        {
            if(walls.get(i) == null)
            {
                walls.add(wall);
            }
        }
    }

    public void spawnWalls()
    {
        for (Wall wall : walls)
        {
            board.getSpace(wall.getX(),wall.getY()).setWall(wall);
        }
    }
}
