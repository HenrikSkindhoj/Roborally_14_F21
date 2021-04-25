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

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @version $Id: $Id
 */
public class Player extends Subject {

    /**
     * Constant <code>NO_REGISTERS=5</code>
     */
    final public static int NO_REGISTERS = 5;
    /**
     * Constant <code>NO_CARDS=8</code>
     */
    final public static int NO_CARDS = 8;

    final public Board board;

    /**
     * Counts how many times the player was hit by a DANGEROUS object.
     * Used to assert number of spamcards.
     * Example: If player was hit by 1 laser. Then player gets 1 spam card.
     */
    private int hit = 0;

    /**
     * Currently not used
     */
    private String name;
    /**
     * The color of a robot
     */
    private String color;
    private Space space;
    private Heading heading = SOUTH;
    private boolean[] checkpoints = new boolean[4];
    /**
     * A integer of which checkpoint the player need to get next
     */
    private int nextChecpoint = 1;
    /**
     * A boolean which when true, means a player has won
     */
    private boolean winner;

    /**
     * Array of the players registers
     */
    private CommandCardField[] program;
    /**
     * Array of the players cards
     */
    private CommandCardField[] cards;

    /**
     * <p>Constructor for Player.</p>
     *
     * @param board a {@link dk.dtu.compute.se.pisd.roborally.model.Board} object.
     * @param color a {@link java.lang.String} object.
     * @param name  a {@link java.lang.String} object.
     */
    public Player(@NotNull Board board, String color, @NotNull String name) {
        this.board = board;
        this.name = name;
        this.color = color;

        this.space = null;

        program = new CommandCardField[NO_REGISTERS];
        for (int i = 0; i < program.length; i++) {
            program[i] = new CommandCardField(this);
        }

        cards = new CommandCardField[NO_CARDS];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new CommandCardField(this);
        }
    }

    /**
     * <p>Getter for the name of a player <code>getName</code>.</p>
     * Currently not used
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Setter for the name of a player <code>setName</code>.</p>
     * Currently not used
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    /**
     * <p>Getter for the color of a player <code>getColor</code>.</p>
     * @return a {@link java.lang.String} object.
     */
    public String getColor() {
        return color;
    }

    /**
     * <p>Setter for the color of a player <code>color</code>.</p>
     * @param color a {@link java.lang.String} object.
     */
    public void setColor(String color) {
        this.color = color;
        notifyChange();
        if (space != null) {
            space.playerChanged();
        }
    }

    /**
     * A method for when a player takes damage and receives a spam card.
     */
    public void damage()
    {
        hit++;
    }

    /**
     * Returns amount of hits player has taken.
     * @return hit a int.
     */
    public int numHits()
    {
        return hit;
    }

    /**
     * When a player plays a spam card, they get one less hit.
     */
    public void rejuvenate()
    {
        hit--;
    }

    /**
     * <p>Getter for the spaces on the board <code>getSpace</code>.</p>
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.Space} object.
     */
    public Space getSpace() {
        return space;
    }

    /**
     * <p>Setter for the spaces on the board <code>setSpace</code>.</p>
     * @param space a {@link dk.dtu.compute.se.pisd.roborally.model.Space} object.
     */
    public void setSpace(Space space) {
        Space oldSpace = this.space;
        if (space != oldSpace &&
                (space == null || space.board == this.board)) {
            this.space = space;
            if (oldSpace != null) {
                oldSpace.setPlayer(null);
            }
            if (space != null) {
                space.setPlayer(this);
            }
            notifyChange();
        }
    }

    /**
     * <p>Getter for the way the player is heading <code>getHeading</code>.</p>
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.Heading} object.
     */
    public Heading getHeading() {
        return heading;
    }

    /**
     * <p>Setter for the way the player is heading <code>setHeading</code>.</p>
     * @param heading a {@link dk.dtu.compute.se.pisd.roborally.model.Heading} object.
     */
    public void setHeading(@NotNull Heading heading) {
        if (heading != this.heading) {
            this.heading = heading;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    /**
     * <p> getProgramField.</p>
     * @param i a int.
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.CommandCardField} object.
     */
    public CommandCardField getProgramField(int i) {
        return program[i];
    }

    /**
     * <p>getCardField.</p>
     * @param i a int.
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.CommandCardField} object.
     */
    public CommandCardField getCardField(int i) {
        return cards[i];
    }

    /**
     * <p>getCheckpoints.</p>
     * @return a {@link dk.dtu.compute.se.pisd.roborally.model.Checkpoints} object.
     */
    public boolean[] getCheckpoints() {
        return checkpoints;
    }

    /**
     * <p>controlForCheckpoints </p>
     * A method that decides which checkpoints the player has gotten, and which one the player has to get next.
     */
    public void controlForCheckpoints() {
        if (space.getCheckpoint() != null) {
            if (!checkpoints[0]) {
                if (space.getCheckpoint().getId() == 1) {
                    checkpoints[0] = true;
                    nextChecpoint = 2;
                }
            } else if (!checkpoints[1]) {
                if (space.getCheckpoint().getId() == 2) {
                    checkpoints[1] = true;
                    nextChecpoint = 3;
                }
            } else if (!checkpoints[2]) {
                if (space.getCheckpoint().getId() == 3) {
                    checkpoints[2] = true;
                    nextChecpoint = 4;
                }

            } else if (!checkpoints[3]) {
                if (space.getCheckpoint().getId() == 4) {
                    checkpoints[3] = true;
                    winner = true;
                }
            }
        }
    }

    /**
     * <p>getNextChecpoint.</p>
     * @return a int.
     */
    public int getNextChecpoint() {
        return nextChecpoint;
    }

    /**
     * <p>isWinner.</p>
     * @return a boolean.
     */
    public boolean isWinner() {
        return winner;
    }
}

