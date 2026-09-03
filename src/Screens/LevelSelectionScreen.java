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
 * Level Selection screen displaying 5 progressive levels,
 * navigable and clickable exclusively via the mouse.
 */
public class LevelSelectionScreen implements Animation {
    private MouseTracker mouse;
    private int unlockedLevel;
    private List<MenuButton> buttons;
    private MenuButton backButton;
    private int selectedIndex;
    private boolean stop;
    private int chosenLevel; // 1..5 for levels, -1 for Back
    private String feedbackMessage;
    private int feedbackTimer;

    /**
     * Constructs a LevelSelectionScreen.
     * @param mouse the mouse tracker
     * @param unlockedLevel the highest level currently unlocked (1..5)
     */
    public LevelSelectionScreen(MouseTracker mouse, int unlockedLevel) {
        this.mouse = mouse;
        this.unlockedLevel = Math.max(1, Math.min(5, unlockedLevel));
        this.selectedIndex = -1;
        this.stop = false;
        this.chosenLevel = 0;
        this.feedbackMessage = null;
        this.feedbackTimer = 0;

        this.buttons = new ArrayList<>();
        int buttonWidth = 460;
        int buttonHeight = 48;
        int startX = (Game.SCREEN_WIDTH - buttonWidth) / 2;
        int startY = 135;
        int gap = 56;

        String[][] levelData = {
            {"1. DIRECT HIT", "1 Ball | Speed: Moderate | Wide Paddle"},
            {"2. WIDE & EASY", "2 Balls | Speed: Normal | Rainbow Row"},
            {"3. BRICK CASCADE", "2 Balls | Speed: Fast | Durable Pyramid"},
            {"4. COLOR CHAOS", "3 Balls | Speed: Very Fast | Neon Matrix"},
            {"5. BREAKOUT INFERNO", "4 Balls | Speed: Extreme | Boss Fortress"}
        };

        for (int i = 0; i < 5; i++) {
            MenuButton btn = new MenuButton(startX, startY + i * gap, buttonWidth, buttonHeight,
                    levelData[i][0], levelData[i][1]);
            if (i + 1 > this.unlockedLevel) {
                btn.setLocked(true);
            }
            this.buttons.add(btn);
        }

        // Back button
        this.backButton = new MenuButton(startX, startY + 5 * gap + 15, buttonWidth, 42,
                "< BACK TO MAIN MENU", null);
    }

    /**
     * Overloaded constructor for compatibility.
     * @param sensor keyboard sensor (ignored for mouse-only navigation)
     * @param mouse mouse tracker
     * @param unlockedLevel highest unlocked level
     */
    public LevelSelectionScreen(KeyboardSensor sensor, MouseTracker mouse, int unlockedLevel) {
        this(mouse, unlockedLevel);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        drawBackground(d);
        handleMouseInput();

        for (int i = 0; i < this.buttons.size(); i++) {
            this.buttons.get(i).setSelected(i == this.selectedIndex);
            this.buttons.get(i).draw(d);
        }

        this.backButton.setSelected(this.selectedIndex == this.buttons.size());
        this.backButton.draw(d);

        // Feedback alert banner if user clicked a locked level
        if (this.feedbackTimer > 0 && this.feedbackMessage != null) {
            d.setColor(new Color(160, 20, 20));
            d.fillRectangle(150, 480, 500, 32);
            d.setColor(new Color(255, 100, 100));
            d.drawRectangle(150, 480, 500, 32);
            d.setColor(Color.WHITE);
            d.drawText(175, 502, this.feedbackMessage, 14);
            this.feedbackTimer--;
        }

        // Mouse-only instruction footer
        d.setColor(new Color(120, 140, 175));
        d.drawText(270, 575, "Click with mouse to select a level or go back", 13);
    }

    private void drawBackground(DrawSurface d) {
        d.setColor(new Color(10, 14, 28));
        d.fillRectangle(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

        d.setColor(new Color(0, 210, 255));
        d.drawText(275, 65, "SELECT LEVEL", 36);

        d.setColor(new Color(255, 205, 50));
        d.drawText(310, 95, "PROGRESS: Level " + this.unlockedLevel + " / 5 Unlocked", 14);

        d.setColor(new Color(0, 130, 170));
        d.drawLine(170, 115, 630, 115);
    }

    private void handleMouseInput() {
        Point mousePos = this.mouse.getMousePosition();
        this.selectedIndex = -1;

        // Check level buttons
        for (int i = 0; i < this.buttons.size(); i++) {
            MenuButton b = this.buttons.get(i);
            if (b.contains(mousePos)) {
                this.selectedIndex = i;
                if (this.mouse.isMouseClicked()) {
                    this.mouse.resetClick();
                    activateOption(i);
                    return;
                }
            }
        }

        // Check back button
        if (this.backButton.contains(mousePos)) {
            this.selectedIndex = this.buttons.size();
            if (this.mouse.isMouseClicked()) {
                this.mouse.resetClick();
                activateOption(this.buttons.size());
                return;
            }
        }

        if (this.mouse.isMouseClicked()) {
            this.mouse.resetClick();
        }
    }

    private void activateOption(int index) {
        if (index == this.buttons.size()) {
            this.chosenLevel = -1;
            this.stop = true;
            return;
        }

        int targetLevel = index + 1;
        if (targetLevel > this.unlockedLevel) {
            this.feedbackMessage = "Level " + targetLevel + " is Locked! Complete Level " + (targetLevel - 1) + " first.";
            this.feedbackTimer = 90;
        } else {
            this.chosenLevel = targetLevel;
            this.stop = true;
        }
    }

    public int getChosenLevel() {
        return this.chosenLevel;
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
