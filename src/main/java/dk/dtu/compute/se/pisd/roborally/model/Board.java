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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static dk.dtu.compute.se.pisd.roborally.model.Phase.INITIALISATION;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @version $Id: $Id
 */
public class Board extends Subject {

    /**
     * The width of the board
     */
    public final int width;

    /**
     * The height og the board
     */
    public final int height;

    /**
     * Currently not used
     */
    public final String boardName;

    /**
     * Currently not used
     */
    private Integer gameId;

    /**
     * A 2D array of all the spaces on the current board
     */
    private final Space[][] spaces;

    /**
     * A arraylist of all the players
     */
    private final List<Player> players = new ArrayList<>();

    /**
     * The player who has to make the next move, for the game to continue
     */
    private Player current;

    /**
     * The current phase of the game, which at the start is initialisation.
     */
    private Phase phase = INITIALISATION;

    /**
     * A counter which show the amount of moves that has been made in total.
     */
    private int step = 0;

    /**
     * A boolean which determines whether or not, the step counter, should count
     */
    private boolean stepMode;

    private Lasers lasers;

    private Checkpoints checkpoints;
    /**
     * <p>Constructor for Board.</p>
     * @param width a int.
     * @param height a int.
     * @param boardName a {@link java.lang.String} object.
     */
    public Board(int width, int height, @NotNull String boardName) {
        this.boardName = boardName;
        this.width = width;
        this.height = height;
        spaces = new Space[width][height];
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Space space = new Space(this, x, y);
                spaces[x][y] = space;
            }
        }
        checkpoints = new Checkpoints(4,this);

        this.stepMode = false;
    }
    /**
     * <p>Constructor for Board.</p>
     * @param width a int.
     * @param height a int.
     */
    public Board(int width, int height) {
        this(width, height, "defaultboard");
    }

    /**
     * <p>Getter for the field. <code>gameId</code>.</p>
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * <p>Setter for the field. <code>gameId</code>.</p>
     * @param gameId a int.
     */
    public void setGameId(int gameId) {
        if (this.gameId == null) {
            this.gameId = gameId;
        } else {
            if (!this.gameId.equals(gameId)) {
                throw new IllegalStateException("A game with a set id may not be assigned a new id!");
            }
        }
    }

    /**
     * <p>Getter for all the spaces on the board. <code>getSpace</code>.</p>
     * @param x a int.
     * @param y a int.
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.Space} object.
     */
    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }

    /**
     * <p>Getter for the amount of players. <code>getPlayersNumber</code>.</p>
     * @return a int.
     */
    public int getPlayersNumber() {
        return players.size();
    }

    /**
     * <p>addPlayer.</p>
     * Places the players on the board.
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     */
    public void addPlayer(@NotNull Player player) {
        if (player.board == this && !players.contains(player)) {
            players.add(player);
            notifyChange();
        }
    }

    /**
     * <p>addLaser.</p>
     * Places the lasers on the board
     * @param laser a {@link dk.dtu.compute.se.pisd.roborally.model.Laser} object.
     */
    public void addLaser(@NotNull Laser laser)
    {
        getSpace(laser.getStartSpace().x,laser.getStartSpace().y).setLaser(laser);
        notifyChange();
    }

    /**
     * <p>addWall.</p>
     * Places the walls on the åboard
     * @param wall a {@link dk.dtu.compute.se.pisd.roborally.model.Wall} object.
     */
    public void addWall(@NotNull Wall wall)
    {
        getSpace(wall.x, wall.y).setWall(wall);
        notifyChange();
    }

    /**
     * <p>addCheckpoint.</p>
     * Places the checkpoints on the board
     * @param checkpoint a {@link dk.dtu.compute.se.pisd.roborally.model.Checkpoint} object.
     */
    public void addCheckpoint(@NotNull Checkpoint checkpoint)
    {
        checkpoints.add(checkpoint);
        notifyChange();
    }

    /**
     * <p>Getter for player.<code>getPlayer</code>.</p>
     * @param i a int.
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     */
    public Player getPlayer(int i) {
        if (i >= 0 && i < players.size()) {
            return players.get(i);
        } else {
            return null;
        }
    }

    /**
     * <p>Getter for the players whose turn it currently is <code>getCurrentPlayer.</code>.</p>
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     */
    public Player getCurrentPlayer() {
        return current;
    }

    /**
     * <p>Setter for the players whose turn it currently is <code>setCurrentPlayer</code>.</p>
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     */
    public void setCurrentPlayer(Player player) {
        if (player != this.current && players.contains(player)) {
            this.current = player;
            notifyChange();
        }
    }

    /**
     * <p>Getter for the current phase of the game <code>getPhase</code>.</p>
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.Phase} object.
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * <p>Setter for the current phase of the game <code>setPhase</code>.</p>
     * @param phase a {@link dk.dtu.compute.se.pisd.roborally.model.Phase} object.
     */
    public void setPhase(Phase phase) {
        if (phase != this.phase) {
            this.phase = phase;
            notifyChange();
        }
    }

    /**
     * <p>Getter for the counter step <code>GetStep</code>.</p>
     * @return a int.
     */
    public int getStep() {
        return step;
    }

    /**
     * <p>Setter for the counter step <code>setStep</code>.</p>
     * @param step a int.
     */
    public void setStep(int step) {
        if (step != this.step) {
            this.step = step;
            notifyChange();
        }
    }

    /**
     * <p>isStepMode.</p>
     * The boolean, determines when the counter step, should be active. When the booelan is true,
     * the counter should count after every move a player makes.
     * @return a boolean.
     */
    public boolean isStepMode() {
        return stepMode;
    }

    /**
     * <p>Setter for the boolean to count steps <code>setStepMode</code>.</p>
     * @param stepMode a boolean.
     */
    public void setStepMode(boolean stepMode) {
        if (stepMode != this.stepMode) {
            this.stepMode = stepMode;
            notifyChange();
        }
    }

    /**
     * <p>Getter for the number of a specific player<code>getPlayerNumber</code>.</p>
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     * @return a int.
     */
    public int getPlayerNumber(@NotNull Player player) {
        if (player.board == this) {
            return players.indexOf(player);
        } else {
            return -1;
        }
    }

    /**
     * <p>Getter for a specific players neighbour<code>getNeighbour</code>.</p>
     *
     * Returns the neighbour of the given space of the board in the given heading.
     * The neighbour is returned only, if it can be reached from the given space
     * (no walls or obstacles in either of the involved spaces); otherwise,
     * null will be returned.
     *
     * @param space the space for which the neighbour should be computed
     * @param heading the heading of the neighbour
     * @return the space in the given direction; null if there is no (reachable) neighbour
     */
    public Space getNeighbour(@NotNull Space space, @NotNull Heading heading) {
        int x = space.x;
        int y = space.y;
        switch (heading) {
            case SOUTH:
                y = (y + 1) % height;
                break;
            case WEST:
                x = (x + width - 1) % width;
                break;
            case NORTH:
                y = (y + height - 1) % height;
                break;
            case EAST:
                x = (x + 1) % width;
                break;
        }

        return getSpace(x, y);
    }

    /**
     * <p>Getter for the status of the game <code>getStatusMessage</code> .</p>
     * @return a {@link java.lang.String} object.
     */
    public String getStatusMessage() {
        // this is actually a view aspect, but for making assignment V1 easy for
        // the students, this method gives a string representation of the current
        // status of the game

        // XXX: V2 changed the status so that it shows the phase, the player and the step

        return "Phase: " + getPhase().name() +
                ", Player = " + getCurrentPlayer().getName() +
                ", Step: " + getStep() + ", Next Checkpoint: " + getCurrentPlayer().getNextChecpoint();
    }

    /**
     * <p>Getter for the status of the walls on the board <code>getWalls</code> .</p>
     * @return a walls object.
     */
    public ArrayList<Wall> getWalls()
    {
        ArrayList<Wall> walls = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(!getSpace(x,y).getWalls().isEmpty()){
                    walls.add(getSpace(x,y).getWalls().get(0));
                }
            }
        }
        return walls;
    }

    /**
     * <p>Getter for the checkpoints on the board <code>getCheckpoints</code> .</p>
     * @return a checkpoints object.
     */
    public ArrayList<ConveyorBelt> getConveyorBelts()
    {
        ArrayList<ConveyorBelt> conveyorBelts = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(getSpace(x,y).getConveyorBelt() != null){
                    conveyorBelts.add(getSpace(x,y).getConveyorBelt());
                }
            }
        }
        return conveyorBelts;
    }

    public Checkpoints getCheckpoints() {
        return checkpoints;
    }

    /**
     * <p>Getter for lasers on the board <code>getLasers</code> .</p>
     * @return a lasers object.
     */
    public ArrayList<Laser> getLasers() {
        ArrayList<Laser> lasers = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(getSpace(x,y).getLaser()!= null){
                    if(!lasers.contains(getSpace(x,y).getLaser()))
                        lasers.add(getSpace(x,y).getLaser());
                }
            }
        }
        return lasers;
    }

    /**
     * <p>Setter for the lasers <code>setLasers</code> .</p>
     * @param lasers a {@link dk.dtu.compute.se.pisd.roborally.model.Laser} object.
     */
    public void setLasers(Lasers lasers) {
        this.lasers = lasers;
    }

    /**
     * <p>Setter for the checkpoints <code>setCheckpoints</code> .</p>
     * @param checkpoints a {@link dk.dtu.compute.se.pisd.roborally.model.Checkpoints} object.
     */
    public void setCheckpoints(Checkpoints checkpoints) {
        this.checkpoints = checkpoints;
    }

    /**
     * <p>Getter for the players in a game <code>getPlayers</code> .</p>
     * @return a Player arraylist.
     */
    public List<Player> getPlayers() {
        return players;
    }
}
