package Geometry;

/**
 * Represents a point in 2D space.
 *
 * <p>
 *     provides method for calculating distance and whether 2 points are equal.
 * </p>
 */
public class Point {
    private double x;
    private double y;
    public static final double COMPARISON_THRESHOLD = 0.00001;

    /**
     * Constructs a new geometry.Point object given 2 coordinates.
     * @param x The x coordinate of the new point.
     * @param y The y coordinate of the new point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks whether two double values are equals within a define tolerance.
     * @param a The first double value to check.
     * @param b The second double value to check.
     * @return 'true' |a-b|< epsilon, 'false' otherwise.
     */
    public static boolean doubleEquals(double a, double b) {
        return  Math.abs(a - b) < COMPARISON_THRESHOLD;
    }

    /**
     * Calculates the distance between the current point and another.
     * @param other The other point to calculate the distance to.
     * @return The calculated distance between the two points, '-1' if the other point is null.
     */
    public double distance(Point other) {
        if (other == null) {
            return -1;
        }
        double height = this.y - other.y;
        double edge = this.x - other.x;
        return Math.sqrt(height * height + edge * edge);
    }

    /**
     * Checks whether the current points equal another.
     * @param other Another points to check against.
     * @return 'true' if the points are equals, 'false' otherwise.
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        return (doubleEquals(this.x, other.getX())) && (doubleEquals(this.y, other.getY()));
    }

    /**
     * Retrieves the x coordinate of the current point.
     * @return The x coordinate value.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Retrieves the y coordinate of the current point.
     * @return the y coordinate value.
     */
    public double getY() {
        return this.y;
    }

}
