package dk.dtu.compute.se.pisd.roborally.model;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

public class Laser
{
    private  int id;
    private int x;
    private int y;
    private Heading heading;


    public Laser(int id, int x, int y, Heading heading)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.heading = heading;

    }

    public void hits(Player player)
    {

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