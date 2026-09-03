package Listeners;

import Game.Game;
import Sprites.Ball;
import Sprites.Block;
import Utils.Counter;

/**
 * listeners.BlockRemover is a listeners.HitListener that removes blocks from the game when they are hit.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructor for listeners.BlockRemover.
     * @param game the game instance
     * @param remainingBlocks the counter for remaining blocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }


    /**
     * Handles the hit event by removing the block from the game and updating the counter.
     * @param beingHit the object that was hit
     * @param hitter the object that caused the hit
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.decreaseHitPoints();
        if (beingHit.getHitPoints() <= 0) {
            beingHit.removeFromGame(this.game);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
        }
    }
}
