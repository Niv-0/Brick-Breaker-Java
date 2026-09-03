package Game;

import Sprites.ScoreIndicator;
import Sprites.Ball;
import Sprites.Block;
import Sprites.Collidable;
import Sprites.Paddle;
import Sprites.Sprite;
import Utils.ColorUtils;
import Utils.Counter;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;
import Listeners.BallRemover;
import Listeners.BlockRemover;
import Listeners.ScoreTrackingListener;

import java.awt.Color;

/**
 * The game.Game class manages the game state, including sprites and environment,
 * and handles the game loop for rendering and updating the game.
 */
public class Game {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final int BORDER_SIZE = 10;
    public static final int STATUS_BAR_HEIGHT = 20;

    protected SpriteCollection sprites;
    protected GameEnvironment environment;
    protected GUI gui;
    protected Counter blocksCounter;
    protected Counter ballCounter;
    protected Counter score;

    /**
     * Constructs a new game.Game instance with initialized GUI, sprite collection, and game environment.
     */
    public Game() {
        this(true);
    }

    /**
     * Constructs a Game instance with optional GUI initialization.
     * @param initGui true to initialize a new GUI, false otherwise
     */
    protected Game(boolean initGui) {
        if (initGui) {
            this.gui = new GUI("Breakout Game", 800, 600);
        }
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.blocksCounter = new Counter(0);
        this.ballCounter = new Counter(0);
        this.score = new Counter(0);
    }

    /**
     * Retrieves the sprite collection.
     * @return the SpriteCollection
     */
    public SpriteCollection getSprites() {
        return this.sprites;
    }

    /**
     * Retrieves the game environment.
     * @return the GameEnvironment
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * Retrieves the GUI instance.
     * @return the GUI
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * Retrieves the score counter.
     * @return the score Counter
     */
    public Counter getScore() {
        return this.score;
    }
    /**
     * Adds a collidable object to the game environment.
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the game's sprite collection.
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Removes a collidable object from the game environment.
     * @param c the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes a sprite from the game's sprite collection.
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initializes the game borders by creating blocks around the edges of the game window,
     * and adding them to the game.
     */
    private void initBorders() {
        int size = 10;
        int width = gui.getDrawSurface().getWidth();
        int height = gui.getDrawSurface().getHeight();
        Color color = Color.GRAY;
        Block topBorder = new Block(new Rectangle(0, 20, width, size), color);
        Block leftBorder = new Block(new Rectangle(0, 20, size, height), color);
        Block rightBorder = new Block(new Rectangle(width - size, 20, size, height), color);
        topBorder.addToGame(this);
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);
    }

    /**
     * Initializes the blocks in the game by creating a grid of blocks,
     * assigning them random colors, and adding them to the game.
     */
    private void initBlocks() {
        double blockWidth = 45;
        double blockHeight = 20;
        double firstBlockX = gui.getDrawSurface().getWidth() - 10 - blockWidth;
        double firstBlockY = (double) gui.getDrawSurface().getHeight() / 3 - blockHeight;
        BlockRemover blockRemover = new BlockRemover(this, this.blocksCounter);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        int rows = 6;
        int cols = 7;
        for (int i = 0; i < rows; i++) {
            Color color = ColorUtils.getRandomColor();
            for (int j = 0; j < cols; j++) {
                Block b = new Block(new Rectangle(firstBlockX - blockWidth * j,
                        firstBlockY - blockHeight * i, blockWidth, blockHeight), color);
                b.addHitListener(blockRemover);
                b.addHitListener(scoreTrackingListener);
                b.addToGame(this);
                this.blocksCounter.increase(1);
            }
            cols++;
        }
    }
    /**
     * Initializes the paddle in the game by creating a paddle object
     * and adding it to the game.
     */
    private void initPaddle() {
        int width = 85;
        int height = 20;
        int x = gui.getDrawSurface().getWidth() / 2 - width / 2;
        int y = gui.getDrawSurface().getHeight() - height - 10;
        Paddle paddle = new Paddle(new Rectangle(x, y, width, height), gui.getKeyboardSensor());
        paddle.addToGame(this);
    }

    /**
     * Initializes the balls in the game by creating n balls
     * and adding them to the game.
     * @param n the number of balls to initialize
     */
    private void initBalls(int n) {
        int r = 8; //fixed radius
        int speed = 6; // fixed speed
        int angle = 320; // fixed angle
        double y = gui.getDrawSurface().getHeight() - 90; //fixed y
        double midX = (double) gui.getDrawSurface().getWidth() / (n + 1);
        for (int i = 1; i <= n; i++) {
            Ball ball = new Ball(new Point(midX * i, y), r, Color.BLACK, this.environment);
            ball.setVelocity(Velocity.fromAngleAndSpeed(angle, speed));
            ball.addToGame(this);
            this.ballCounter.increase(1);
        }
    }

    /**
     * Initializes the death border at the bottom of the game window,
     * which removes balls that collide with it.
     */
    private void initDeathBorder() {
        int width = gui.getDrawSurface().getWidth();
        int height = gui.getDrawSurface().getHeight();
        BallRemover ballRemover = new BallRemover(this, this.ballCounter);
        Color color = Color.GRAY;
        Block deathBorder = new Block(new Rectangle(-50, height + 10, width + 100, 50), color);
        deathBorder.addHitListener(ballRemover);
        this.addCollidable(deathBorder);
    }


    /**
     * Initializes the score indicator by creating a sprites.ScoreIndicator object
     * and adding it to the game.
     */
    private void initScoreIndicator() {
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        scoreIndicator.addToGame(this);
    }

    /**
     * Initializes the game by setting up balls, blocks, paddle, and borders.
     */
    public void initialize() {
        initBalls(3);
        initBlocks();
        initPaddle();
        initBorders();
        initDeathBorder();
        initScoreIndicator();

    }

    /**
     * Runs the main game loop, rendering sprites and updating the game state
     * until all blocks are destroyed or all balls are lost.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (this.blocksCounter.getValue() > 0 && this.ballCounter.getValue() > 0) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
        if (blocksCounter.getValue() == 0) {
            this.score.increase(100);
            System.out.println("You Win!\nYour score is: " + this.score.getValue());
        } else {
            System.out.println("Game Over.\nYour score is: " + this.score.getValue());
        }
        this.gui.close();
    }
}
