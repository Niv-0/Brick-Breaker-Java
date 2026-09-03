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
 * In-game pause overlay screen with Resume, Retry, and Quit options,
 * navigable and clickable exclusively via the mouse.
 */
public class PauseScreen implements Animation {
    private MouseTracker mouse;
    private List<MenuButton> buttons;
    private int selectedIndex;
    private boolean stop;
    private String action;

    /**
     * Constructs a PauseScreen.
     * @param mouse the mouse tracker
     */
    public PauseScreen(MouseTracker mouse) {
        this.mouse = mouse;
        this.selectedIndex = -1;
        this.stop = false;
        this.action = "Resume";

        this.buttons = new ArrayList<>();
        int buttonWidth = 280;
        int buttonHeight = 44;
        int startX = (Game.SCREEN_WIDTH - buttonWidth) / 2;
        int startY = 200;
        int gap = 55;

        this.buttons.add(new MenuButton(startX, startY, buttonWidth, buttonHeight, "RESUME", "Continue current level"));
        this.buttons.add(new MenuButton(startX, startY + gap, buttonWidth, buttonHeight, "RETRY", "Restart this level"));
        this.buttons.add(new MenuButton(startX, startY + gap * 2, buttonWidth, buttonHeight, "QUIT", "Exit to Level Selection"));
    }

    /**
     * Overloaded constructor for compatibility.
     * @param sensor keyboard sensor (ignored for mouse-only navigation)
     * @param mouse mouse tracker
     */
    public PauseScreen(KeyboardSensor sensor, MouseTracker mouse) {
        this(mouse);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Dimmed overlay over the frozen game screen
        d.setColor(new Color(12, 16, 30));
        d.fillRectangle(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

        // Center dialog box
        int dialogWidth = 360;
        int dialogHeight = 310;
        int dialogX = (Game.SCREEN_WIDTH - dialogWidth) / 2;
        int dialogY = 135;

        d.setColor(new Color(18, 24, 44));
        d.fillRectangle(dialogX, dialogY, dialogWidth, dialogHeight);
        d.setColor(new Color(0, 210, 255));
        d.drawRectangle(dialogX, dialogY, dialogWidth, dialogHeight);
        d.drawRectangle(dialogX + 2, dialogY + 2, dialogWidth - 4, dialogHeight - 4);

        // Pause title
        d.setColor(new Color(255, 205, 50));
        d.drawText(310, 175, "GAME PAUSED", 26);

        handleMouseInput();

        for (int i = 0; i < this.buttons.size(); i++) {
            this.buttons.get(i).setSelected(i == this.selectedIndex);
            this.buttons.get(i).draw(d);
        }

        // Mouse-only footer helper
        d.setColor(new Color(130, 150, 180));
        d.drawText(275, 420, "Click an option with mouse", 13);
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
                    triggerOption(i);
                    return;
                }
            }
        }

        if (this.mouse.isMouseClicked()) {
            this.mouse.resetClick();
        }
    }

    private void triggerOption(int index) {
        if (index == 0) {
            this.action = "Resume";
        } else if (index == 1) {
            this.action = "Retry";
        } else {
            this.action = "Quit";
        }
        this.stop = true;
    }

    /**
     * Checks if the user selected to resume the current level.
     * @return true if Resume chosen
     */
    public boolean isResumeRequested() {
        return "Resume".equalsIgnoreCase(this.action);
    }

    /**
     * Checks if the user selected to retry the current level.
     * @return true if Retry chosen
     */
    public boolean isRetryRequested() {
        return "Retry".equalsIgnoreCase(this.action);
    }

    /**
     * Checks if the user selected to quit the current level.
     * @return true if Quit was chosen
     */
    public boolean isQuitRequested() {
        return "Quit".equalsIgnoreCase(this.action);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
