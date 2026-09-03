package Game;

import Geometry.Point;
import Sprites.Collidable;

/**
 * game.CollisionInfo class holds information about a collision event,
 * including the point of collision and the object involved in the collision.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Constructs a game.CollisionInfo object with the specified collision point and collidable object.
     * @param collisionPoint the point where the collision occurred
     * @param collisionObject the collidable object involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Returns the point of collision.
     * @return the collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }
    /**
     * Returns the collidable object involved in the collision.
     * @return the collidable object
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
