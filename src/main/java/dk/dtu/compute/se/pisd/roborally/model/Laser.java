package dk.dtu.compute.se.pisd.roborally.model;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

public class Laser
{
    private int x;
    private int y;
    private Heading heading;


    public Laser(int x, int y, Heading heading)
    {
        this.x = x;
        this.y = y;
        this.heading = heading;

    }

    public void hits(Player player)
    {

    }


    public int getX1() {
        return x;
    }

    public int getY1() {
        return y;
    }

}