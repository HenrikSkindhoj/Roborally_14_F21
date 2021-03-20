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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import org.jetbrains.annotations.NotNull;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @version $Id: $Id
 */
public class SpaceView extends StackPane implements ViewObserver {

    /** Constant <code>SPACE_HEIGHT=75</code> */
    final public static int SPACE_HEIGHT = 75; // 60; // 75;
    /** Constant <code>SPACE_WIDTH=75</code> */
    final public static int SPACE_WIDTH = 75;  // 60; // 75;

    public final Space space;
    public CheckpointsView checkpointsView;
    public WallView wallView;
    public LaserView laserView;



    /**
     * <p>Constructor for SpaceView.</p>
     *
     * @param space a {@link dk.dtu.compute.se.pisd.roborally.model.Space} object.
     */
    public SpaceView(@NotNull Space space, CheckpointsView checkpointsView, WallView wallView, LaserView laserView) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

        if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }

        // updatePlayer();
        this.laserView = laserView;
        this.checkpointsView = checkpointsView;
        this.wallView = wallView;
        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    private void updatePlayer() {
        this.getChildren().clear();

        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0 );
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90*player.getHeading().ordinal())%360);
            this.getChildren().add(arrow);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            updatePlayer();

            for(int i = 0; i < checkpointsView.getCheckpoints().length; i++)
            {
                if(this.space.x == checkpointsView.getCheckpoints()[i].getX() && this.space.y == checkpointsView.getCheckpoints()[i].getY())
                    updateCheckpoint(checkpointsView.getCheckpoints()[i]);
            }

            for(int i = 0; i < wallView.getWalls().length; i++){
                if(this.space.x == wallView.getWalls()[i].getX() && this.space.y == wallView.getWalls()[i].getY())
                updateWall(wallView.getWalls()[i]);
            }

            for(int i = 0; i < laserView.getLasers().length; i++)
            {
                if(this.space.x == laserView.getLasers()[i].getX() && this.space.y == laserView.getLasers()[i].getY())
                    updateLasers(laserView.getLasers()[i]);
            }
        }
    }

    public void updateCheckpoint(Checkpoint checkpoint)
    {
        Canvas can = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);

        GraphicsContext gc = can.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.strokeText(Integer.toString(checkpoint.getId()),SPACE_HEIGHT/2,SPACE_WIDTH/2);
        gc.rect(2,2,SPACE_WIDTH-2,SPACE_HEIGHT-2);

        this.getChildren().add(can);
    }

    public void updateWall(Wall wall){
        Canvas canvas = new Canvas(SPACE_WIDTH,SPACE_HEIGHT);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);

        if(wall.getHeading() == NORTH){
           gc.strokeLine(2, SPACE_HEIGHT-73,SPACE_WIDTH-2,SPACE_HEIGHT-73);
           this.getChildren().add(canvas);
        }
        else if(wall.getHeading() == SOUTH){
            gc.strokeLine(2, SPACE_HEIGHT-2,SPACE_WIDTH-2,SPACE_HEIGHT-2);
            this.getChildren().add(canvas);
        }
        else if(wall.getHeading() == EAST){
            gc.strokeLine(73, SPACE_HEIGHT-2,SPACE_WIDTH-2,SPACE_HEIGHT-73);
            this.getChildren().add(canvas);
        }
        else if(wall.getHeading() == WEST){
            gc.strokeLine(2, SPACE_HEIGHT-2,SPACE_WIDTH-73,SPACE_HEIGHT-73);
            this.getChildren().add(canvas);
        }


    }

    public void updateLasers(Laser laser)
    {
        Canvas can = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);

        GraphicsContext gc = can.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);

        if(laser.getHeading() == Heading.NORTH || laser.getHeading() == Heading.SOUTH)
        {
            gc.strokeLine(0,35,75,35);
        } else
            {
                gc.strokeLine(35,0,35,75);
            }
    }

}
