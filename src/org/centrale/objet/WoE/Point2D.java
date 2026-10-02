package org.centrale.objet.WoE;

public class Point2D {
    private int x;
    private int y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(Point2D p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Point2D() {
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void afficher() {
        System.out.println("Point2D: (" + x + ", " + y + ")");
    }

    public float distance(Point2D p) {
        int dx = this.x - p.x;
        int dy = this.y - p.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public static Point2D randomPoint(int worldHeight, int worldWidth) {
        int randomX = (int) (Math.random() * worldWidth);
        int randomY = (int) (Math.random() * worldHeight);
        return new Point2D(randomX, randomY);
    }
}