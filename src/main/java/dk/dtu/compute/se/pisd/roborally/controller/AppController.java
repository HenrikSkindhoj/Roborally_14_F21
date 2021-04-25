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

import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import dk.dtu.compute.se.pisd.roborally.RoboRally;

import dk.dtu.compute.se.pisd.roborally.dal.GameInDB;
import dk.dtu.compute.se.pisd.roborally.dal.IRepository;
import dk.dtu.compute.se.pisd.roborally.dal.RepositoryAccess;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @version $Id: $Id
 */
public class AppController implements Observer {

    /** Constant <code>PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6)</code> */
    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    /** Constant <code>PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta")</code> */
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    /** Constant <code>MAP_OPTIONS = Arrays.asList("1", "2")</code> */
    final private List<String> MAP_OPTIONS = Arrays.asList("1", "2", "High_Octane");

    /** Constant */
    final private RoboRally roboRally;

    private GameController gameController;

    /**
     * <p>Constructor for AppController.</p>
     * @param roboRally a {@link dk.dtu.compute.se.pisd.roborally.RoboRally} object.
     */
    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    /**
     * <p>newGame.</p>
     * The method which gives the user det option to select the number of players,
     * who are playing, when the user starts a new game.
     * Then the method creates a board, and gives the players a color each.
     */
    public void newGame() {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);
        dialog.setTitle("Player number");
        dialog.setHeaderText("Select number of players");
        Optional<Integer> result = dialog.showAndWait();
        LoadBoard loadBoard = new LoadBoard();

        if (result.isPresent()) {
            if (gameController != null) {
                // The UI should not allow this, but in case this happens anyway.
                // give the user the option to save the game or abort this operation!
                if (!stopGame()) {
                    return;
                }
            }



            ChoiceDialog<String> dialog1 = new ChoiceDialog<>(MAP_OPTIONS.get(0), MAP_OPTIONS);
            dialog1.setTitle("Map Selection");
            dialog1.setHeaderText("Select a map");
            Optional<String> result1 = dialog1.showAndWait();



            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.
            Board board =  loadBoard.loadBoard(result1.get());;

            gameController = new GameController(board);
            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));
            }

            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }

    /**
     * <p>saveGame.</p>
     * This method saves the current game in the database, if there is a game currently being played.
     */
    public void saveGame() {
        IRepository repo = RepositoryAccess.getRepository();
        List<GameInDB> repoGames = repo.getGames();
        if(this.gameController.board.getGameId() != null){
            for (GameInDB game : repoGames) {
                if (game.id == this.gameController.board.getGameId()) {
                    repo.updateGameInDB(this.gameController.board);
                    return;
                }
            }
        }
        repo.createGameInDB(this.gameController.board);
    }

    /**
     * <p>loadGame.</p>
     * This method makes it possible to load previously saved games, from the database.
     * When a user presses load game, the user will receive a list of saved games, that they can load.
     * If there are no games saved in the database, the method will tell the player so.
     *
     */
    public void loadGame() {
        if (gameController == null) {
            List<GameInDB> games = RepositoryAccess.getRepository().getGames();
            if(!games.isEmpty())
            {
                ChoiceDialog<GameInDB> dialog = new ChoiceDialog<>(games.get(games.size()-1), games);
                dialog.setTitle("Select game");
                dialog.setHeaderText("Select a game number");
                Optional<GameInDB> result = dialog.showAndWait();

                if(result.isPresent())
                {
                    Board board = RepositoryAccess.getRepository().loadGameFromDB(result.get().id);
                    if(board != null)
                    {
                        gameController = new GameController(board);
                        roboRally.createBoardView(gameController);
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Problems loading game");
                        alert.setHeaderText("There was a problem loading the game!");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("No games");
                alert.setHeaderText("There are no games in the database yet!");
                alert.showAndWait();
            }
        }
    }

    /**
     * <p>stopGame.</p>
     * Stop playing the current game, giving the user the option to save
     * the game or to cancel stopping the game. The method returns true
     * if the game was successfully stopped (with or without saving the
     * game); returns false, if the current game was not stopped. In case
     * there is no current game, false is returned.
     *
     * @return true if the current game was stopped, false otherwise
     */
    public boolean stopGame() {
        if (gameController != null) {

            // here we save the game (without asking the user).
            saveGame();

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }

    /**
     * <p>exit.</p>
     * When the user exits the game, they will get the a pop-up confirming if they want to close the game.
     * if the user doesn't press ok, then the game will continue.
     */
    public void exit() {
        if (gameController != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit RoboRally?");
            alert.setContentText("Are you sure you want to exit RoboRally?");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {
                return; // return without exiting the application
            }
        }

        // If the user did not cancel, the RoboRally application will exit
        // after the option to save the game
        if (gameController == null || stopGame()) {
            Platform.exit();
        }
    }

    /**
     * <p>isGameRunning.</p>
     * A boolean, which when true, means that a game is currently being played
     * @return a boolean.
     */
    public boolean isGameRunning() {
        return gameController != null;
    }


    /** {@inheritDoc} */
    @Override
    public void update(Subject subject) {
        // XXX do nothing for now
    }

}
