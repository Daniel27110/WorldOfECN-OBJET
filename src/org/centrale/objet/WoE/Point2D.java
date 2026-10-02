package org.centrale.objet.WoE;

/** Represents an integer two-dimensional position. */
public class Point2D {
    private int x;
    private int y;

    /**
     * Creates a point at the supplied coordinates.
     * @param x x coordinate
     * @param y y coordinate
     */
    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Creates a copy of a point. @param p point to copy */
    public Point2D(Point2D p) {
        this.x = p.x;
        this.y = p.y;
    }

    /** Creates a point at the origin. */
    public Point2D() {
        this.x = 0;
        this.y = 0;
    }

    /** Returns the x coordinate. @return the x coordinate */
    public int getX() {
        return x;
    }

    /** Changes the x coordinate. @param x new x coordinate */
    public void setX(int x) {
        this.x = x;
    }

    /** Returns the y coordinate. @return the y coordinate */
    public int getY() {
        return y;
    }

    /** Changes the y coordinate. @param y new y coordinate */
    public void setY(int y) {
        this.y = y;
    }

    /** Sets both coordinates. @param x new x coordinate @param y new y coordinate */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Translates the point. @param dx x offset @param dy y offset */
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    /** Displays the coordinates. */
    public void afficher() {
        System.out.println("Point2D: (" + x + ", " + y + ")");
    }

    /** Returns the Euclidean distance to another point. @param p other point
     * @return the Euclidean distance */
    public float distance(Point2D p) {
        int dx = this.x - p.x;
        int dy = this.y - p.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Creates a random point inside a world rectangle.
     *
     * @param worldHeight world height
     * @param worldWidth world width
     * @return a point with x in {@code [0, worldWidth)} and y in
     *         {@code [0, worldHeight)}
     */
    public static Point2D randomPoint(int worldHeight, int worldWidth) {
        int randomX = (int) (Math.random() * worldWidth);
        int randomY = (int) (Math.random() * worldHeight);
        return new Point2D(randomX, randomY);
    }
}