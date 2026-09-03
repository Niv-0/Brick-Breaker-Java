package Utils;

import biuoop.GUI;
import Geometry.Point;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Field;

/**
 * Tracks mouse position and click events on a biuoop.GUI window.
 */
public class MouseTracker implements MouseListener, MouseMotionListener {
    private volatile Point currentPoint;
    private volatile boolean clicked;

    /**
     * Constructs a MouseTracker and attaches it to the given GUI.
     * @param gui the GUI to attach to
     */
    public MouseTracker(GUI gui) {
        this.currentPoint = new Point(-1, -1);
        this.clicked = false;
        attachToGUI(gui);
    }

    private void attachToGUI(GUI gui) {
        JFrame frame = null;
        try {
            Field frameField = GUI.class.getDeclaredField("frame");
            frameField.setAccessible(true);
            frame = (JFrame) frameField.get(gui);
        } catch (Exception e) {
            // Fallback to Window.getWindows()
            for (Window window : Window.getWindows()) {
                if (window instanceof JFrame) {
                    frame = (JFrame) window;
                    break;
                }
            }
        }

        if (frame != null) {
            registerListeners(frame);
            for (Component c : frame.getContentPane().getComponents()) {
                registerListeners(c);
            }
        }
    }

    private void registerListeners(Component component) {
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                registerListeners(child);
            }
        }
    }

    /**
     * Gets the latest tracked mouse position.
     * @return geometry.Point containing x and y coordinates
     */
    public Point getMousePosition() {
        return this.currentPoint;
    }

    /**
     * Checks if a mouse click was registered.
     * @return true if mouse clicked, false otherwise
     */
    public boolean isMouseClicked() {
        return this.clicked;
    }

    /**
     * Resets the mouse click state.
     */
    public void resetClick() {
        this.clicked = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.currentPoint = new Point(e.getX(), e.getY());
        this.clicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.currentPoint = new Point(e.getX(), e.getY());
        this.clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.currentPoint = new Point(e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.currentPoint = new Point(e.getX(), e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Mouse exited window area
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.currentPoint = new Point(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.currentPoint = new Point(e.getX(), e.getY());
    }
}
