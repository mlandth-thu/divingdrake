package de.mlandth.divingdrake;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Drake {

    private Rectangle drake;

    /**
     * Describes the movement the drake can swim in y-axis
     */
    private double moveDeltaX;
    private double moveDeltaY;

    public Drake(Rectangle drake, double moveDeltaX, double moveDeltaY) {
        this.drake = drake;
        this.moveDeltaX = moveDeltaX;
        this.moveDeltaY = moveDeltaY;
    }

    /**
     * Let the drake swim
     *
     * Only on y axis for now
     */
    public void swim(int dir) {
        double newY = -moveDeltaY * dir;
        //check if the drake wants to swim too far north (layout is fixed, drake changes)
        if (drake.getLayoutY() + drake.getY() <= moveDeltaY) {
            newY = -(drake.getLayoutY() + drake.getY());
        }
        //check if the drake wants to swim to far south

        moveDrake(0, newY);
    }

    /**
     * move the drake on the scene
     * @param x sets the position of the drake in the x axis
     * @param y sets the position of the drake in the y axis
     */
    public void moveDrake(double x, double y) {
        drake.setX(drake.getX() + x);
        drake.setY(drake.getY() + y);
    }

    /**
     * Checks if the Drake collides with an obstacle and so is dead.
     * @param obstacles
     * @param pane
     * @return
     * TODO this has to go somewhere else
     */
    public boolean checkDeath(ArrayList<Rectangle> obstacles, AnchorPane pane) {
        double posY = drake.getLayoutY() + drake.getY();

        CollisionController cc = new CollisionController();

        if (cc.checkForCollision(obstacles, drake)) return true;

        return posY >= pane.getHeight();
    }
}
