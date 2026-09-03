package Listeners;

import Sprites.Ball;
import Sprites.Block;
import Utils.Counter;

/**
 * listeners.ScoreTrackingListener class that implements listeners.HitListener to track the score.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructor for listeners.ScoreTrackingListener.
     * @param scoreCounter the counter for the current score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * This method is called whenever a hit event occurs.
     * @param beingHit the object that was hit
     * @param hitter the object that caused the hit
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }
}
