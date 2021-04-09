package dk.dtu.compute.se.pisd.roborally.model;

import java.util.ArrayList;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 * @version $Id: $Id
 */
public class Laser
{
    /**
     * The id of a specific laser
     */
    private int id;

    /**
     * The way a laser points.
     */
    private Heading heading;

    private int y;

    private int x;

    private Space startSpace;

    private Space endSpace;

    private ArrayList<Space> occupiedSpaces;

    private Board board;


    /**
     * <p>Constructor for Laser.</p>
     *
     * @param id a int.
     */

    public Laser(int id, int x, int y, Heading heading)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.startSpace = startSpace;
        this.heading = heading;
        this.board = board;
    }

    private void occupiedSpaces()
    {
        ArrayList<Space> newOccupiedSpaces = new ArrayList<>();

        newOccupiedSpaces.add(board.getSpace(startSpace.x,startSpace.y));

        if(heading == NORTH)
        {
            for (int y = startSpace.y-1; y > 0; y--)
            {
                if(board.getSpace(startSpace.x, y).getWalls().get(0) != null && board.getSpace(startSpace.x, y).getWalls().get(0).getHeading() == NORTH) {
                    newOccupiedSpaces.add(board.getSpace(startSpace.x, y));
                    board.getSpace(startSpace.x, y).setLaser(this);
                    break;
                } else if(board.getSpace(startSpace.x, y).getWalls().get(0) != null && board.getSpace(startSpace.x, y).getWalls().get(0).getHeading() == SOUTH)
                {
                    break;
                } else {
                    newOccupiedSpaces.add(board.getSpace(startSpace.x, y));
                    board.getSpace(startSpace.x, y).setLaser(this);
                }
            }
        } else if(heading == EAST)
        {
            for (int x = startSpace.x+1; x < board.width; x++)
            {
                if(board.getSpace(x, startSpace.y).getWalls().get(0) != null && board.getSpace(x, startSpace.y).getWalls().get(0).getHeading() == EAST) {
                    newOccupiedSpaces.add(board.getSpace(x, startSpace.y));
                    board.getSpace(x, startSpace.y).setLaser(this);
                    break;
                } else if(board.getSpace(x, startSpace.y).getWalls().get(0) != null && board.getSpace(x, startSpace.y).getWalls().get(0).getHeading() == WEST)
                {
                    break;
                } else {
                    newOccupiedSpaces.add(board.getSpace(x, startSpace.y));
                    board.getSpace(x, startSpace.y).setLaser(this);
                }
            }
        } else if(heading == SOUTH)
        {
            for (int y = startSpace.y+1; y < board.height; y++)
            {
                if(board.getSpace(startSpace.x, y).getWalls().get(0) != null && board.getSpace(startSpace.x, y).getWalls().get(0).getHeading() == SOUTH) {
                    newOccupiedSpaces.add(board.getSpace(startSpace.x, y));
                    board.getSpace(startSpace.x, y).setLaser(this);
                    break;
                } else if(board.getSpace(startSpace.x, y).getWalls().get(0) != null && board.getSpace(startSpace.x, y).getWalls().get(0).getHeading() ==  NORTH)
                {
                    break;
                } else {
                    newOccupiedSpaces.add(board.getSpace(startSpace.x, y));
                    board.getSpace(startSpace.x, y).setLaser(this);
                }
            }
        } else if(heading == WEST)
        {
            for (int x = startSpace.x-1; x > 0; x--)
            {
                if(board.getSpace(x, startSpace.y).getWalls().get(0) != null && board.getSpace(x, startSpace.y).getWalls().get(0).getHeading() == WEST) {
                    newOccupiedSpaces.add(board.getSpace(x, startSpace.y));
                    board.getSpace(x, startSpace.y).setLaser(this);
                    break;
                } else if(board.getSpace(x, startSpace.y).getWalls().get(0) != null && board.getSpace(x, startSpace.y).getWalls().get(0).getHeading() == EAST)
                {
                    break;
                } else {
                    newOccupiedSpaces.add(board.getSpace(x, startSpace.y));
                    board.getSpace(x, startSpace.y).setLaser(this);
                }
            }
        }

        endSpace = newOccupiedSpaces.get(newOccupiedSpaces.size()-1);
        occupiedSpaces = newOccupiedSpaces;
    }

    public boolean checkIfOccupied(Space space)
    {
        boolean occupied = false;
        for (int i = 0; i < occupiedSpaces.size(); i++) {

            if(occupiedSpaces.get(i) == space)
            {
                occupied = true;
                break;
            }
        }
        return occupied;
    }

    /**
     * <p>Getter for the Id <code>getId</code>.</p>
     *
     * @return the id of the laser.
     */
    public int getId() {
        return id;
    }

    public void setStartSpace(Space space) {
        this.startSpace = space;
    }

    public Space getStartSpace() {
        return startSpace;
    }

    public void setEndSpace() {
        occupiedSpaces();
    }

    public Space getEndSpace() {
        return endSpace;
    }

    public ArrayList<Space> getOccupiedSpaces() {
        return occupiedSpaces;
    }

    /**
     * <p>Getter for the way the laser is shooting <code>getHeading</code>.</p>
     * @return the heading of a laser.
     */
    public Heading getHeading() {
        return heading;
    }

    public int getOrdinal()
    {
        if(heading == SOUTH)
        {
            return 0;
        } else if(heading == WEST)
        {
            return 1;
        } else if(heading == NORTH)
        {
            return 2;
        } else
            {
                return 3;
            }
    }
}