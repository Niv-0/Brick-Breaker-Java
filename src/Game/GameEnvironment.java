package Game;

import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Sprites.Collidable;

import java.util.ArrayList;
import java.util.List;

/**
 * The game.GameEnvironment class represents the environment in which the game takes place.
 * It manages a collection of collidable objects and provides functionality to determine
 * collisions along a given trajectory.
 */
public class GameEnvironment {
    private List<Collidable> collidables = new ArrayList<>();

    /**
     * Adds a collidable object to the game environment.
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }
    /**
     * Removes a collidable object from the game environment.
     * @param c the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Finds the closest collision between a collidable and a given trajectory.
     * @param trajectory the line representing the trajectory
     * @return a game.CollisionInfo object representing the closest collision, or null if no collision occurs
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Collidable closestCollision = null;
        Point closestCollisionPoint = null;
        double shortestDistance = Double.MAX_VALUE;

        for (Collidable collidable : this.collidables) {
            Point closestInter = trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle());
            if (closestInter == null && collidable.getCollisionRectangle().contains(trajectory.start())) {
                //if the start point is inside the rectangle and there is no intersection point the ball stuck inside.
                Rectangle rect = collidable.getCollisionRectangle();
                //put the collision point directly above the ball, on the top edge of the block (push him out)
                closestInter = new Point(trajectory.start().getX(), rect.getY());
            }
            if (closestInter != null) {
                double currentDistance = trajectory.start().distance(closestInter);
                if (currentDistance < shortestDistance) {
                    closestCollision = collidable;
                    closestCollisionPoint = closestInter;
                    shortestDistance = currentDistance;
                }
            }
        }

        if (closestCollision == null) {
            return null;
        }
        return new CollisionInfo(closestCollisionPoint, closestCollision);
    }

}
