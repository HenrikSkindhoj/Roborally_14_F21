package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LaserTest {
/*
    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;
    private ArrayList<Wall> walls = new ArrayList<>();

    private GameController gameController;
    private Lasers lasers;

    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);

        lasers = new Lasers(1,board);

        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }

        board.getSpace(2,2).setWall(new Wall(1,2,2,Heading.NORTH));
        board.getSpace(2,4).setWall(new Wall(1,2,4,Heading.SOUTH));

        walls.add(board.getSpace(2,2).getWall());
        walls.add(board.getSpace(2,4).getWall());

        lasers.setSpacesWithWalls(walls);

        lasers.spawnLasers();
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @Test
    public void startOnWallTest()
    {
        Board board = gameController.board;
        boolean laserSetOnWall = false;

        if(board.getSpace(2,2) == lasers.getLasers().get(0).getStartSpace()) laserSetOnWall = true;
        else if(board.getSpace(2,4) == lasers.getLasers().get(0).getStartSpace()) laserSetOnWall = true;

        Assertions.assertEquals(true,laserSetOnWall,"Should be true");
    }

    @Test
    public void stopsOnWall()
    {
        Board board = gameController.board;
        boolean stops = false;
        ArrayList<Space> arrayList = lasers.getLasers().get(0).getOccupiedSpaces();

        for(int i = 0; i < arrayList.size(); i++)
        {
            if(arrayList.get(i) == board.getSpace(2,2)
                    || arrayList.get(i) == board.getSpace(2,3)
                    || arrayList.get(i) == board.getSpace(2,4))
            {
                stops = true;
            } else
                {
                    stops = false;
                    break;
                }
        }

        Assertions.assertEquals(true,stops,"Should be true");
    }

*/

}
