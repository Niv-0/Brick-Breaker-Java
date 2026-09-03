package Levels;

import Geometry.Rectangle;
import Geometry.Velocity;
import Sprites.Block;
import Sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 5: "Breakout Inferno" - Four fast balls, narrow paddle, and a molten fortress layout.
 */
public class Level5 implements LevelInformation {
    private int blockCount = 0;

    @Override
    public int numberOfBalls() {
        return 4;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        velocities.add(Velocity.fromAngleAndSpeed(-46, 7.8));
        velocities.add(Velocity.fromAngleAndSpeed(-12, 8.8));
        velocities.add(Velocity.fromAngleAndSpeed(22, 8.2));
        velocities.add(Velocity.fromAngleAndSpeed(54, 7.4));
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 11;
    }

    @Override
    public int paddleWidth() {
        return 75;
    }

    @Override
    public String levelName() {
        return "Breakout Inferno";
    }

    @Override
    public Sprite getBackground() {
        return new LevelBackground(5);
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        this.blockCount = 0;

        Color crimsonShield = new Color(180, 25, 25);
        Color moltenOrange = new Color(240, 90, 20);
        Color goldenCore = new Color(255, 190, 30);

        int blockWidth = 50;
        int blockHeight = 22;

        // Outer fortress shield (row 1 and sides)
        int startX = 75;
        int startY = 100;
        int cols = 13;

        // Top shield
        for (int c = 0; c < cols; c++) {
            blocks.add(new Block(new Rectangle(startX + c * blockWidth, startY, blockWidth, blockHeight), crimsonShield, 3));
            this.blockCount++;
        }

        // Secondary layer
        for (int c = 1; c < cols - 1; c++) {
            blocks.add(new Block(new Rectangle(startX + c * blockWidth, startY + blockHeight, blockWidth, blockHeight), moltenOrange, 2));
            this.blockCount++;
        }

        // Core cluster (rows 3 and 4)
        for (int r = 2; r < 4; r++) {
            for (int c = 2; c < cols - 2; c++) {
                Color col = (r == 2) ? moltenOrange : goldenCore;
                int dur = (r == 2) ? 2 : 1;
                blocks.add(new Block(new Rectangle(startX + c * blockWidth, startY + r * blockHeight, blockWidth, blockHeight), col, dur));
                this.blockCount++;
            }
        }

        // Center jewel
        for (int c = 4; c <= 8; c++) {
            blocks.add(new Block(new Rectangle(startX + c * blockWidth, startY + 4 * blockHeight, blockWidth, blockHeight), goldenCore, 1));
            this.blockCount++;
        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 47;
    }
}
