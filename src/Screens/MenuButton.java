package Screens;

import Geometry.Point;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Reusable interactive button for menu screens supporting keyboard and mouse interaction.
 */
public class MenuButton {
    private int x;
    private int y;
    private int width;
    private int height;
    private String label;
    private String sublabel;
    private boolean selected;
    private boolean locked;

    /**
     * Constructs a MenuButton.
     * @param x top-left x
     * @param y top-left y
     * @param width button width
     * @param height button height
     * @param label primary button text
     * @param sublabel optional descriptive text
     */
    public MenuButton(int x, int y, int width, int height, String label, String sublabel) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.sublabel = sublabel;
        this.selected = false;
        this.locked = false;
    }

    /**
     * Simple button constructor without sublabel.
     * @param x top-left x
     * @param y top-left y
     * @param width button width
     * @param height button height
     * @param label primary button text
     */
    public MenuButton(int x, int y, int width, int height, String label) {
        this(x, y, width, height, label, null);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public String getLabel() {
        return this.label;
    }

    /**
     * Checks if a point lies within the button's boundaries.
     * @param p point to check
     * @return true if point is inside, false otherwise
     */
    public boolean contains(Point p) {
        if (p == null) {
            return false;
        }
        return p.getX() >= this.x && p.getX() <= (this.x + this.width)
                && p.getY() >= this.y && p.getY() <= (this.y + this.height);
    }

    /**
     * Draws the button onto the DrawSurface with hover and selection styling.
     * @param d the DrawSurface
     */
    public void draw(DrawSurface d) {
        Color bgColor;
        Color borderColor;
        Color textColor;

        if (this.locked) {
            bgColor = new Color(30, 32, 42);
            borderColor = new Color(60, 65, 80);
            textColor = new Color(110, 115, 130);
        } else if (this.selected) {
            bgColor = new Color(20, 80, 140);
            borderColor = new Color(0, 210, 255);
            textColor = Color.WHITE;
        } else {
            bgColor = new Color(22, 28, 48);
            borderColor = new Color(50, 75, 120);
            textColor = new Color(210, 220, 240);
        }

        // Draw background
        d.setColor(bgColor);
        d.fillRectangle(this.x, this.y, this.width, this.height);

        // Draw selection glow / outline
        d.setColor(borderColor);
        d.drawRectangle(this.x, this.y, this.width, this.height);
        if (this.selected && !this.locked) {
            d.drawRectangle(this.x + 1, this.y + 1, this.width - 2, this.height - 2);
            // Left indicator bar
            d.setColor(new Color(0, 230, 255));
            d.fillRectangle(this.x, this.y, 6, this.height);
        }

        // Draw text
        d.setColor(textColor);
        int textY = (this.sublabel != null) ? this.y + 26 : this.y + this.height / 2 + 6;
        int textX = this.x + 24;
        d.drawText(textX, textY, this.label, 18);

        // Draw sublabel or lock status
        if (this.locked) {
            d.setColor(new Color(220, 80, 70));
            d.drawText(this.x + this.width - 110, textY, "[ LOCKED ]", 14);
        } else if (this.sublabel != null) {
            d.setColor(this.selected ? new Color(170, 225, 255) : new Color(130, 145, 175));
            d.drawText(textX, textY + 18, this.sublabel, 12);
        }
    }
}
