package org.centrale.objet.WoE;

/** A character able to fight with a limited number of arrows. */
public class Archer extends Personnage {

    private int nbFleches;

    /** Creates an archer with the supplied attributes. */
    public Archer(String nom, int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, int distAttMax,
            Point2D pos, int nbFleches) {
        super(nom, ptVie, degAtt, ptPar, pageAtt, pagePar, distAttMax, pos);
        this.nbFleches = nbFleches;
    }

    /** Creates an archer with default attributes and ten arrows. */
    public Archer() {
        super();
        this.nbFleches = 10;
    }

    public int getNbFleches() {
        return nbFleches;
    }

    /** @return whether at least one arrow is available */
    @Override
    protected boolean peutTirer() {
        return nbFleches > 0;
    }

    /** Removes one arrow after a ranged attack. */
    @Override
    protected void consommerProjectile() {
        nbFleches--;
    }
}