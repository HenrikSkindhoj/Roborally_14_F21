package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LaserTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;
    private Wall[] walls = new Wall[2];

    private GameController gameController;
    private LaserView laserView;

    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);

        laserView = new LaserView(1,board);

        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }

        board.getSpace(2,2).setWall(new Wall(2,2,Heading.NORTH));
        board.getSpace(2,4).setWall(new Wall(2,4,Heading.SOUTH));

        walls[0] = board.getSpace(2,2).getWall();
        walls[1] = board.getSpace(2,4).getWall();

        laserView.setSpacesWithWalls(walls);

        laserView.spawnLasers();
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @Test
    public void startOnWallTest()
    {
        Board board = gameController.board;
        boolean laserSetOnWall = false;

        if(board.getSpace(2,2) == laserView.getLasers()[0].getStartSpace()) laserSetOnWall = true;
        else if(board.getSpace(2,4) == laserView.getLasers()[0].getStartSpace()) laserSetOnWall = true;

        Assertions.assertEquals(true,laserSetOnWall,"Should be true");
    }

    @Test
    public void stopsOnWall()
    {
        Board board = gameController.board;
        boolean stops = false;


        Assertions.assertEquals(true,stops,"Should be true");
    }



}
