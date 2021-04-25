package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Laser;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import java.util.ArrayList;

/**
 * ...
 *
 * @author Hans Christian Leth-Nissen, s205435@student.dtu.dk
 *
 */
public class LasersView implements ViewObserver{

    private Board board;

    private ArrayList<Laser> laserViews;

    /**
     * <p>Construct for LasersView</p>
     * @param gameController a {@link dk.dtu.compute.se.pisd.roborally.controller.GameController} object
     */
    public LasersView(GameController gameController)
    {
        board = gameController.board;

        laserViews = gameController.board.getLasers();

        for (Laser laser: laserViews) {
            laser.attach(this);
        }
    }

    @Override
    public void updateView(Subject subject) {
        if(subject == board)
        {
            for(Laser laser : laserViews)
            {
                this.updateView(laser);
            }
        }
    }
}
