package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Laser;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import java.util.ArrayList;

public class LasersView implements ViewObserver{

    private Board board;

    private ArrayList<Laser> laserViews;

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
