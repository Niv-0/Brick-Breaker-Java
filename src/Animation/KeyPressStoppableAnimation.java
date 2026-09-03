package Animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Decorator for Animation that stops when a specific key is pressed,
 * ensuring already-pressed keys from prior screens are not falsely triggered.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * Constructs a KeyPressStoppableAnimation.
     * @param sensor the keyboard sensor
     * @param key the key string that stops this animation
     * @param animation the underlying animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);

        if (this.sensor.isPressed(this.key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop || this.animation.shouldStop();
    }
}
