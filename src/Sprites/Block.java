package Sprites;

import Game.Game;
import Listeners.HitListener;
import Listeners.HitNotifier;
import biuoop.DrawSurface;
import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * sprites.Block class represents a block in the game that can be collided with and drawn on the screen.
 * It implements the sprites.Collidable, sprites.Sprite, and listeners.HitNotifier interfaces.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Color color;
    private Rectangle block;
    private List<HitListener> hitListeners = new ArrayList<>();
    private int hitPoints;

    /**
     * Constructs a sprites.Block with the specified rectangle and color (default 1 hit point).
     * @param rec the rectangle defining the block's position and size
     * @param color the color of the block
     */
    public Block(Rectangle rec, Color color) {
        this(rec, color, 1);
    }

    /**
     * Constructs a sprites.Block with the specified rectangle, color, and hit points.
     * @param rec the rectangle defining the block's position and size
     * @param color the color of the block
     * @param hitPoints the durability / hit count of the block
     */
    public Block(Rectangle rec, Color color, int hitPoints) {
        this.block = new Rectangle(rec);
        this.color = color;
        this.hitPoints = Math.max(1, hitPoints);
    }

    /**
     * Retrieves the remaining hit points of the block.
     * @return the current hit points
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Sets the remaining hit points of the block.
     * @param hitPoints the hit points to set
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * Decreases the block's hit points by 1.
     */
    public void decreaseHitPoints() {
        if (this.hitPoints > 0) {
            this.hitPoints--;
        }
    }

    /**
     * Checks if the color of the given ball matches the block's color.
     * @param ball the ball to check
     * @return true if the colors match, false otherwise
     */
    public boolean ballColorMatch(Ball ball) {
        return this.color.equals(ball.getColor());
    }

    /**
     * Removes the block from the game as both a collidable and a sprite.
     * @param game the game from which the block is removed
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Returns the collision rectangle of the block.
     * @return the rectangle representing the block's collision area
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * Calculates the new velocity of an object after a collision with the block.
     * @param hitter the ball causing the collision
     * @param collisionPoint the point where the collision occurred
     * @param currentVelocity the current velocity of the object before the collision
     * @return the new velocity after the collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Line[] sides = this.block.getSides();
        int length = sides.length;
        for (int i = 0; i < length; i++) {
            if (sides[i].isPointInLine(collisionPoint)) {
                if (i % 2 == 0) { // horizontal hit (top or bottom side)
                    dy = -dy;
                } else { // vertical hit (left or right side)
                    dx = -dx;
                }
            }
        }
        if (!this.hitListeners.isEmpty()) {
            this.notifyHit(hitter);
            hitter.setColor(this.color);
        }
        return new Velocity(dx, dy);
    }

    private void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Draws the block on the given DrawSurface.
     * @param d the DrawSurface to draw the block on
     */
    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) block.getX();
        int y = (int) block.getY();
        int width = (int) block.getWidth();
        int height = (int) block.getHeight();

        d.setColor(this.color);
        d.fillRectangle(x, y, width, height);

        // Highlight top border
        d.setColor(Color.LIGHT_GRAY);
        d.drawLine(x, y, x + width, y);

        // Outline
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, width, height);

        // Render hit points if durability > 1
        if (this.hitPoints > 1) {
            d.setColor(Color.WHITE);
            d.drawText(x + width / 2 - 4, y + height / 2 + 5, String.valueOf(this.hitPoints), 12);
        }
    }

    /**
     * Notifies the block that time has passed.
     */
    @Override
    public void timePassed() {
        // Static block, no time passage logic needed
    }

    /**
     * Adds the block to the game as both a sprite and a collidable object.
     * @param g the game to which the block is added
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}
