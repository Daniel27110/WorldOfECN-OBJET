package org.centrale.objet.WoE;

/** Common base for every element that can exist on the game board. */
public abstract class ElementDeJeu {
    private Point2D pos;

    /** Creates an element at the origin. */
    protected ElementDeJeu() {
        this(new Point2D());
    }

    /**
     * Creates an element at a copy of the supplied position.
     *
     * @param pos initial position
     */
    protected ElementDeJeu(Point2D pos) {
        if (pos == null) {
            throw new IllegalArgumentException("La position ne peut pas être nulle.");
        }
        this.pos = new Point2D(pos);
    }

    /** Returns the mutable position of this element. */
    public Point2D getPos() {
        return pos;
    }

    /**
     * Changes the element position.
     *
     * @param pos new position
     */
    public void setPos(Point2D pos) {
        if (pos == null) {
            throw new IllegalArgumentException("La position ne peut pas être nulle.");
        }
        this.pos = new Point2D(pos);
    }

    public abstract void afficher();
}
