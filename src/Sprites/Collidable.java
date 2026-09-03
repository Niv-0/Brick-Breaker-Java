package Sprites;

import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;

/**
 * The sprites.Collidable interface represents objects that can be collided with in a 2D space.
 * It provides methods to get the collision rectangle and to handle collisions.
 */
public interface Collidable {
    /**
     * Returns the collision rectangle of the collidable object.
     * @return the geometry.Rectangle representing the collision area
     */
    Rectangle getCollisionRectangle();
    /**
     * Calculates the new velocity of an object after a collision with the collidable.
     * @param collisionPoint the point where the collision occurred
     * @param currentVelocity the current velocity of the object before the collision
     * @param hitter the ball that hit the collidable
     * @return the new geometry.Velocity after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
