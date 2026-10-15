package org.centrale.objet.WoE;
/** Base class for objects that can be placed in the world. */
public abstract class Objet extends ElementDeJeu {
    protected Objet() {
        super();
    }

    protected Objet(Point2D pos) {
        super(pos);
    }

    @Override
    public void afficher() {
        System.out.println(getClass().getSimpleName() + ": pos=(" + getPos().getX()
                + ", " + getPos().getY() + ")");
    }
}
