package Game;

import biuoop.DrawSurface;
import Sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * The game.SpriteCollection class manages a collection of sprites.Sprite objects.
 * It provides methods to add sprites, notify them of time passage,
 * and draw them on a given DrawSurface.
 */
public class SpriteCollection {
    private List<Sprite> sprites = new ArrayList<>();

    /**
     * Adds a sprite to the collection.
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Removes a sprite from the collection.
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * Notifies all sprites in the collection that time has passed.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite sprite: spritesCopy) {
            sprite.timePassed();
        }
    }

    /**
     * Draws all sprites in the collection on the given DrawSurface.
     * @param d the DrawSurface to draw the sprites on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite: this.sprites) {
            sprite.drawOn(d);
        }
    }
}
