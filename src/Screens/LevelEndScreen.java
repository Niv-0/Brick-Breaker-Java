package Screens;

import Animation.Animation;
import Game.Game;
import Geometry.Point;
import Utils.MouseTracker;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Screen presented upon level completion or failure,
 * navigable and clickable exclusively via the mouse.
 */
public class LevelEndScreen implements Animation {
    private MouseTracker mouse;
    private boolean isWin;
    private int levelNumber;
    private int score;
    private List<MenuButton> buttons;
    private int selectedIndex;
    private boolean stop;
    private String selectedAction;

    /**
     * Constructs a LevelEndScreen.
     * @param mouse mouse tracker
     * @param isWin true if level was cleared, false if all balls were lost
     * @param levelNumber current level number (1..5)
     * @param score current total score
     */
    public LevelEndScreen(MouseTracker mouse, boolean isWin, int levelNumber, int score) {
        this.mouse = mouse;
        this.isWin = isWin;
        this.levelNumber = levelNumber;
        this.score = score;
        this.selectedIndex = -1;
        this.stop = false;
        this.selectedAction = null;

        this.buttons = new ArrayList<>();
        int buttonWidth = 320;
        int buttonHeight = 46;
        int startX = (Game.SCREEN_WIDTH - buttonWidth) / 2;
        int startY = 320;
        int gap = 54;

        if (this.isWin) {
            if (levelNumber < 5) {
                this.buttons.add(new MenuButton(startX, startY, buttonWidth, buttonHeight, "NEXT LEVEL", "Proceed to Level " + (levelNumber + 1)));
                startY += gap;
            }
            this.buttons.add(new MenuButton(startX, startY, buttonWidth, buttonHeight, "LEVEL SELECTION", "Choose another level"));
            this.buttons.add(new MenuButton(startX, startY + gap, buttonWidth, buttonHeight, "RETRY LEVEL", "Play this level again"));
        } else {
            this.buttons.add(new MenuButton(startX, startY, buttonWidth, buttonHeight, "RETRY LEVEL", "Try this level again"));
            this.buttons.add(new MenuButton(startX, startY + gap, buttonWidth, buttonHeight, "LEVEL SELECTION", "Return to Level Select"));
            this.buttons.add(new MenuButton(startX, startY + gap * 2, buttonWidth, buttonHeight, "MAIN MENU", "Return to Title Screen"));
        }
    }

    /**
     * Overloaded constructor for compatibility.
     * @param sensor keyboard sensor (ignored for mouse-only navigation)
     * @param mouse mouse tracker
     * @param isWin true if win
     * @param levelNumber level index
     * @param score score value
     */
    public LevelEndScreen(KeyboardSensor sensor, MouseTracker mouse, boolean isWin, int levelNumber, int score) {
        this(mouse, isWin, levelNumber, score);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        drawBackground(d);
        handleMouseInput();

        for (int i = 0; i < this.buttons.size(); i++) {
            this.buttons.get(i).setSelected(i == this.selectedIndex);
            this.buttons.get(i).draw(d);
        }

        d.setColor(new Color(130, 150, 180));
        d.drawText(280, 560, "Click an option with mouse to continue", 13);
    }

    private void drawBackground(DrawSurface d) {
        d.setColor(new Color(10, 14, 28));
        d.fillRectangle(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

        if (this.isWin) {
            d.setColor(new Color(40, 180, 80));
            d.drawText(235, 140, "LEVEL COMPLETED!", 38);

            d.setColor(new Color(255, 215, 60));
            d.drawText(290, 190, "Level " + this.levelNumber + " Cleared Successfully!", 18);
        } else {
            d.setColor(new Color(230, 50, 50));
            d.drawText(275, 140, "LEVEL FAILED", 38);

            d.setColor(new Color(200, 200, 200));
            d.drawText(310, 190, "All balls were lost in Level " + this.levelNumber, 16);
        }

        // Score display
        d.setColor(new Color(0, 210, 255));
        d.drawText(330, 250, "SCORE: " + this.score, 24);

        d.setColor(new Color(0, 110, 150));
        d.drawLine(200, 280, 600, 280);
    }

    private void handleMouseInput() {
        Point mousePos = this.mouse.getMousePosition();
        this.selectedIndex = -1;

        for (int i = 0; i < this.buttons.size(); i++) {
            MenuButton b = this.buttons.get(i);
            if (b.contains(mousePos)) {
                this.selectedIndex = i;
                if (this.mouse.isMouseClicked()) {
                    this.mouse.resetClick();
                    triggerAction(b.getLabel());
                    return;
                }
            }
        }

        if (this.mouse.isMouseClicked()) {
            this.mouse.resetClick();
        }
    }

    private void triggerAction(String label) {
        if ("NEXT LEVEL".equalsIgnoreCase(label)) {
            this.selectedAction = "Next";
        } else if ("RETRY LEVEL".equalsIgnoreCase(label)) {
            this.selectedAction = "Retry";
        } else if ("LEVEL SELECTION".equalsIgnoreCase(label)) {
            this.selectedAction = "LevelSelect";
        } else if ("MAIN MENU".equalsIgnoreCase(label)) {
            this.selectedAction = "MainMenu";
        }
        this.stop = true;
    }

    public String getSelectedAction() {
        return this.selectedAction;
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
