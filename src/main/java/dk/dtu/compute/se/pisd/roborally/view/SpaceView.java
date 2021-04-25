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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;

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
    public Checkpoints checkpointsView;

    public Lasers laserView;



    /**
     * <p>Constructor for SpaceView.</p>
     *
     * @param space a {@link dk.dtu.compute.se.pisd.roborally.model.Space} object.
     */
    public SpaceView(@NotNull Space space) {
        this.space = space;

        this.setStyle("-fx-background-image: url('Visuals/Basicfloor.PNG'); " +
                "-fx-background-size: cover;");

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);




        // updatePlayer();
        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    private void updatePlayer() {

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
            this.getChildren().clear();


            for(int i = 0; i < space.board.getCheckpoints().getCheckpoints().size(); i++)
            {
                if(this.space.x == space.board.getCheckpoints().getCheckpoints().get(i).getX() && this.space.y ==
                        space.board.getCheckpoints().getCheckpoints().get(i).getY()) {
                    updateCheckpoint(space.board.getCheckpoints().getCheckpoints().get(i));
                    this.space.setCheckpoint(space.board.getCheckpoints().getCheckpoints().get(i));
                }
            }

            for(int i = 0; i < space.board.getWalls().size(); i++){
                if(this.space.x == space.board.getWalls().get(i).getX() && this.space.y == space.board.getWalls().get(i).getY()) {
                    updateWall(space.board.getWalls().get(i));
                }
            }
            for(int i = 0; i < space.board.getLasers().size(); i++)
            {
                if(space.board.getLasers().get(i).checkIfOccupied(this.space)) {
                    updateLasers(space.board.getLasers().get(i));
                }
            }
            for(int i = 0; i < space.board.getConveyorBelts().size(); i++) {
                if (space.getConveyorBelt() != null) {
                    if (this.space.x == space.getConveyorBelt().getX() && this.space.y == space.getConveyorBelt().getY()) {
                        updateConveyorBelt(space.getConveyorBelt());
                    }
                }
            }
            for(int i = 0; i < space.board.getGears().size(); i++) {
                if (space.getGear() != null) {
                    if (this.space.x == space.getGear().getX() && this.space.y == space.getGear().getY()) {
                        updateGear(space.getGear());
                    }
                }
            }
            updatePlayer();
        }
    }

    /**
     * <p>updateCheckpoints.</p>
     * Draws the checkpoints, and decides how they should look.
     * @param checkpoint a {@link dk.dtu.compute.se.pisd.roborally.model.Checkpoint} object.
     */
    public void updateCheckpoint(Checkpoint checkpoint)
    {
        Canvas can = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        final Image point = new Image ("Visuals/Checkpoints.PNG");

        GraphicsContext gc = can.getGraphicsContext2D();
        gc.drawImage(point,0,0,75,75);
        gc.setStroke(Color.RED);
        gc.strokeText(Integer.toString(checkpoint.getId()),SPACE_HEIGHT/2,SPACE_WIDTH/2);
        gc.rect(2,2,SPACE_WIDTH-2,SPACE_HEIGHT-2);

        this.getChildren().add(can);
    }

    /**
     * <p>updateWall.</p>
     * Draws the walls, and decides how they should look.
     * @param wall a {@link dk.dtu.compute.se.pisd.roborally.model.Wall} object.
     */
    public void updateWall(Wall wall){
        Canvas canvas = new Canvas(SPACE_WIDTH,SPACE_HEIGHT);
        final Image northWall = new Image ("Visuals/nWall.PNG");

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.ROYALBLUE);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);

        if(wall.getHeading() == NORTH){
           //gc.strokeLine(2, SPACE_HEIGHT-73,SPACE_WIDTH-2,SPACE_HEIGHT-73);
            gc.drawImage(northWall,0,0,75,20);
           this.getChildren().add(canvas);
        }
        else if(wall.getHeading() == SOUTH){
            gc.strokeLine(2, SPACE_HEIGHT-2,SPACE_WIDTH-2,SPACE_HEIGHT-2);
            ImageView ivS = new ImageView(northWall);
            ivS.setRotate(180);
            SnapshotParameters params1 = new SnapshotParameters();
            params1.setFill(Color.TRANSPARENT);
            Image rotatedImageS = ivS.snapshot(params1, null);
            gc.drawImage(rotatedImageS, 0,55,75,20);
            this.getChildren().add(canvas);
        }
        else if(wall.getHeading() == EAST){
            gc.strokeLine(73, SPACE_HEIGHT-2,SPACE_WIDTH-2,SPACE_HEIGHT-73);
            ImageView ivE = new ImageView(northWall);
            ivE.setRotate(90);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            Image rotatedImageE = ivE.snapshot(params, null);
            gc.drawImage(rotatedImageE, 55,0, 20, 75);
            this.getChildren().add(canvas);
        }
        else if(wall.getHeading() == WEST){
            gc.strokeLine(2, SPACE_HEIGHT-2,SPACE_WIDTH-73,SPACE_HEIGHT-73);
            ImageView iv = new ImageView(northWall);
            iv.setRotate(270);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            Image rotatedImageW = iv.snapshot(params, null);
            gc.drawImage(rotatedImageW, 0,0,20,75);
            this.getChildren().add(canvas);
        }
    }

    /**
     * <p>updateLasers</p>
     * Draws the laser, and decides how they should look.
     * @param laser a {@link dk.dtu.compute.se.pisd.roborally.model.Laser} object.
     */
    public void updateLasers(Laser laser)
    {
       Canvas can = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);

        GraphicsContext gc = can.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);

        if(laser.getHeading() == Heading.NORTH || laser.getHeading() == Heading.SOUTH)
        {
            gc.strokeLine(35,0,35,75);
        } else
            {
                gc.strokeLine(0,35,75,35);
            }
        this.getChildren().add(can);
    }

    public void updateConveyorBelt(ConveyorBelt conveyorBelt){
        Canvas can = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        final Image conveyor = new Image ("Visuals/Conveyorbelt.PNG");

        GraphicsContext gc = can.getGraphicsContext2D();
        gc.setStroke(Color.GREY);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);

        if(conveyorBelt.getHeading() == Heading.NORTH) {
            ImageView iv1 = new ImageView(conveyor);
            iv1.setRotate(90);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            Image rotatedImage1 = iv1.snapshot(params,null);
            gc.drawImage(rotatedImage1,7,0,57,75);
        }
        else if(conveyorBelt.getHeading() == Heading.SOUTH) {
            ImageView iv2 = new ImageView(conveyor);
            iv2.setRotate(270);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            Image rotatedImage2 = iv2.snapshot(params,null);
            gc.drawImage(rotatedImage2,7,0,57,75);
        }
        else if(conveyorBelt.getHeading() == Heading.EAST) {
            ImageView iv3 = new ImageView(conveyor);
            iv3.setRotate(180);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            Image rotatedImage3 = iv3.snapshot(params,null);
            gc.drawImage(rotatedImage3,0,7,75,55);
        }
        else if(conveyorBelt.getHeading() == Heading.WEST) {
            gc.drawImage(conveyor,0,7,75,55);
        }

        this.getChildren().add(can);
    }

    public void updateGear(Gear gear){

    }

}
