package Levels;

import Geometry.Rectangle;
import Geometry.Velocity;
import Sprites.Block;
import Sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 4: "Color Chaos" - Three fast balls, narrower paddle, and a dense matrix of neon blocks with high durability.
 */
public class Level4 implements LevelInformation {
    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        velocities.add(Velocity.fromAngleAndSpeed(-38, 6.5));
        velocities.add(Velocity.fromAngleAndSpeed(8, 7.4));
        velocities.add(Velocity.fromAngleAndSpeed(48, 6.0));
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 9;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Color Chaos";
    }

    @Override
    public Sprite getBackground() {
        return new LevelBackground(4);
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        Color[] colors = {
            new Color(255, 20, 140),  // Row 0: Neon Magenta (durability 3)
            new Color(0, 220, 255),    // Row 1: Electric Cyan (durability 2)
            new Color(60, 255, 80),    // Row 2: Neon Lime (durability 2)
            new Color(255, 230, 30)    // Row 3: Bright Yellow (durability 1)
        };
        int[] durabilities = {3, 2, 2, 1};

        int blockWidth = 50;
        int blockHeight = 22;
        int startX = 25;
        int startY = 120;
        int cols = 15;

        for (int r = 0; r < colors.length; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * blockWidth;
                int y = startY + r * blockHeight;
                blocks.add(new Block(new Rectangle(x, y, blockWidth, blockHeight), colors[r], durabilities[r]));
            }
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 60;
    }
}
