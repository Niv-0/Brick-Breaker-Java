package Sprites;

import Game.CollisionInfo;
import Game.Game;
import Game.GameEnvironment;
import biuoop.DrawSurface;
import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;

import java.awt.Color;

/**
 * Represents a ball in a 2D space.
 * A ball has a center point, radius, color, velocity and environment.
 */
public class Ball implements Sprite {
    private Point center;
    private int r;
    private Color color;
    private Velocity v;
    private GameEnvironment environment;
    private boolean isRemoved = false;

    /**
     * Checks if this ball has already been marked as removed from play.
     * @return true if removed, false otherwise
     */
    public boolean isRemoved() {
        return this.isRemoved;
    }

    /**
     * Sets the removed status of this ball.
     * @param removed true if removed
     */
    public void setRemoved(boolean removed) {
        this.isRemoved = removed;
    }

    /**
     * Constructs a new ball with a given center, radius, color and an environment.
     * @param center the ball's center point
     * @param r the ball's radius
     * @param color the ball's color
     * @param environment the ball's environment
     */
    public Ball(Point center, int r, Color color, GameEnvironment environment) {
        this.center = new Point(center.getX(), center.getY());
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
        this.environment = environment;
    }

    /**
     * Copy constructor.
     * Constructs a new ball that is a deep copy of another ball.
     * @param ball the ball to copy.
     */
    public Ball(Ball ball) {
        this.center = new Point(ball.center.getX(), ball.center.getY());
        this.r = ball.r;
        this.color = ball.color;
        this.v = new Velocity(ball.v.getDx(), ball.v.getDy());
        this.environment = ball.environment;
    }

    /**
     * Sets the ball's velocity.
     * @param v the new velocity
     */
    public void setVelocity(Velocity v) {
        this.v = new Velocity(v.getDx(), v.getDy());
    }

    /**
     * Sets the ball's velocity using dx and dy components.
     * @param dx the horizontal velocity
     * @param dy the vertical velocity
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * Retrieve the ball's current velocity.
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * Moves the ball one step according to its velocity, handling collisions with the environment.
     * If a collision is detected, the ball's position and velocity are adjusted accordingly.
     */
    public void moveOneStep() {
        Point nextCenter = this.getVelocity().applyToPoint(this.center);
        Line trajectory = new Line(this.center, nextCenter);
        trajectory.expand(r);
        CollisionInfo collisionInfo = this.environment.getClosestCollision(trajectory);
        if (collisionInfo == null) {
            this.center = nextCenter;
            return;
        }
        this.center = getAdjustedPosition(collisionInfo.collisionPoint(), collisionInfo.collisionObject());
        this.v = collisionInfo.collisionObject().hit(this, collisionInfo.collisionPoint(), this.v);
    }

    /**
     * Calculates the adjusted position of the ball after a collision to prevent overlapping.
     * @param collisionPoint the point where the collision occurred
     * @param collisionObject the object that the ball collided with
     * @return a new geometry.Point representing the adjusted position of the ball
     */
    private Point getAdjustedPosition(Point collisionPoint, Collidable collisionObject) {
        Rectangle rect = collisionObject.getCollisionRectangle();
        double epsilon = this.r + Point.COMPARISON_THRESHOLD;
        double nextX = this.center.getX();
        double nextY = this.center.getY();

        //check for horizontal adjustment
        if (Point.doubleEquals(collisionPoint.getY(), rect.getY())) {
            nextY = collisionPoint.getY() - epsilon;
        } else if (Point.doubleEquals(collisionPoint.getY(), rect.getY() + rect.getHeight())) {
            nextY = collisionPoint.getY() + epsilon;
        }

        //check for vertical adjustment
        if (Point.doubleEquals(collisionPoint.getX(), rect.getX())) {
            nextX = collisionPoint.getX() - epsilon;
        } else if (Point.doubleEquals(collisionPoint.getX(), rect.getX() + rect.getWidth())) {
            nextX = collisionPoint.getX() + epsilon;
        }
        return new Point(nextX, nextY);
    }


    /**
     * Retrieves the x coordinate of the ball's center point.
     * @return an integer representing the x coordinate of the center
     */
    public int getX() {
        return (int) this.center.getX();
    }
    /**
     * Retrieves the y coordinate of the ball's center point.
     * @return an integer representing the y coordinate of the center
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Retrieves the ball's radius.
     * @return an integer representing the ball's radius
     */
    public int getSize() {
        return this.r;
    }


    /**
     * Sets the ball's color.
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Retrieves the ball's color.
     * @return the Color of the ball
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Notifies the ball that time has passed, prompting it to move one step.
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Drawing the ball on the given DrawSurface.
     * @param surface the DrawSurface to draw the ball on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
    }

    /**
     * Removes the ball from the given game.
     * @param g the game to remove the ball from
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }

    /**
     * Adds the ball to the given game as a sprite.
     * @param g the game to add the ball to
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

}
