package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;

public class Gear extends FieldAction {
    int id;
    /**
     * The location of a wall on the x-axis
     */
    int x;
    /**
     * The location of a wall on the y-axis
     */
    int y;
    String direction;
    Command command;

    public Gear (int id, int x, int y, String direction){
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        if(space.getPlayer() != null){
            switch(command){
                case LEFT:
                    Heading current = space.getPlayer().getHeading();
                    space.getPlayer().setHeading(current.prev());
                    break;

                case RIGHT:
                    Heading curr = space.getPlayer().getHeading();
                    space.getPlayer().setHeading(curr.next());
                    break;
                default:
            }

        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
