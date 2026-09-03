package Animation;

import biuoop.DrawSurface;

/**
 * The Animation interface represents an animatable screen or game loop in the application.
 */
public interface Animation {
    /**
     * Executes one frame of the animation, drawing onto the given DrawSurface and updating state.
     * @param d the DrawSurface to draw on
     */
    void doOneFrame(DrawSurface d);

    /**
     * Determines whether the animation should stop.
     * @return true if the animation should stop, false otherwise
     */
    boolean shouldStop();
}
