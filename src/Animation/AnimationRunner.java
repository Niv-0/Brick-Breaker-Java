package Animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * Runs an Animation loop at a fixed frame rate.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * Constructs an AnimationRunner with a specified frame rate.
     * @param gui the GUI to run animations on
     * @param framesPerSecond the target frames per second
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = new Sleeper();
    }

    /**
     * Constructs an AnimationRunner with default 60 FPS.
     * @param gui the GUI to run animations on
     */
    public AnimationRunner(GUI gui) {
        this(gui, 60);
    }

    /**
     * Gets the GUI used by the runner.
     * @return the biuoop.GUI instance
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * Runs the animation until its shouldStop() returns true.
     * @param animation the animation to execute
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = this.gui.getDrawSurface();

            animation.doOneFrame(d);

            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
