package Sprites;

import Utils.ColorUtils;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;
import Game.Game;

import java.awt.Color;

/**
 * sprites.Paddle class represents a paddle in the game that can be controlled by the player.
 * It implements the sprites.Sprite and sprites.Collidable interfaces.
 */
public class Paddle implements Sprite, Collidable {
    private KeyboardSensor keyboard;
    private Rectangle paddle;
    private Color color;
    private double speed = 7;
    private static int leftBorder = 10;
    private static int rightBorder = 790;

    /**
     * Constructs a new sprites.Paddle with the specified rectangle and keyboard sensor.
     * @param rec the rectangle defining the paddle's position and size
     * @param keyboard the keyboard sensor for controlling the paddle
     */
    public Paddle(Rectangle rec, KeyboardSensor keyboard) {
        this(rec, keyboard, 7);
    }

    /**
     * Constructs a new sprites.Paddle with the specified rectangle, keyboard sensor, and movement speed.
     * @param rec the rectangle defining the paddle's position and size
     * @param keyboard the keyboard sensor for controlling the paddle
     * @param speed the movement speed of the paddle
     */
    public Paddle(Rectangle rec, KeyboardSensor keyboard, double speed) {
        this.keyboard = keyboard;
        this.color = ColorUtils.getRandomColor();
        this.paddle = new Rectangle(rec);
        this.speed = speed;
    }

    /**
     * Moves the paddle to the left by its speed.
     * The paddle moves circularly - when it reaches the edge of the screen, it appears on the other side.
     */
    public void moveLeft() {
        if (this.paddle.getX() - speed >= leftBorder) {
            this.paddle = new Rectangle(this.paddle.getX() - speed, this.paddle.getY(),
                    this.paddle.getWidth(), this.paddle.getHeight());
        } else {
            this.paddle = new Rectangle(rightBorder - this.paddle.getWidth(), this.paddle.getY(),
                    this.paddle.getWidth(), this.paddle.getHeight());
        }
    }

    /**
     * Moves the paddle to the right by its speed.
     * The paddle moves circularly - when it reaches the edge of the screen, it appears on the other side.
     */
    public void moveRight() {
        if (this.paddle.getX() + this.paddle.getWidth() + speed <= rightBorder) {
            this.paddle = new Rectangle(this.paddle.getX() + speed, this.paddle.getY(),
                    this.paddle.getWidth(), this.paddle.getHeight());
        } else {
            this.paddle = new Rectangle(leftBorder, this.paddle.getY(),
                    this.paddle.getWidth(), this.paddle.getHeight());
        }
    }

    /**
     * Notifies the paddle that time has passed.
     * Moves the paddle left or right based on keyboard input.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Returns the collision rectangle of the paddle.
     * @return the rectangle representing the paddle's collision area
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    /**
     * Calculates the new velocity of an object after a collision with the paddle.
     * The paddle is divided into 5 sections, each affecting the ball's angle differently.
     * @param collisionPoint the point where the collision occurred
     * @param currentVelocity the current velocity of the object before the collision
     * @return the new velocity after the collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        int split = 5; //the number of splits
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Line[] sides = this.paddle.getSides();
        int length = sides.length;
        for (int i = 0; i < length; i++) {
            if (sides[i].isPointInLine(collisionPoint)) {
                if (i == 0) { //if hit the paddle from the top.
                    int section = sides[i].whichSection(collisionPoint, split);
                    if (section != 3) { //if it didn't hit the 3rd section
                        double speed = currentVelocity.getSpeed();
                        return Velocity.fromAngleAndSpeed(270 + 30 * section, speed);
                    }
                }
                if (i % 2 == 0) { // if horizontal hit happened.
                    dy = -dy;
                } else { // if vertical hit happened.
                    dx = -dx;
                }
            }
        }
        return new Velocity(dx, dy);
    }

    /** Adds the paddle to the game as both a collidable and a sprite.
     * @param g the game to which the paddle is added
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
    @Override
    public void removeFromGame(Game g) {
        return;
    }

    /** Draws the paddle on the given DrawSurface.
     * @param d the DrawSurface to draw the paddle on
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.paddle.getX();
        int y = (int) this.paddle.getY();
        int width = (int) this.paddle.getWidth();
        int height = (int) this.paddle.getHeight();
        d.setColor(this.color);
        d.fillRectangle(x, y, width, height);
        d.setColor(Color.black);
        d.drawRectangle(x, y, width, height);
    }
}
