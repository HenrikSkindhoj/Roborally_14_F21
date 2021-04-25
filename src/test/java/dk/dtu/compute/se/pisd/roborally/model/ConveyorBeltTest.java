package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConveyorBeltTest {

    GameController gameController;

    @BeforeEach
    void setUp() {
        Board board = new Board(8,8);
        gameController = new GameController(board);
        ConveyorBelt conveyorBelt = new ConveyorBelt(1,4,4,Heading.EAST);
        board.getSpace(4,4).setConveyorBelt(conveyorBelt);
        Player player = new Player(board,"red","What");
        player.setSpace(board.getSpace(3,4));
        player.setHeading(Heading.EAST);
        gameController.board.addPlayer(player);
    }

    @Test
    void doAction() {
        Player player = gameController.board.getPlayer(0);
        gameController.moveForward(player);
        boolean playerPositionCheck = false;
        gameController.board.getConveyorBelts().get(0).doAction(gameController,player.getSpace());

        if(player.getSpace() == gameController.board.getSpace(5,4))
            playerPositionCheck = true;

        Assertions.assertTrue(playerPositionCheck);
    }
}