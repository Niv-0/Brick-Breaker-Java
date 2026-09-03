package Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rectangle in 2D space, defined by a top-left point, width, and height.
 */
public class Rectangle {
    private Point topLeft;
    private double width;
    private double height;
    /**
     * Constructs a new rectangle given the top left geometry.Point, width and height.
     * @param topLeft the top left corner of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(Point topLeft, double width, double height) {
        this.topLeft = new Point(topLeft.getX(), topLeft.getY());
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new geometry.Rectangle given a top left coordinates, width and height.
     * @param x the x coordinate of the top left corner
     * @param y the y coordinate of the top left corner
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(double x, double y, double width, double height) {
        this.topLeft = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    /**
     * Construct a new geometry.Rectangle as a copy of another geometry.Rectangle.
     * @param rec the rectangle to copy
     */
    public Rectangle(Rectangle rec) {
        Point topLeft = new Point(rec.topLeft.getX(), rec.topLeft.getY());
        this.topLeft = topLeft;
        this.width = rec.width;
        this.height = rec.height;
    }

    /**
     * Checks whether a point is inside the rectangle.
     * @param p1 the point to check
     * @return true if point is within the geometry.Rectangle bounds, false otherwise.
     */
    public boolean contains(Point p1) {
        double x = this.topLeft.getX();
        double y = this.topLeft.getY();
        if (p1.getX() < x || p1.getX() > x + this.width) {
            return false;
        }
        return !(p1.getY() < y) && !(p1.getY() > y + this.height);
    }

    /**
     * Finds the intersection points between the rectangle and a given line.
     * @param line the line to check for intersections
     * @return a list of intersection points (can be empty if no intersections)
     */
    public List<Point> intersectionPoints(Line line) {
        Line[] sides = this.getSides();
        List<Point> points = new ArrayList<>();
        for (Line side : sides) {
            Point p1 = line.intersectionWith(side);
            if (p1 != null) {
                points.add(p1);
            }
        }
        return points;
    }

    /**
     * Retrieves the height of the rectangle.
     * @return the height value
     */
    public double getHeight() {
        return height;
    }

    /**
     * Retrieves the width of the rectangle.
     * @return the width value
     */
    public double getWidth() {
        return width;
    }

    /**
     * Retrieves the y coordinate of the left corner.
     * @return the x coordinate
     */
    public double getY() {
        return topLeft.getY();
    }
    /**
     * Retrieves the x coordinate of the left corner.
     * @return the x coordinate
     */
    public double getX() {
        return topLeft.getX();
    }

    /**
     * Retrieves the top-left point of the rectangle.
     * @return a new geometry.Point representing the top-left corner
     */
    public Point getUpperLeft() {
        return new Point(this.topLeft.getX(), this.topLeft.getY());
    }

    /**
     * Finds the closest point on the rectangle's border to a given point inside the rectangle.
     * @param p the point inside the rectangle
     * @return the closest point on the border
     */
    public Point getClosestPointOnBorder(Point p) {
        Line[] sides = this.getSides();
        Point closest = null;
        double minDist = Double.MAX_VALUE;
        for (Line side : sides) {
            double d = side.distanceToPoint(p);
            if (d < minDist) {
                minDist = d;
                if (side.start().getX() == side.end().getX()) {
                    closest = new Point(side.start().getX(), p.getY());
                } else {
                    closest = new Point(p.getX(), side.start().getY());
                }
            }
        }
        return closest;
    }
    /**
     * Returns the four sides of the rectangle as geometry.Line objects.
     * @return an array of four Lines representing the sides (top, right, bottom, left)
     */
    public Line[] getSides() {
        return new Line[]{getTopWall(), getRWall(), getBotWall(), getLWall()};
    }

    /**
     * Returns the right side of the rectangle as geometry.Line object.
     * @return a geometry.Line object representing the right side.
     */
    private Line getRWall() {
        Point top = new Point(this.topLeft.getX() + this.width, this.topLeft.getY());
        Point bot = new Point(this.topLeft.getX() + this.width, this.topLeft.getY() + this.height);
        return new Line(top, bot);
    }
    /**
     * Returns the left side of the rectangle as geometry.Line object.
     * @return a geometry.Line object representing left right side.
     */
    private Line getLWall() {
        Point bot = new Point(this.topLeft.getX(), this.topLeft.getY() + this.height);
        return new Line(bot, this.topLeft);
    }
    /**
     * Returns the top side of the rectangle as geometry.Line object.
     * @return a geometry.Line object representing the top side.
     */
    private Line getTopWall() {
        Point right = new Point(this.topLeft.getX() + this.width, this.topLeft.getY());
        return new Line(this.topLeft, right);
    }
    /**
     * Returns the bottom side of the rectangle as geometry.Line object.
     * @return a geometry.Line object representing the bottom side.
     */
    private Line getBotWall() {
        Point left = new Point(this.topLeft.getX(), this.topLeft.getY() + this.height);
        Point right = new Point(this.topLeft.getX() + this.width, this.topLeft.getY() + this.height);
        return new Line(right, left);
    }

}
