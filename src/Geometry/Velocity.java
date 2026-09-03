package Geometry;

/**
 * Represents the velocity of an object in a 2D space, with horizontal and vertical components.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     ** Constructs and initializes a new geometry.Velocity object with the specified horizontal and vertical components.
     * @param dx the horizontal component of the velocity
     * @param dy the vertical component of the velocity
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Calculates the horizontal (dx) and vertical (dy) components of velocity based on a given angle and speed.
     * @param angle the direction of motion (measured in degrees)
     * @param speed the magnitude of the velocity (distance traveled per step)
     * @return a new geometry.Velocity object containing the calculated dx and dy
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = -Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Calculates the speed (magnitude) of the velocity vector.
     * @return the speed calculated using the Pythagorean theorem
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }
    /**
     * Calculates a new point location after applying the velocity vector.
     * @param p the current position (geometry.Point) to which the velocity is applied
     * @return a new geometry.Point representing the next position in the trajectory
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }


    /**
     * Retrieves the horizontal change of the velocity vector.
     * @return the delta-x value, representing horizontal speed
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Retrieves the vertical change of the velocity vector.
     * @return the delta-y value, representing vertical speed
     */
    public double getDy() {
        return this.dy;
    }
}
