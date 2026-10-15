package org.centrale.objet.WoE;

/** A harmful movable board element. */
public class NuageToxique extends ElementDeJeu implements Deplacable {
    private final int pourcentageAttaque;
    private final int degats;

    public NuageToxique(int pourcentageAttaque, int degats, Point2D pos) {
        super(pos);
        this.pourcentageAttaque = pourcentageAttaque;
        this.degats = degats;
    }

    public int getPourcentageAttaque() {
        return pourcentageAttaque;
    }

    public int getDegats() {
        return degats;
    }

    @Override
    public void deplacer() {
        getPos().translate((int) (Math.random() * 3) - 1,
                (int) (Math.random() * 3) - 1);
    }

    @Override
    public void afficher() {
        System.out.println("NuageToxique: pos=(" + getPos().getX() + ", " + getPos().getY() + ")");
    }
}
