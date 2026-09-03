package Levels;

import Game.Game;
import Sprites.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Clean, minimal thematic backgrounds for each of the 5 game levels.
 */
public class LevelBackground implements Sprite {
    private int levelNumber;

    /**
     * Constructs a LevelBackground for the specified level.
     * @param levelNumber the level number (1 through 5)
     */
    public LevelBackground(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    @Override
    public void drawOn(DrawSurface d) {
        int w = Game.SCREEN_WIDTH;
        int h = Game.SCREEN_HEIGHT;

        switch (this.levelNumber) {
            case 1:
                // Level 1: Deep space with clean target rings & crosshairs
                d.setColor(new Color(15, 18, 32));
                d.fillRectangle(0, 0, w, h);

                d.setColor(new Color(25, 95, 180));
                d.drawCircle(400, 165, 45);
                d.drawCircle(400, 165, 80);
                d.drawLine(400 - 100, 165, 400 + 100, 165);
                d.drawLine(400, 165 - 100, 400, 165 + 100);
                break;

            case 2:
                // Level 2: Crisp sunny sky, bright sun, and rolling green field
                d.setColor(new Color(210, 235, 255));
                d.fillRectangle(0, 0, w, h);

                d.setColor(new Color(255, 200, 40));
                d.fillCircle(130, 130, 48);

                d.setColor(new Color(90, 180, 70));
                d.fillRectangle(0, 540, w, 60);
                break;

            case 3:
                // Level 3: Twilight dusk with moon and horizon silhouette
                d.setColor(new Color(35, 28, 55));
                d.fillRectangle(0, 0, w, h);

                d.setColor(new Color(250, 240, 180));
                d.fillCircle(680, 110, 28);
                d.setColor(new Color(35, 28, 55));
                d.fillCircle(672, 105, 24); // crescent cutout

                d.setColor(new Color(20, 16, 32));
                d.fillRectangle(0, 530, w, 70);
                break;

            case 4:
                // Level 4: Cyberpunk dark matrix with sleek neon horizon
                d.setColor(new Color(16, 12, 28));
                d.fillRectangle(0, 0, w, h);

                d.setColor(new Color(60, 20, 70));
                d.fillRectangle(0, 320, w, 280);

                d.setColor(new Color(0, 200, 230));
                d.drawLine(0, 320, w, 320);
                d.drawLine(0, 321, w, 321);
                break;

            case 5:
                // Level 5: Volcanic obsidian with glowing molten lava base
                d.setColor(new Color(25, 10, 12));
                d.fillRectangle(0, 0, w, h);

                d.setColor(new Color(60, 18, 14));
                d.fillRectangle(0, 380, w, 220);

                d.setColor(new Color(190, 50, 15));
                d.fillRectangle(0, 550, w, 50);
                break;

            default:
                d.setColor(new Color(20, 20, 30));
                d.fillRectangle(0, 0, w, h);
                break;
        }
    }

    @Override
    public void timePassed() {
        // Static background
    }

    public void addToGame(Game g) {
        g.addSprite(this);
    }

    @Override
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }
}
