package Utils;

/**
 * A simple counter class that can be increased or decreased by a specified amount.
 */
public class Counter {
    private int number;

    /**
     * Constructor to initialize the counter with a starting value.
     * @param number the initial value of the counter
     */
    public Counter(int number) {
        this.number = number;
    }

    /**
     * Increases the counter by a specified amount.
     * @param number the amount to increase the counter by
     */
    public void increase(int number) {
        this.number += number;
    }

    /**
     * Decreases the counter by a specified amount.
     * @param number the amount to decrease the counter by
     */
    public void decrease(int number) {
        this.number -= number;
    }

    /**
     * Retrieves the current value of the counter.
     * @return the current value of the counter
     */
    public int getValue() {
        return this.number;
    }

    /**
     * Sets the counter to a specified value.
     * @param number the new value
     */
    public void setValue(int number) {
        this.number = number;
    }
}
