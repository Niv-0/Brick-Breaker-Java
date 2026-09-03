package Sprites;

import Game.Game;
import biuoop.DrawSurface;

/**
 * sprites.Sprite interface represents a drawable and updatable object in the game.
 */
public interface Sprite {

    /**
     * Removes this sprite from the given game.
     * @param g the game to remove the sprite from
     */
    void removeFromGame(Game g);
    /** Draws the sprite on the given DrawSurface.
     * @param d the DrawSurface to draw the sprite on
     */
    void drawOn(DrawSurface d);
    /**
     * Notifies the sprite that time has passed, prompting it to update its state.
     */
    void timePassed();
}
