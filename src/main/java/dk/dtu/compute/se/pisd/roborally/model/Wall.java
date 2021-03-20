package dk.dtu.compute.se.pisd.roborally.model;

public class Wall {
    int x;
    int y;
    Heading heading;

    public Wall(int x, int y, Heading heading){
        this.x = x;
        this.y = y;
        this.heading = heading;
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
