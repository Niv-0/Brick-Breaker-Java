package Listeners;

import Sprites.Ball;
import Sprites.Block;

/**
 * The listeners.HitListener interface defines a listener for hit events between game objects.
 */
public interface HitListener {
    /**
     * This method is called whenever a hit event occurs.
     * @param beingHit the object that was hit
     * @param hitter the object that caused the hit
     */
    void hitEvent(Block beingHit, Ball hitter);
}
