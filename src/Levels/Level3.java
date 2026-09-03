package Levels;

import Geometry.Rectangle;
import Geometry.Velocity;
import Sprites.Block;
import Sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 3: "Brick Cascade" - Stepped pyramid layout with multi-hit durable bricks and dusk aesthetics.
 */
public class Level3 implements LevelInformation {
    private int blockCount = 0;

    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        velocities.add(Velocity.fromAngleAndSpeed(-22, 5.6));
        velocities.add(Velocity.fromAngleAndSpeed(38, 6.4));
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 8;
    }

    @Override
    public int paddleWidth() {
        return 130;
    }

    @Override
    public String levelName() {
        return "Brick Cascade";
    }

    @Override
    public Sprite getBackground() {
        return new LevelBackground(3);
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        Color[] rowColors = {
            new Color(160, 40, 90),
            new Color(190, 65, 75),
            new Color(215, 100, 60),
            new Color(230, 150, 50),
            new Color(240, 195, 60)
        };

        int blockWidth = 50;
        int blockHeight = 22;
        int rightBoundary = 780;
        int startY = 140;

        this.blockCount = 0;
        for (int r = 0; r < rowColors.length; r++) {
            int blocksInRow = 10 - r;
            int durability = (r < 2) ? 2 : 1;
            Color c = rowColors[r];

            for (int col = 0; col < blocksInRow; col++) {
                int x = rightBoundary - (col + 1) * blockWidth;
                int y = startY + r * blockHeight;
                blocks.add(new Block(new Rectangle(x, y, blockWidth, blockHeight), c, durability));
                this.blockCount++;
            }
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 40;
    }
}
