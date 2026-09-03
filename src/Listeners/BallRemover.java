package Listeners;

import Sprites.Ball;
import Sprites.Block;
import Utils.Counter;
import Game.Game;

/**
 * listeners.BallRemover class that implements listeners.HitListener to remove balls from the game.
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructor for listeners.BallRemover.
     * @param game the game instance
     * @param remainingBalls the counter for remaining balls
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Removes a ball from the game and updates the counter strictly once per ball.
     * @param ball the ball to be removed
     */
    public synchronized void removeBall(Ball ball) {
        if (!ball.isRemoved()) {
            ball.setRemoved(true);
            ball.removeFromGame(this.game);
            this.remainingBalls.decrease(1);
        }
    }

    /**
     * Handles the hit event by delegating to removeBall.
     * @param beingHit the object that was hit
     * @param hitter the object that caused the hit
     */
    @Override
    public synchronized void hitEvent(Block beingHit, Ball hitter) {
        removeBall(hitter);
    }
}
