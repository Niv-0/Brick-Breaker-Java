package Listeners;

/**
 * The listeners.HitNotifier interface represents an object that can notify HitListeners about hit events.
 */
public interface HitNotifier {
    /**
     * Adds a listeners.HitListener to the notifier.
     * @param hl the listeners.HitListener to be added
     */
    void addHitListener(HitListener hl);
    /**
     * Removes a listeners.HitListener from the notifier.
     * @param hl the listeners.HitListener to be removed
     */
    void removeHitListener(HitListener hl);
}
