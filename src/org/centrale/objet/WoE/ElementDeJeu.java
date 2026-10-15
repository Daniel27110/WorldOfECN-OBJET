package org.centrale.objet.WoE;

/** Common base for every element that can exist on the game board. */
public abstract class ElementDeJeu {
    private Point2D pos;

    protected ElementDeJeu() {
        this(new Point2D());
    }

    protected ElementDeJeu(Point2D pos) {
        if (pos == null) {
            throw new IllegalArgumentException("La position ne peut pas être nulle.");
        }
        this.pos = new Point2D(pos);
    }

    public Point2D getPos() {
        return pos;
    }

    public void setPos(Point2D pos) {
        if (pos == null) {
            throw new IllegalArgumentException("La position ne peut pas être nulle.");
        }
        this.pos = new Point2D(pos);
    }

    public abstract void afficher();
}
