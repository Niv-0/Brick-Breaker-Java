package Levels;

import Geometry.Velocity;
import Sprites.Block;
import Sprites.Sprite;

import java.util.List;

/**
 * Interface defining the configuration and metadata for a game level.
 */
public interface LevelInformation {
    /**
     * Number of balls in this level.
     * @return count of balls
     */
    int numberOfBalls();

    /**
     * Initial velocities for each ball.
     * @return list of velocities
     */
    List<Velocity> initialBallVelocities();

    /**
     * Paddle movement speed.
     * @return paddle speed
     */
    int paddleSpeed();

    /**
     * Paddle width in pixels.
     * @return paddle width
     */
    int paddleWidth();

    /**
     * Display name of the level.
     * @return level name
     */
    String levelName();

    /**
     * Sprite representing the level's visual background.
     * @return background sprite
     */
    Sprite getBackground();

    /**
     * Blocks that make up this level.
     * @return list of blocks
     */
    List<Block> blocks();

    /**
     * Number of blocks that need to be cleared to win the level.
     * @return block count
     */
    int numberOfBlocksToRemove();
}
