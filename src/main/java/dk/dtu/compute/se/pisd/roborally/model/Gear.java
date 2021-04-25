package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Henrik Lynggard Skindhøj, s205464@student.dtu.dk
 *
 */
public class Gear extends FieldAction {
    /**
     * The id of a gear
     */
    int id;
    /**
     * The location of a wall on the x-axis
     */
    int x;
    /**
     * The location of a wall on the y-axis
     */
    int y;

    Command command;

    /**
     * <p>Constructor for Gear.</p>
     * @param command {@link dk.dtu.compute.se.pisd.roborally.model.Command} object.
     * @param x a int.
     * @param y a int.
     * @param id a int.
     */
    public Gear (int id, int x, int y, Command command){
        this.id = id;
        this.x = x;
        this.y = y;
        this.command = command;
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


    /**
     *  <p>Getter for the direction the gear turns <code>getCommand</code>.</p>
     * @return command a {@link dk.dtu.compute.se.pisd.roborally.model.Command} object.
     * @author Henrik Lynggard Skindhøj, s205464@student.dtu.dk
     */
    public Command getCommand() {
        return command;
    }

    /**
     *  <p>Getter for location of a gear on the x axis <code>getX</code>.</p>
     * @return x int.
     * @author Henrik Lynggard Skindhøj, s205464@student.dtu.dk
     */
    public int getX() {
        return x;
    }

    /**
     *  <p>Getter for location of a gear on the y axis <code>getY</code>.</p>
     * @return y int.
     * @author Henrik Lynggard Skindhøj, s205464@student.dtu.dk
     */
    public int getY() {
        return y;
    }
}
