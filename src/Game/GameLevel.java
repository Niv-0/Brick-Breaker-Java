package Game;

import Animation.Animation;
import Animation.AnimationRunner;
import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;
import Levels.LevelInformation;
import Listeners.BallRemover;
import Listeners.BlockRemover;
import Listeners.ScoreTrackingListener;
import Screens.PauseScreen;
import Sprites.Ball;
import Sprites.Block;
import Sprites.GameStatusBar;
import Sprites.Paddle;
import Utils.Counter;
import Utils.MouseTracker;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * GameLevel manages the active gameplay for a single level, implementing the Animation interface.
 * Implements death zone ball loss, in-game pause on ESC, and custom level parameters.
 */
public class GameLevel extends Game implements Animation {
    private LevelInformation levelInfo;
    private KeyboardSensor keyboard;
    private AnimationRunner runner;
    private MouseTracker mouse;
    private Counter score;
    private Counter blocksCounter;
    private Counter ballCounter;
    private List<Ball> activeBalls;
    private BallRemover ballRemover;
    private boolean running;
    private boolean quitRequested;
    private boolean retryRequested;
    private boolean levelSuccess;
    private boolean escPressedLastFrame;

    /**
     * Constructs a GameLevel.
     * @param levelInfo level configuration
     * @param keyboard keyboard sensor
     * @param runner animation runner
     * @param mouse mouse tracker
     * @param score shared score counter
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor keyboard,
                     AnimationRunner runner, MouseTracker mouse, Counter score) {
        super(false);
        this.levelInfo = levelInfo;
        this.keyboard = keyboard;
        this.runner = runner;
        this.mouse = mouse;
        this.score = score;
        this.blocksCounter = new Counter(0);
        this.ballCounter = new Counter(0);
        this.activeBalls = new ArrayList<>();
        this.running = true;
        this.quitRequested = false;
        this.retryRequested = false;
        this.levelSuccess = false;
        this.escPressedLastFrame = true; // Wait for ESC release before accepting pause
    }

    @Override
    public void initialize() {
        // 1. Level background sprite
        this.addSprite(this.levelInfo.getBackground());

        // 2. Boundaries: Left, Top, Right (physical bottom wall is removed!)
        initBorders();

        // 3. Off-screen death zone collidable
        initDeathZone();

        // 4. Status indicator bar
        GameStatusBar statusBar = new GameStatusBar(this.score, this.ballCounter, this.levelInfo.levelName());
        statusBar.addToGame(this);

        // 5. Blocks layout from level configuration
        initBlocks();

        // 6. Paddle
        initPaddle();

        // 7. Balls
        initBalls();
    }

    private void initBorders() {
        Color borderColor = Color.GRAY;
        int borderThickness = Game.BORDER_SIZE;
        int screenWidth = Game.SCREEN_WIDTH;
        int screenHeight = Game.SCREEN_HEIGHT;
        int statusBarHeight = Game.STATUS_BAR_HEIGHT;

        // Top border (below status bar)
        Block topBorder = new Block(new Rectangle(0, statusBarHeight, screenWidth, borderThickness), borderColor);
        // Left border
        Block leftBorder = new Block(new Rectangle(0, statusBarHeight, borderThickness, screenHeight), borderColor);
        // Right border
        Block rightBorder = new Block(new Rectangle(screenWidth - borderThickness, statusBarHeight, borderThickness, screenHeight), borderColor);

        topBorder.addToGame(this);
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);
        // Note: NO bottom physical border block inside visible screen!
    }

    private void initDeathZone() {
        this.ballRemover = new BallRemover(this, this.ballCounter);
        // Death block placed below the visible screen
        Block deathZone = new Block(new Rectangle(-50, Game.SCREEN_HEIGHT + 10, Game.SCREEN_WIDTH + 100, 50), Color.DARK_GRAY);
        deathZone.addHitListener(this.ballRemover);
        this.addCollidable(deathZone);
        // Not added as a sprite, so it remains completely invisible off-screen
    }

    private void initBlocks() {
        BlockRemover blockRemover = new BlockRemover(this, this.blocksCounter);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);

        List<Block> blocks = this.levelInfo.blocks();
        for (Block b : blocks) {
            b.addHitListener(blockRemover);
            b.addHitListener(scoreTrackingListener);
            b.addToGame(this);
            this.blocksCounter.increase(1);
        }
    }

    private void initPaddle() {
        int width = this.levelInfo.paddleWidth();
        int speed = this.levelInfo.paddleSpeed();
        int height = 18;
        int x = (Game.SCREEN_WIDTH - width) / 2;
        int y = 570;

        Paddle paddle = new Paddle(new Rectangle(x, y, width, height), this.keyboard, speed);
        paddle.addToGame(this);
    }

    private void initBalls() {
        int n = this.levelInfo.numberOfBalls();
        List<Velocity> velocities = this.levelInfo.initialBallVelocities();
        int paddleY = 570;
        int radius = 6;

        // Choose high-contrast ball color against the level background
        Color ballColor = Color.WHITE;
        if (this.levelInfo.levelName().contains("Wide")) {
            ballColor = new Color(20, 50, 160); // Deep royal blue for bright meadow background
        } else if (this.levelInfo.levelName().contains("Direct")) {
            ballColor = Color.CYAN;
        }

        for (int i = 0; i < n; i++) {
            double xOffset = (n == 1) ? 0 : (i - (n - 1) / 2.0) * 30;
            double yOffset = (n > 1) ? ((i % 2 == 0) ? 0 : -8) : 0;
            Ball ball = new Ball(new Point(400 + xOffset, paddleY - radius - 5 + yOffset), radius, ballColor, this.getEnvironment());
            Velocity v = velocities.get(i % velocities.size());
            ball.setVelocity(v);
            ball.addToGame(this);
            this.activeBalls.add(ball);
            this.ballCounter.increase(1);
        }
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Pause Screen check (ESC key or P key)
        boolean escPressed = this.keyboard.isPressed("\u001b")
                || this.keyboard.isPressed("esc")
                || this.keyboard.isPressed("p")
                || this.keyboard.isPressed("P");

        if (escPressed && !this.escPressedLastFrame) {
            PauseScreen pauseScreen = new PauseScreen(this.mouse);
            this.runner.run(pauseScreen);
            if (pauseScreen.isRetryRequested()) {
                this.retryRequested = true;
                this.running = false;
                return;
            }
            if (pauseScreen.isQuitRequested()) {
                this.quitRequested = true;
                this.running = false;
                return;
            }
        }
        this.escPressedLastFrame = escPressed;

        // Render current game state
        this.getSprites().drawAllOn(d);
        this.getSprites().notifyAllTimePassed();

        // Death zone sweep for balls that fell below bottom edge (y >= 600)
        List<Ball> remaining = new ArrayList<>();
        for (Ball ball : this.activeBalls) {
            if (ball.getY() >= Game.SCREEN_HEIGHT) {
                this.ballRemover.removeBall(ball);
            }
            if (!ball.isRemoved()) {
                remaining.add(ball);
            }
        }
        this.activeBalls = remaining;
        // Strictly synchronize the level's ball counter to the current active ball count
        this.ballCounter.setValue(this.activeBalls.size());

        // Win condition: all blocks destroyed
        if (this.blocksCounter.getValue() <= 0) {
            this.score.increase(100);
            this.levelSuccess = true;
            this.running = false;
        }

        // Loss condition: all balls fell off screen
        if (this.ballCounter.getValue() <= 0) {
            this.levelSuccess = false;
            this.running = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    public boolean isRetryRequested() {
        return this.retryRequested;
    }

    public boolean isQuitRequested() {
        return this.quitRequested;
    }

    public boolean isLevelSuccess() {
        return this.levelSuccess;
    }

    @Override
    public void run() {
        this.runner.run(this);
    }
}
