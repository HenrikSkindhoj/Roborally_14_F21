package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;

import java.util.ArrayList;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 * @version $Id: $Id
 */
public class Laser extends FieldAction
{
    /**
     * The id of a specific laser
     */
    private int id;

    /**
     * The way a laser points.
     */
    private Heading heading;

    public int x;

    public int y;

    private Space startSpace;

    private Space endSpace;

    private ArrayList<Space> occupiedSpaces;


    /**
     * <p>Constructor for Laser.</p>
     *
     * @param id a int.
     */

    public Laser(int id, int x, int y, Heading heading)
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.heading = heading;
    }

    private void occupiedSpaces()
    {
        ArrayList<Space> newOccupiedSpaces = new ArrayList<>();

        if(occupiedSpaces != null) {
            removeLasersFromSpaces();
        }
        newOccupiedSpaces.add(startSpace);
        if(heading == NORTH)
        {
            for (int y = startSpace.y-1; y > 0; y--)
            {
                if((!startSpace.board.getSpace(startSpace.x, y).getWalls().isEmpty() && startSpace.board.getSpace(startSpace.x, y).getWalls().get(0).getHeading() == NORTH)
                        || playerHit(startSpace.board.getSpace(startSpace.x, y))) {
                    newOccupiedSpaces.add(startSpace.board.getSpace(startSpace.x, y));
                    startSpace.board.getSpace(startSpace.x, y).setLaser(this);
                    break;
                } else if(!startSpace.board.getSpace(startSpace.x, y).getWalls().isEmpty() && startSpace.board.getSpace(startSpace.x, y).getWalls().get(0).getHeading() == NORTH.next().next())
                {
                    break;
                } else {
                    newOccupiedSpaces.add(startSpace.board.getSpace(startSpace.x, y));
                    startSpace.board.getSpace(startSpace.x, y).setLaser(this);
                }
            }
        } else if(heading == EAST)
        {
            for (int x = startSpace.x+1; x < startSpace.board.width; x++)
            {
                if((!startSpace.board.getSpace(x, startSpace.y).getWalls().isEmpty() && startSpace.board.getSpace(x, startSpace.y).getWalls().get(0).getHeading() == EAST)
                        || playerHit(startSpace.board.getSpace(x, startSpace.y))) {
                    newOccupiedSpaces.add(startSpace.board.getSpace(x, startSpace.y));
                    startSpace.board.getSpace(x, startSpace.y).setLaser(this);
                    break;
                } else if(!startSpace.board.getSpace(x, startSpace.y).getWalls().isEmpty() && startSpace.board.getSpace(x, startSpace.y).getWalls().get(0).getHeading() == EAST.next().next())
                {
                    break;
                } else {
                    newOccupiedSpaces.add(startSpace.board.getSpace(x, startSpace.y));
                    startSpace.board.getSpace(x, startSpace.y).setLaser(this);
                }
            }
        } else if(heading == SOUTH)
        {
            for (int y = startSpace.y+1; y < startSpace.board.height; y++)
            {
                if((!startSpace.board.getSpace(startSpace.x, y).getWalls().isEmpty() && startSpace.board.getSpace(startSpace.x, y).getWalls().get(0).getHeading() == SOUTH)
                        || playerHit(startSpace.board.getSpace(startSpace.x, y))) {
                    newOccupiedSpaces.add(startSpace.board.getSpace(startSpace.x, y));
                    startSpace.board.getSpace(startSpace.x, y).setLaser(this);
                    break;
                } else if(!startSpace.board.getSpace(startSpace.x, y).getWalls().isEmpty() && startSpace.board.getSpace(startSpace.x, y).getWalls().get(0).getHeading() ==  SOUTH.next().next())
                {
                    break;
                } else {
                    newOccupiedSpaces.add(startSpace.board.getSpace(startSpace.x, y));
                    startSpace.board.getSpace(startSpace.x, y).setLaser(this);
                }
            }
        } else if(heading == WEST)
        {
            for (int x = startSpace.x-1; x > 0; x--)
            {
                if((!startSpace.board.getSpace(x, startSpace.y).getWalls().isEmpty() && startSpace.board.getSpace(x, startSpace.y).getWalls().get(0).getHeading() == WEST)
                        || playerHit(startSpace.board.getSpace(x, startSpace.y))) {
                    newOccupiedSpaces.add(startSpace.board.getSpace(x, startSpace.y));
                    startSpace.board.getSpace(x, startSpace.y).setLaser(this);
                    break;
                } else if(!startSpace.board.getSpace(x, startSpace.y).getWalls().isEmpty() && startSpace.board.getSpace(x, startSpace.y).getWalls().get(0).getHeading() == WEST.next().next())
                {
                    break;
                } else {
                    newOccupiedSpaces.add(startSpace.board.getSpace(x, startSpace.y));
                    startSpace.board.getSpace(x, startSpace.y).setLaser(this);
                }
            }
        }

        endSpace = newOccupiedSpaces.get(newOccupiedSpaces.size()-1);
        occupiedSpaces = newOccupiedSpaces;
    }

    private boolean playerHit(Space space)
    {
        if(space.getPlayer() != null)
        {
            return true;
        }
        return false;
    }

    public void removeLasersFromSpaces()
    {
        ArrayList<Space> arr = occupiedSpaces;
        if(!arr.isEmpty())
        {
            for (Space space : arr)
            {
                space.setLaser(null);
            }
        }
    }

    public boolean checkIfOccupied(Space space)
    {
        boolean occupied = false;
        if(occupiedSpaces != null) {
            for (int i = 0; i < occupiedSpaces.size(); i++) {

                if (occupiedSpaces.get(i) == space) {
                    occupied = true;
                    break;
                }
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

    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}