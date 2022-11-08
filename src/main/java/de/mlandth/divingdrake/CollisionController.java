package de.mlandth.divingdrake;

import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * This class is handling the collisions
 */
public class CollisionController {

    /**
     * Checks if the drake intersects with other objects
     * @param obstacles list of objects blocking the drake
     * @param drake
     * @return
     */
    public boolean checkForCollision(ArrayList<Rectangle> obstacles, Rectangle drake) {

        //Check for collision
        for (Rectangle o: obstacles) {
            if (o.getBoundsInParent().intersects(drake.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

}
