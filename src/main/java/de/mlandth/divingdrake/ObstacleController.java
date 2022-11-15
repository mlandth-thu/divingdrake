package de.mlandth.divingdrake;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ObstacleController {

    private AnchorPane obsPane;
    private double pHeight;
    private double pWidth;

    //TODO tweak moving values
    /**
     * This value defines how much an obstacle is moving in one step.
     * Default: 1 px per step. | Negative because moving to the left
     */
    private double dist = 1.0;
    private final double distFactor = 0.05;
    private final double minDist = 5.0;
    private final double orgDist = 1.0;

    Random r = new Random();

    public ObstacleController(AnchorPane obsPane, double paneHeight, double paneWidth, double moveDistance) {
        this.obsPane = obsPane;
        this.pHeight = paneHeight;
        this.pWidth = paneWidth;
        this.dist = moveDistance;
    }

    //TODO Tweak values
    /**
     * Creates a log
     * @param obsX
     * @return
     */
    public ArrayList<Rectangle> createLog(double obsX) {

        //calculate vertical gap between logs
        //drake: 70 + 60 = 130
        int low = 170;
        int high = 240;
        //randomized vertical gap
        double gap = r.nextInt(high-low)+low;

        //values for calculating log position
        //randomized log with
        int logWidth = r.nextInt(70-50)+50;
        double offset = pHeight / 2;

        //calculate randomized positions for new log
        int logNorthY = r.nextInt((int)(offset)) + (100);
        int logSouthY = (int) (pHeight - gap - logNorthY);

        //create new log
        Rectangle logNorth = new Rectangle(obsX, 0, logWidth, logNorthY);
        Rectangle logSouth = new Rectangle(obsX, logNorthY + gap, logWidth, logSouthY);

        //set randomized brown color of log
        int logR = r.nextInt(110-90)+90;
        int logG = r.nextInt(70-50)+50;
        int logB = r.nextInt(40-20)+20;

        Color logColor = Color.rgb(logR, logG, logB, 1.0);
        logNorth.setFill(logColor);
        logSouth.setFill(logColor);

        //Adding logs to the view
        obsPane.getChildren().addAll(logNorth, logSouth);
        //Adding logs for collision
        return new ArrayList<>(Arrays.asList(logNorth,logSouth));
    }

    public ArrayList<Rectangle> createRock(double obsX) {
        //dimension of rock
        double rockWidth = 90.0;
        double rockHeight = 63.0;

        //position of rock
        double rockX = obsX;
        double rockY = r.nextInt(550 - 350) +350;

        //rock view
        Rectangle rock = new Rectangle(rockX, rockY, rockWidth, rockHeight);

        int rockRGB = r.nextInt(169-105)+105;
        Color rockColor = Color.rgb(rockRGB, rockRGB, rockRGB, 1.0);
        rock.setFill(rockColor);

        //Create rock view
        Image rockImg = new Image(getClass().getResource("images/rock.png").toExternalForm());
        rock.setFill(new ImagePattern(rockImg));

        //Adding rock to view
        obsPane.getChildren().addAll(rock);
        //Adding rock for collision
        return new ArrayList<>(Arrays.asList(rock));
    }

    /**
     * Updates the position of obstacles
     * @param obstacles
     */
    public void moveObstacles(ArrayList<Rectangle> obstacles){

        ArrayList<Rectangle> out = new ArrayList<>();

        for (Rectangle rectangle: obstacles) {
            moveRectX(rectangle, dist);

            if(rectangle.getX() <= -rectangle.getWidth()){
                out.add(rectangle);
            }
        }
        obstacles.removeAll(out);
        obsPane.getChildren().removeAll(out);
    }

    /**
     * Moves an obstacle
     * @param rect obstacle
     * @param dist distance to move
     */
    private void moveRectX(Rectangle rect, double dist){
        rect.setX(rect.getX() - dist);
    }

    /**
     * Increases the distance when updating the position of an obstacle
     */
    public void incMovingDistance() {
        dist += distFactor;
        //ugly debug
        //System.out.println("moving distance: "+dist+"[px]");
    }

    /**
     * Resets the current moving distance to the original distance
     */
    public void resetMovingDistance() {
        dist = orgDist;
    }
}
