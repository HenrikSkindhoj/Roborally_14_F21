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
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @version $Id: $Id
 */
public class GameController {

    final public Board board;

    /**A boolean which determines if the game should continue, or if it has ended. */
    public boolean gameOver = false;

    /**
     * <p>Constructor for GameController.</p>
     * @param board a {@link dk.dtu.compute.se.pisd.roborally.model.Board} object.
     */
    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * <p>MoveCurrentPlayerToSpace</p>
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {
        // TODO Assignment V1: method should be implemented by the students:
        //   - the current player should be moved to the given space
        //     (if it is free()
        //   - and the current player should be set to the player
        //     following the current player
        //   - the counter of moves in the game should be increased by one
        //     if the player is moved

        if(space.getPlayer() == null) {
            Player current = board.getCurrentPlayer();
            current.setSpace(space);
            int number = board.getPlayerNumber(current);
            Player next = board.getPlayer((number + 1) % board.getPlayersNumber());
            board.setCurrentPlayer(next);

            board.setStep(board.getStep() + 1);
        }

    }

    /**
     * <p>startProgrammingPhase.</p>
     * Starts the programming phase, determines the player who should start, and sets the counter step, to 0.
     * The method then gives each player a register, and some command cards.
     */
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }


    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    /**
     * <p>finishProgrammingPhase.</p>
     * Finishes the programming phase, and sets the phase to activation phase,
     * the player who goes first, and sets the counter to 0
     */
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }


    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }


    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    /**
     * <p>executePrograms.</p>
     * A method which can be used, when the program shouldn't count steps.
     */
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    /**
     * <p>executeStep.</p>
     * A method which can be used, when the program should count steps.
     */
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }


    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;
                    if (command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }
                    executeCommand(currentPlayer, command);
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        for(int i = 0; i<board.getPlayersNumber(); i++){
                            board.getPlayer(i).controlForCheckpoints();
                            if(board.getPlayer(i).isWinner()){
                                this.gameOver = true;
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("WINNER WINNER CHICKEN DINNER!!!");
                                alert.setHeaderText(null);
                                alert.setContentText(board.getPlayer(i).getName() + " has won the game! The game will now close");
                                alert.showAndWait();

                            }
                        }
                        if(!gameOver) {
                            startProgrammingPhase();
                        }
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    /**
     * <p>executeCommandOptionAndContinue.</p>
     * The method checks if the current phase is Player_interaction,
     * and if the Player_interaction phase is done, then it gives the turn, to the next player.
     *
     * @param option a {@link dk.dtu.compute.se.pisd.roborally.model.Command} object.
     */
    public void executeCommandOptionAndContinue(@NotNull Command option){
        Player currentPlayer = board.getCurrentPlayer();
        if(currentPlayer != null && board.getPhase() == Phase.PLAYER_INTERACTION && option != null){
            board.setPhase(Phase.ACTIVATION);
            executeCommand(currentPlayer, option);
            int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
            if (nextPlayerNumber < board.getPlayersNumber()) {
                board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                continuePrograms();
            } else {
                int step = board.getStep() +1;
                if (step < Player.NO_REGISTERS) {
                    makeProgramFieldsVisible(step);
                    board.setStep(step);
                    board.setCurrentPlayer(board.getPlayer(0));
                    continuePrograms();
                } else {
                    for(int i = 0; i<board.getPlayersNumber(); i++){
                        board.getPlayer(i).controlForCheckpoints();
                        if(board.getPlayer(i).isWinner()){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("WINNER WINNER CHICKEN DINNER!!!");
                            alert.setHeaderText(null);
                            alert.setContentText(board.getPlayer(i).getName() + " has won the game! The game will now close");
                            alert.showAndWait();

                        }
                    }
                    if(!gameOver) {
                        startProgrammingPhase();
                    }
                }
            }
        }
    }

    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
                    break;
                case SPRINT_FORWARD:
                    this.sprintForward(player);
                    break;
                case U_TURN:
                    this.uTurn(player);
                    break;
                case BACK_UP:
                    this.backUp(player);
                    break;
                default:
                    // DO NOTHING (for now)
            }
        }
    }

    private void moveToSpace (
            @NotNull Player player,
            @NotNull Space space,
            @NotNull Heading heading) throws ImpossibleMoveException{

        Player other = space.getPlayer();
        if(player.getSpace().getWalls().size() > 0) {
                if (player.getSpace().getWalls().get(0).getHeading() == heading){
                    throw new ImpossibleMoveException(player, space, heading);
                }
        }
        if(space.getWalls().size() > 0) {
                if (space.getWalls().get(0).getHeading().next().next() == heading) {
                    throw new ImpossibleMoveException(player, space, heading);
                }
        }
        if (other != null) {
            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                moveToSpace(other, target, heading);
            }else {
                throw new ImpossibleMoveException(player, space, heading);
            }
        }
        player.setSpace(space);

        if(space.getLaser() != null)
        {
            System.out.println("hit!");
            space.getLaser().setEndSpace();
        }
    }


    /**
     * <p>moveForward.</p>
     * A method which contains the function of the forward command card,
     * which makes the robot go forward one space.
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     * @author Henrik Lynggard Skindhøj, s205464@student.dtu.dk
     */
    public void moveForward(@NotNull Player player) {
        if (player.board == board) {
            Space space = player.getSpace();
            Heading heading = player.getHeading();

            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                try {
                    moveToSpace(player, target, heading);
                } catch (ImpossibleMoveException e) {

                }
            }
        }
    }

    /**
     * <p>fastForward.</p>
     * A method which contains the function of the fast forward command card,
     * which makes the robot go forward two spaces.
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     * @author Henrik Lynggard Skindhøj, s205464@student.dtu.dk
     */
    public void fastForward(@NotNull Player player) {
        if (player.board == board) {
            Space currentSpace = player.getSpace();
            Heading heading = player.getHeading();

            Space targetSpace = board.getNeighbour(currentSpace,heading);
            if (targetSpace != null) {
                try {
                    moveToSpace(player, targetSpace, heading);
                } catch (ImpossibleMoveException e) {
                    targetSpace = player.getSpace();
                }
            }
            Space targetSpace2 = board.getNeighbour(targetSpace, heading);
            if (targetSpace2 != null) {
                try {
                    moveToSpace(player, targetSpace2, heading);
                } catch (ImpossibleMoveException e) {
                }
            }
        }
    }

    /**
     * <p>sprintForward.</p>
     * A method which contains the function of the sprint forward command card,
     * which makes the robot go forward three spaces.
     * @author Henrik Lynggard Skindhøj, s205464@student.dtu.dk
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     */
    public void sprintForward(@NotNull Player player){
        if(player.board == board) {
            Space currentSpace = player.getSpace();
            Heading heading = player.getHeading();
            Space targetSpace = board.getNeighbour(currentSpace,heading);
            if (targetSpace != null) {
                try {
                    moveToSpace(player, targetSpace, heading);
                } catch (ImpossibleMoveException e) {
                    targetSpace = player.getSpace();
                }
            }
            Space targetSpace2 = board.getNeighbour(targetSpace, heading);
            if (targetSpace2 != null) {
                try {
                    moveToSpace(player, targetSpace2, heading);
                } catch (ImpossibleMoveException e) {
                    targetSpace2 = player.getSpace();
                }
            }
            Space targetSpace3 = board.getNeighbour(targetSpace2,heading);
            if (targetSpace3 != null) {
                try {
                    moveToSpace(player, targetSpace3, heading);
                } catch (ImpossibleMoveException e) {

                }
            }
        }
    }

    /**
     * <p>backUp.</p>
     * A method which contains the function of the back up command card,
     * which makes the robot go backwards one space, while not changing heading.
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     * @author Kasper Falch Skov, s205429@student.dtu.dk
     */
    public void backUp(@NotNull Player player) {
        if (player.board == board) {
            Space currentSpace = player.getSpace();
            Heading heading = player.getHeading().next().next();
            Space targetSpace = board.getNeighbour(currentSpace, heading);
            if (targetSpace != null) {
                try {
                    moveToSpace(player, targetSpace, heading);
                } catch (ImpossibleMoveException e) {

                }
            }
        }
    }

    /**
     * <p>uTurn.</p>
     * A method which contains the function of the u turn command card,
     * which makes the robot turn around, without moving.
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     * @author Kasper Falch Skov, s205429@student.dtu.dk
     */
    public void uTurn(@NotNull Player player) {
        Space currentSpace = player.getSpace();
        Heading heading = player.getHeading().next().next();

        if (currentSpace != null && player.board == currentSpace.board) {
            player.setHeading(heading);
        }
    }


    /**
     * <p>turnRight.</p>
     * A method which contains the function of the turn right command card,
     * which makes the robot turn right.
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     * @author Henrik Lynggard Skindhøj, s205464@student.dtu.dk
     */
    public void turnRight(@NotNull Player player) {
        Heading current = player.getHeading();
        player.setHeading(current.next());

    }

    /**
     * <p>turnLeft.</p>
     * A method which contains the function of the turn left command card,
     * which makes the robot turn left.
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object.
     * @author Henrik Lynggard Skindhøj, s205464@student.dtu.dk
     */
    public void turnLeft(@NotNull Player player) {
        Heading current = player.getHeading();
        player.setHeading(current.prev());

    }

    /**
     * <p>moveCards.</p>
     * @param source a {@link dk.dtu.compute.se.pisd.roborally.model.CommandCardField} object.
     * @param target a {@link dk.dtu.compute.se.pisd.roborally.model.CommandCardField} object.
     * @return a boolean.
     */
    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }

}
