package de.mlandth.divingdrake;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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

    //TODO Change values
    public ArrayList<Rectangle> createLog(double obsX) {

        //calculate vertical gap between logs
        int low = 170;
        int high = 220;
        //randomized vertical gap
        double gap = r.nextInt(high-low)+low;

        //values for calculating log position
        //randomized log with
        int obsWidth = r.nextInt(70-50)+50;
        double offset = pHeight / 2;

        //calculate randomized positions for new log
        int logNorth = r.nextInt((int)(offset)) + (100);
        int logSouth = (int) (pHeight - gap - logNorth);

        //create new log
        Rectangle obsNorth = new Rectangle(obsX, 0, obsWidth, logNorth);
        Rectangle obsSouth = new Rectangle(obsX, logNorth + gap, obsWidth, logSouth);

        //set randomized brown color of log
        int red = r.nextInt(110-90)+90;
        int green = r.nextInt(70-50)+50;
        int blue = r.nextInt(40-20)+20;

        Color logColor = Color.rgb(red, green, blue, 1.0);
        obsNorth.setFill(logColor);
        obsSouth.setFill(logColor);

        //Adding logs to the view
        obsPane.getChildren().addAll(obsNorth, obsSouth);
        //Adding logs for collision
        return new ArrayList<>(Arrays.asList(obsNorth,obsSouth));
    }

    //TODO change values
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

    private void moveRectX(Rectangle rect, double dist){
        rect.setX(rect.getX() - dist);
    }

    public void incMovingDistance() {
        dist += distFactor;
        //ugly debug
        //System.out.println("moving distance: "+dist+"[px]");
    }

    public void resetMovingDistance() {
        dist = orgDist;
    }
}
