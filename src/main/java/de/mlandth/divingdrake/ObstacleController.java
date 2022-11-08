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

    Random r = new Random();

    public ObstacleController(AnchorPane obsPane, double paneHeight, double paneWidth) {
        this.obsPane = obsPane;
        this.pHeight = paneHeight;
        this.pWidth = paneWidth;
    }

    //TODO change values
    public ArrayList<Rectangle> createLog() {

        //calculate vertical gap between logs
        int low = 150;
        int high = 220;
        //randomized vertical gap
        double gap = r.nextInt(high-low)+low;
        double obsX = pWidth;

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
        double dist = -0.75;

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
        rect.setX(rect.getX() + dist);
    }
}