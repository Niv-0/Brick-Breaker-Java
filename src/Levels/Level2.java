package Levels;

import Geometry.Rectangle;
import Geometry.Velocity;
import Sprites.Block;
import Sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 2: "Wide & Easy" - Two balls, extra-wide paddle, and a rainbow row of blocks.
 */
public class Level2 implements LevelInformation {
    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        velocities.add(Velocity.fromAngleAndSpeed(-16, 4.6)); // Steeper and slower
        velocities.add(Velocity.fromAngleAndSpeed(32, 5.4));  // Wider and faster
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 6;
    }

    @Override
    public int paddleWidth() {
        return 220;
    }

    @Override
    public String levelName() {
        return "Wide & Easy";
    }

    @Override
    public Sprite getBackground() {
        return new LevelBackground(2);
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        Color[] rainbow = {
            new Color(230, 50, 50), new Color(240, 100, 40), new Color(250, 160, 30),
            new Color(250, 210, 40), new Color(180, 220, 50), new Color(100, 200, 70),
            new Color(50, 190, 140), new Color(40, 180, 200), new Color(50, 140, 230),
            new Color(80, 90, 240), new Color(130, 60, 230), new Color(180, 50, 210),
            new Color(220, 50, 180), new Color(240, 60, 130), new Color(250, 70, 90)
        };

        int blockWidth = 50;
        int blockHeight = 25;
        int startX = 25;
        int startY = 250;

        for (int i = 0; i < rainbow.length; i++) {
            blocks.add(new Block(new Rectangle(startX + i * blockWidth, startY, blockWidth, blockHeight), rainbow[i], 1));
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 15;
    }
}
