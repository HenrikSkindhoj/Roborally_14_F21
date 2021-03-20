package dk.dtu.compute.se.pisd.roborally.model;

import java.util.ArrayList;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;

public class Laser
{
    private int id;
    private int x;
    private int y;
    private int maxX;
    private int maxY;
    private Heading heading;

    public Laser(int id, int x, int y, Heading heading, int maxX, int maxY)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.maxX = maxX;
        this.maxY = maxY;
        this.heading = heading;

    }

    public boolean hit(int givenX, int givenY)
    {
        boolean isHit = false;

        if(heading == NORTH)
        {
            if(givenX == x && givenY <= y)
            {
                isHit = true;
            }
        } else if(heading == EAST)
        {
            if(givenX >= x && givenY == y)
            {
                isHit = true;
            }
        } else if(heading == SOUTH)
        {
            if(givenX == x && givenY >= y)
            {
                isHit = true;
            }
        } else if(heading == WEST)
        {
            if(givenX <= x && givenY == y)
            {
                isHit = true;
            }
        }
        return isHit;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Heading getHeading() {
        return heading;
    }
}