package dk.dtu.compute.se.pisd.roborally.model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaserTest {

    Board board;

    @BeforeEach
    void setUp() {
        this.board = new Board(8,8);
        Laser laser = new Laser(1,4,4,Heading.NORTH);
        laser.setStartSpace(board.getSpace(laser.x,laser.y));
        Wall wall = new Wall(1,4,1,Heading.NORTH);
        board.addWall(wall);
        board.addLaser(laser);
    }


    /**
     * Checking whether or not the laser stops at wall.
     */
    @Test
    void stopAtWall() {
        Laser laser = board.getSpace(4,4).getLaser();
        laser.setEndSpace();

        Space end = laser.getEndSpace();

        Assertions.assertEquals(board.getSpace(4,1),end,"Not expected space");
    }

    /**
     * Checking whether or not the laser stops when hitting a player.
     */
    @Test
    void stopsAtPlayer()
    {
        Player player = new Player(board,"red","What");
        Laser laser = board.getSpace(4,4).getLaser();
        player.setSpace(board.getSpace(4,2));
        laser.setEndSpace();

        Space end = laser.getEndSpace();

        Assertions.assertEquals(board.getSpace(4,2),end,"Not expected space");
    }

    @Test
    void getOrdinal() {
        Laser laser = board.getSpace(4,4).getLaser();
        int ord = laser.getOrdinal();
        boolean isTrue = false;

        if(ord == 2)
        {
            isTrue = true;
        }

        Assertions.assertTrue(isTrue);
    }
}