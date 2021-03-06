/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class ConveyorBelt extends FieldAction {
    /**
     * The id of a conveyor belt.
     */
    int id;
    /**
     * The location of a conveyor on the x-axis
     */
    int x;
    /**
     * The location of a conveyor on the y-axis
     */
    int y;
    /**
     * The direction a conveyor belt points.
     */
    private Heading heading;

    /**
     * <p>Constructor for ConveyorBelt. </p>
     * @param heading {@link dk.dtu.compute.se.pisd.roborally.model.Heading} object.
     * @param x a int.
     * @param y a int.
     * @param id a int.
     */
    public ConveyorBelt(int id, int x, int y, Heading heading){
        this.id = id;
        this.x = x;
        this.y = y;
        this.heading = heading;

    }

    /**
     * <p>Getter for location of the conveyor belt on the x axis <code>getX</code>.</p>
     * @return a int x.
     * @author Kasper Falch Skov, s205429@student.dtu.dk
     */
    public int getX() {
        return x;
    }

    /**
     * <p>Getter for location of the conveyor belt on the y axis <code>getX</code>.</p>
     * @return a int y.
     * @author Kasper Falch Skov, s205429@student.dtu.dk
     */
    public int getY() {
        return y;
    }

    /**
     * <p>Getter for the heading og the conveyor belt<code>getHeading</code>.</p>
     * @return the heading of the conveyor belt.
     * @author Kasper Falch Skov, s205429@student.dtu.dk
     */

    public Heading getHeading() {
        return heading;
    }

    /**
     * <p>Setter for the heading of the conveyor belt <code>setHeading</code>.</p>
     * @param heading the heading of the conveyor belt.
     * @author Kasper Falch Skov, s205429@student.dtu.dk
     */
    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    /** {@inheritDoc} */
    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        if(space.getPlayer() != null){
            Space conveyor = space;
            Heading heading = getHeading();

            Space conveyorTarget = gameController.board.getNeighbour(conveyor, heading);
            if (conveyorTarget != null) {
                try {
                    gameController.moveToSpace(space.getPlayer(), conveyorTarget, heading);
                } catch (ImpossibleMoveException e) {

                }
            }

        }
        return false;
    }

}
