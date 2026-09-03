package Levels;

import Geometry.Rectangle;
import Geometry.Velocity;
import Sprites.Block;
import Sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 1: "Direct Hit" - Introductory level with a single target and wide paddle.
 */
public class Level1 implements LevelInformation {
    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        velocities.add(Velocity.fromAngleAndSpeed(0, 4.5));
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 7;
    }

    @Override
    public int paddleWidth() {
        return 160;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        return new LevelBackground(1);
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        // Target block directly in the center of the radar
        Block target = new Block(new Rectangle(385, 150, 30, 30), Color.RED, 1);
        blocks.add(target);
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }
}
