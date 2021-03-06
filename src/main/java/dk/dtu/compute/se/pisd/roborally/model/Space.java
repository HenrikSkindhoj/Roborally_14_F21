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

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @version $Id: $Id
 */
public class Space extends Subject {

    public final Board board;

    /**
     * The location on the x-axis, of a space on the board
     */
    public final int x;
    /**
     * The location on the y-axis, of a space on the board.
     */
    public final int y;

    private Player player;
    private Laser laser;
    private ArrayList<Wall> walls = new ArrayList<>();
    private Checkpoint checkpoint;
    private ConveyorBelt conveyorBelt;
    private Gear gear;

    /**
     * <p>Constructor for Space.</p>
     * @param board a {@link dk.dtu.compute.se.pisd.roborally.model.Board} object.
     * @param x a int.
     * @param y a int.
     */
    public Space(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        player = null;
        checkpoint = null;
    }

    /**
     * <p>Getter for player<code>getPlayer</code>.</p>
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * <p>Setter for player<code>setPlayer</code>.</p>
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     */
    public void setPlayer(Player player) {
        Player oldPlayer = this.player;
        if (player != oldPlayer &&
                (player == null || board == player.board)) {
            this.player = player;
            if (oldPlayer != null) {
                // this should actually not happen
                oldPlayer.setSpace(null);
            }
            if (player != null) {
                player.setSpace(this);
            }
            notifyChange();
        }
    }

    /**
     * <p>Setter for laser<code>setLaser</code>.</p>
     * @param laser a object
     */
    public void setLaser(Laser laser) {
        this.laser = laser;
    }

    /**
     * <p>Getter for laser<code>getLaser</code>.</p>
     * @return a laser object
     */
    public Laser getLaser() {
        return laser;
    }

    void playerChanged() {
        // This is a minor hack; since some views that are registered with the space
        // also need to update when some player attributes change, the player can
        // notify the space of these changes by calling this method.
        notifyChange();
    }

    /**
     * <p>Setter for wall<code>setWall</code>.</p>
     * @param wall a {@link dk.dtu.compute.se.pisd.roborally.model.Wall} object.
     * @author Kasper Falch Skov, s205429@student.dtu.dk
     */
    public void setWall(Wall wall){
        this.walls.add(wall);
    }

    /**
     * <p>Getter for wall<code>getWall</code>.</p>
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.Wall} object.
     * @author Kasper Falch Skov, s205429@student.dtu.dk
     */
    public ArrayList<Wall> getWalls() {
        return walls;
    }

    /**
     * <p>Setter a array list of walls <code>setWalls</code>.</p>
     * @param walls a {@link dk.dtu.compute.se.pisd.roborally.model.Wall} object.
     * @author Kasper Falch Skov, s205429@student.dtu.dk
     */
    public void setWalls(List<Wall> walls){
        this.walls.addAll(walls);
    }

    /**
     * <p>Getter for the checkpoints <code>getCheckpoint</code>.</p>
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.Checkpoint} object.
     * @author Kasper Falch Skov, s205429@student.dtu.dk
     */
    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    /**
     * <p>Setter for checkpoint <code>setCheckpoint</code>.</p>
     * @param checkpoint a {@link dk.dtu.compute.se.pisd.roborally.model.Checkpoint} object.
     * @author Kasper Falch Skov, s205429@student.dtu.dk
     */
    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    /**
     * <p>Getter for the conveyor belts <code>getConveyorBelt</code>.</p>
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.ConveyorBelt} object.
     *
     */
    public ConveyorBelt getConveyorBelt() {
        return conveyorBelt;
    }

    /**
     * <p>Setter for the conveyor belts <code>setConveyorBelt</code>.</p>
     * @param conveyorBelt a {@link dk.dtu.compute.se.pisd.roborally.model.ConveyorBelt} object.
     */
    public void setConveyorBelt(ConveyorBelt conveyorBelt) {
        this.conveyorBelt = conveyorBelt;
    }

    /**
     * <p>Getter for the gears <code>getGear</code>.</p>
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.Gear} object.
     */
    public Gear getGear() {
        return gear;
    }

    /**
     * <p>Setter for the gears <code>setGear</code>.</p>
     * @param gear a {@link dk.dtu.compute.se.pisd.roborally.model.Gear} object.
     */
    public void setGear(Gear gear) {
        this.gear = gear;
    }
}
