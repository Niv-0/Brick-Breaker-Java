package Sprites;

import Game.Game;
import Utils.Counter;
import biuoop.DrawSurface;

/**
 * sprites.ScoreIndicator is a sprite that displays the current score on the screen.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * Constructor for sprites.ScoreIndicator.
     * @param score the counter for the current score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * Adds the sprites.ScoreIndicator to the game as a sprite.
     * @param g the game instance
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * Removes the sprites.ScoreIndicator from the game.
     * This method is currently empty as the sprites.ScoreIndicator is not removed in this implementation.
     * @param g
     */
    public void removeFromGame(Game g) {
        return;
    }

    /**
     * Draws the score on the given DrawSurface.
     * @param d the DrawSurface to draw the sprite on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.BLACK);
        d.drawText(350, 15, "Score: " + this.score.getValue(), 15);
    }

    /**
     * Notifies the sprites.
     * ScoreIndicator that time has passed. This sprite does not change over time, so the method is empty.
     */
    public void timePassed() {
        return;
    }
}
