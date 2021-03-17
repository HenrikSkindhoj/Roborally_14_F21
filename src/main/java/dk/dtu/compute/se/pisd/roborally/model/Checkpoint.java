package dk.dtu.compute.se.pisd.roborally.model;

public class Checkpoint {

    private int id;
    private int x;
    private int y;

    public Checkpoint(int x, int y, int id)
    {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }
}
