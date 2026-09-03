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
 * Main Menu screen offering "Play" and "Quit" options,
 * navigable and clickable exclusively via the mouse.
 */
public class MainMenuScreen implements Animation {
    private MouseTracker mouse;
    private List<MenuButton> buttons;
    private int selectedIndex;
    private boolean stop;
    private String selectedAction;

    /**
     * Constructs a MainMenuScreen.
     * @param mouse the mouse tracker
     */
    public MainMenuScreen(MouseTracker mouse) {
        this.mouse = mouse;
        this.selectedIndex = -1;
        this.stop = false;
        this.selectedAction = null;

        this.buttons = new ArrayList<>();
        int buttonWidth = 320;
        int buttonHeight = 55;
        int startX = (Game.SCREEN_WIDTH - buttonWidth) / 2;
        int startY = 310;

        this.buttons.add(new MenuButton(startX, startY, buttonWidth, buttonHeight, "PLAY", "Start level selection"));
        this.buttons.add(new MenuButton(startX, startY + 75, buttonWidth, buttonHeight, "QUIT", "Exit the application"));
    }

    /**
     * Overloaded constructor for compatibility.
     * @param sensor keyboard sensor (ignored for mouse-only navigation)
     * @param mouse mouse tracker
     */
    public MainMenuScreen(KeyboardSensor sensor, MouseTracker mouse) {
        this(mouse);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        drawBackground(d);
        handleMouseInput();

        for (int i = 0; i < this.buttons.size(); i++) {
            this.buttons.get(i).setSelected(i == this.selectedIndex);
            this.buttons.get(i).draw(d);
        }

        // Mouse-only instruction footer
        d.setColor(new Color(120, 140, 175));
        d.drawText(285, 560, "Click with mouse to select an option", 13);
    }

    private void drawBackground(DrawSurface d) {
        d.setColor(new Color(10, 14, 28));
        d.fillRectangle(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

        // Title
        d.setColor(new Color(0, 60, 120));
        d.drawText(223, 173, "BREAKOUT", 62);
        d.setColor(new Color(0, 210, 255));
        d.drawText(220, 170, "BREAKOUT", 62);

        // Subtitle
        d.setColor(new Color(255, 205, 50));
        d.drawText(310, 215, "- ARCADE EDITION -", 18);

        // Decorative accent
        d.setColor(new Color(0, 130, 170));
        d.drawLine(240, 235, 560, 235);
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

    private void triggerAction(String action) {
        if ("PLAY".equalsIgnoreCase(action)) {
            this.selectedAction = "Play";
            this.stop = true;
        } else if ("QUIT".equalsIgnoreCase(action)) {
            this.selectedAction = "Quit";
            this.stop = true;
        }
    }

    public String getSelectedAction() {
        return this.selectedAction;
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
