package org.centrale.objet.WoE;

/** A harmful movable board element. */
public class NuageToxique extends ElementDeJeu implements Deplacable {
    private final int pourcentageAttaque;
    private final int degats;

    /** Creates a toxic cloud with the supplied attack values and position. */
    public NuageToxique(int pourcentageAttaque, int degats, Point2D pos) {
        super(pos);
        this.pourcentageAttaque = pourcentageAttaque;
        this.degats = degats;
    }

    /** @return the chance that the cloud hits */
    public int getPourcentageAttaque() {
        return pourcentageAttaque;
    }

    /** @return the damage inflicted by the cloud */
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
