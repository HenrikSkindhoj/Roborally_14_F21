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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @version $Id: $Id
 */
public enum Command {

    // This is a very simplistic way of realizing different commands.

    FORWARD("Fwd"),
    RIGHT("Turn Right"),
    LEFT("Turn Left"),
    FAST_FORWARD("Fast Fwd"),

    // XXX Assignment V3
    OPTION_LEFT_RIGHT("Left OR Right", LEFT, RIGHT),
    OPTION_FORWARD_FAST_FORWARD("Forward or Fast Fwd", FORWARD, FAST_FORWARD),
    SPRINT_FORWARD("Sprint Fwd"),
    BACK_UP("Back Up"),
    U_TURN("U-Turn");

    final public String displayName;

    // XXX Assignment V3
    // Command(String displayName) {
    //     this.displayName = displayName;
    // }
    //
    // replaced by the code below:

    /**
     * The arraylist options, which contains multiple commands.
     */
    final private List<Command> options;

    /**
     * <p>Constructor for Command.</p>
     * @param displayName a {@link java.lang.String}.
     * @param options a Command object.
     */
    Command(String displayName, Command... options) {
        this.displayName = displayName;
        this.options = Collections.unmodifiableList(Arrays.asList(options));
    }

    /**
     * <p>isInteractive.</p>
     * Is true when a player has played a command card which has multiple options,
     * and remains true while the player has to chose.
     * @return a boolean.
     */
    public boolean isInteractive() {
        return !options.isEmpty();
    }

    /**
     * <p>Getter for the field <code>options</code>.</p>
     * @return a {@link java.util.List} object.
     */
    public List<Command> getOptions() {
        return options;
    }

}
