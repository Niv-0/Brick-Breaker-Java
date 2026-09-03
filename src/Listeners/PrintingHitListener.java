package Listeners;

import Sprites.Ball;
import Sprites.Block;

/**
 * A listeners.HitListener that prints a message to the console whenever a block is hit.
 */
public class PrintingHitListener implements HitListener {
    /**
     * Handles the hit event by printing a message to the console.
     * @param beingHit the object that was hit
     * @param hitter the object that caused the hit
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A block was hit at " + beingHit.getCollisionRectangle().getUpperLeft());
    }
}
