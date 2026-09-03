package Sprites;

import Game.Game;
import Utils.Counter;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Top banner sprite displaying score, current level name, and active ball count.
 */
public class GameStatusBar implements Sprite {
    private Counter score;
    private Counter ballCounter;
    private String levelName;

    /**
     * Constructs a GameStatusBar.
     * @param score the score counter
     * @param ballCounter the active balls counter
     * @param levelName the current level name
     */
    public GameStatusBar(Counter score, Counter ballCounter, String levelName) {
        this.score = score;
        this.ballCounter = ballCounter;
        this.levelName = levelName;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // Status bar background
        d.setColor(new Color(235, 238, 245));
        d.fillRectangle(0, 0, d.getWidth(), 20);

        // Bottom border line
        d.setColor(new Color(180, 185, 200));
        d.drawLine(0, 20, d.getWidth(), 20);

        // Text indicators
        d.setColor(new Color(30, 40, 60));
        d.drawText(60, 15, "Score: " + this.score.getValue(), 13);
        d.drawText(350, 15, "Level: " + this.levelName, 13);
        d.drawText(640, 15, "Balls: " + this.ballCounter.getValue(), 13);
    }

    @Override
    public void timePassed() {
        // Static UI component
    }

    public void addToGame(Game g) {
        g.addSprite(this);
    }

    @Override
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }
}
