package Geometry;

import java.util.List;

/**
 * Represents a simple line.
 *
 * <p>
 * Provides methods for calculating the middle point, the distance, and if two lines are colliding
 * </p>
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * Constructs a new line object with 2 points.
     *
     * @param start the start point of the line.
     * @param end   the end point of the line.
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
    }

    /**
     * Constructs a new line object with 4 coordinates of 2 points.
     *
     * @param x1 the x coordinate of the start point
     * @param y1 the y coordinate of the start point
     * @param x2 the x coordinate of the end point
     * @param y2 the y coordinate of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * calculate the distance between the two points of the line.
     *
     * @return the length of the line.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Calculates the shortest distance from the geometry.Line to a geometry.Point.
     * @param p1 the point to check against.
     * @return the shortest distance between a line to a point.
     */
    public double distanceToPoint(Point p1) {
        double len = this.length();
        // avoid division by zero if the line is just a single point
        if (len == 0) {
            return this.start.distance(p1);
        }

        double distToStart = this.start.distance(p1);
        double distToEnd = this.end.distance(p1);

        // check if the closest point is 'start'
        // if b^2 > a^2 + c^2 the angle (bac) is >90 degrees -> a is closer to c than b.
        // distToEnd^2 > distToStart^2 + len^2
        if (distToEnd * distToEnd > distToStart * distToStart + len * len
                || Point.doubleEquals(distToEnd * distToEnd, distToStart * distToStart + len * len)) {
            return distToStart;
        }

        // check if the closest point is 'end'
        // distToStart^2 > distToEnd^2 + len^2
        if (distToStart * distToStart >= distToEnd * distToEnd + len * len) {
            return distToEnd;
        }

        // closest point is somewhere in the middle
        // the orientation will return the area of the parallelogram which is: base * height
        // so: height = orientation / base
        return Math.abs(this.calculateOrientation(p1)) / len;
    }

    /**
     * Calculate the middle point of the line segment.
     * @return A new point representing the center of the line segment.
     */
    public Point middle() {
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(midX, midY);
    }

    /**
     * Returns a copy of the line's start point.
     * @return A new geometry.Point object representing the start point.
     */
    public Point start() {
        return new Point(this.start.getX(), this.start.getY());
    }

    /**
     * Returns a copy of the line's end point.
     * @return A new geometry.Point object representing the end point.
     */
    public Point end() {
        return new Point(this.end.getX(), this.end.getY());
    }

    /**
     * Checks whether a given point is on the line.
     *
     * @param p1 The geometry.Point object to check.
     * @return 'true' if the given point lies on the line, 'false' otherwise or if it's null.
     */
    public boolean isPointInLine(Point p1) {
        if (p1 == null) {
            return false;
        }
        double minX = Math.min(this.start.getX(), this.end.getX());
        double maxX = Math.max(this.start.getX(), this.end.getX());
        double minY = Math.min(this.start.getY(), this.end.getY());
        double maxY = Math.max(this.start.getY(), this.end.getY());
        return ((p1.getX() < maxX || Point.doubleEquals(maxX, p1.getX()))
                && (p1.getX() > minX || Point.doubleEquals(p1.getX(), minX))
                && (p1.getY() < maxY || Point.doubleEquals(p1.getY(), maxY))
                && (p1.getY() > minY || Point.doubleEquals(p1.getY(), minY)));
    }

    /**
     * Checks weather a given points is between the starting and ending point of the geometry.Line object.
     * @param p1 The geometry.Point object to check.
     * @return 'true' if the points is strictly between the 2 points of the line, 'false' otherwise or if its null.
     */
    private boolean isStrictlyBetween(Point p1) {
        if (p1 == null || !this.isPointInLine(p1)) {
            return false;
        }
        return !p1.equals(this.start) && !p1.equals((this.end));
    }

    /**
     * Expands the line by a given length from the end point.
     * @param length The length to expand the line by.
     */
    public void expand(double length) {
            double newLength = length + length();
            double x = ((end.getX() * newLength) - length * start.getX()) / length();
            double y = ((end.getY() * newLength) - length * start.getY()) / length();
            this.end = new Point(x, y);
    }

    /**
     * Calculates the slope of a line segment.
     * This method must **not** be called on a vertical line.
     * @return The calculated slope value.
     */
    private double getSlope() {
        double startX = this.start.getX();
        double startY = this.start.getY();
        double endX = this.end.getX();
        double endY = this.end.getY();
        return ((startY - endY) / (startX - endX));
    }

    /**
     * Calculates the y intercept (b) of the line.
     * @return The y intercept value.
     */
    private double getYIntercept() {
        return (this.start.getY() - (getSlope() * this.start.getX()));
    }

    /**
     * Finds the closest intersection point between the line and a rectangle to the start of the line.
     * @param rect The geometry.Rectangle object to check for intersection points.
     * @return A geometry.Point object representing the closest intersection point to the start of the line.
     * null if there are no intersection points.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> interPoints = rect.intersectionPoints(this);
        if (interPoints.isEmpty()) {
            return null;
        }
        Point closestPoint = null;
        double shortestDistance = Double.MAX_VALUE;
        for (Point p : interPoints) {
            double distance = this.start.distance(p);
            if (distance < shortestDistance) {
                closestPoint = p;
                shortestDistance = distance;
            }
        }
        return closestPoint;
    }
    /**
     * Calculates the exact intersection point between two non-parallel lines.
     * The method must **not** be used on a parallel lines.
     * @param otherM The slope (m) of the other line.
     * @param otherB The y intercept (b) of the other line.
     * @return A geometry.Point object representing the intersection point between the two lines.
     */
    private Point getCollision(double otherM, double otherB) {
        double x = (otherB - getYIntercept()) / (getSlope() - otherM);
        double y = calculateY(x);
        return new Point(x, y);
    }

    /**
     * Calculates the y coordinate for a given x coordinate based on a linear equation (y = mx + b).
     * @param x The known x coordinate for which to calculate y.
     * @return The calculated y coordinate.
     */
    private double calculateY(double x) {
        return (getSlope() * x + getYIntercept());
    }

    /**
     * Checks weather a segment line is vertical.
     * @return 'true' if vertical, 'false' otherwise.
     */
    private boolean isVertical() {
        return (Point.doubleEquals(this.start.getX() - this.end.getX(), 0));
    }

    /**
     * Calculate orientation of a line and a point.
     *
     * @param p1 the point we want to check against.
     * @return The orientation value.
     */
    private double calculateOrientation(Point p1) {
        double v1x = this.end.getX() - this.start.getX();
        double v1y = this.end.getY() - this.start.getY();
        double v2y = p1.getY() - this.start.getY();
        double v2x = p1.getX() - this.start.getX();
        return ((v1x * v2y) - (v2x * v1y));
    }

    /**
     * Checks through orientation whether two lines are intersecting.
     *
     * @param other The geometry.Line object we want to check if intersecting.
     * @return 'true' if lines are intersecting, 'false' otherwise or if line is null.
     */
    public boolean isIntersecting(Line other) {
        if (other == null) {
            return false;
        }
        double o1 = this.calculateOrientation(other.start());
        double o2 = this.calculateOrientation(other.end());
        double o3 = other.calculateOrientation(this.start);
        double o4 = other.calculateOrientation(this.end);
        if (!Point.doubleEquals(o1 * o2, 0) && o1 * o2 < 0
                && !Point.doubleEquals(o3 * o4, 0) && o3 * o4 < 0) {
            return true;
        }
        if (!Point.doubleEquals(o1 * o2, 0) && o1 * o2 > 0
                || !Point.doubleEquals(o3 * o4, 0) &&  o3 * o4 > 0) {
            return false;
        }
        return (this.isPointInLine(other.end()) || this.isPointInLine(other.start())
                || other.isPointInLine(this.start()) || other.isPointInLine(this.end));
    }

    /**
     * Checks whether two lines have infinite intersection points.
     *
     * @param other The geometry.Line object to check against.
     * @return 'true' if there are infinite intersection, 'false' otherwise or if other is null.
     */
    public boolean isInfiniteIntersections(Line other) {
        if (other == null) {
            return false;
        }
        //checks whether one of the lines is contained within the other.
        if ((other.isPointInLine(this.start) && other.isPointInLine(this.end))
                || (this.isPointInLine(other.start()) && this.isPointInLine(other.end()))) {
            return true;
        }
        //checks if there is an overlap between the lines.
        return isStrictlyBetween(other.end()) || isStrictlyBetween(other.start());
    }

    /**
     * Checks whether the current line is intersecting with 2 other lines.
     *
     * @param other1 The 1st geometry.Line object to check with the current line.
     * @param other2 The 2nd geometry.Line object to check with the current line.
     * @return 'true' if these 2 lines are intersecting, 'false' otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return (isIntersecting(other1) && isIntersecting(other2));
    }

    /**
     * Divides the line into {n} equal sections and returns the section that the point is in.
     * @param p1 The point to check
     * @param n The number of sections to divide the line to
     * @return The section number the point is in, -1 if the point isn't in line.
     */
    public int whichSection(Point p1, int n) {
        if (!isPointInLine(p1)) {
            return -1;
        }
        double length = length();
        double distance = this.start.distance(p1);
        //which percentile of the line the point is on.
        double percentile =  distance / length;
        return (int) Math.floor(percentile * n) + 1;
    }
    /**
     * Calculate the point of intersection between the current line segment and another.
     * @param other The geometry.Line object to check for intersection point.
     * @return A new geometry.Point object of the intersection point.
     * null if there aren't intersection points or if there are infinite.
     */
    public Point intersectionWith(Line other) {
        if (!this.isIntersecting(other)) {
            return null;
        }
        //case 1 - one vertical, the other not.
        if (this.isVertical() && !other.isVertical()) {
            return new Point(this.start.getX(), other.calculateY(this.start.getX()));
        }
        if (other.isVertical() && !this.isVertical()) {
            return new Point(other.start.getX(), this.calculateY(other.start.getX()));
        }
        //case 2 - both vertical or have the same slope.
        boolean bothVertical = this.isVertical() && other.isVertical();
        boolean sameSlope = Point.doubleEquals(this.getSlope(), other.getSlope());
        if (bothVertical || sameSlope) {
            if (this.isInfiniteIntersections(other)) {
                return null;
            }
            // if they have only 1, it has to be the start/end point. return it.
            if (other.isPointInLine(this.start)) {
                return new Point(this.start.getX(), this.start.getY());
            }
            return new Point(this.end.getX(), this.end.getY());
        }
        //case 3 - regular intersection.
        return getCollision(other.getSlope(), other.getYIntercept());
    }
    /**
     * Checks whether two lines are equal.
     * @param other The geometry.Line object to check against the current line.
     * @return 'true' it the lines are equal, 'false' otherwise.
     */
    public boolean equals(Line other) {
        if (other == null) {
            return false;
        }
        return ((this.start.equals(other.start()) && this.end.equals(other.end()))
                || this.start.equals(other.end()) && this.end.equals(other.start()));
    }
}

