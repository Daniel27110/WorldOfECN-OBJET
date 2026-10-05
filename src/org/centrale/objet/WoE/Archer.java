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

    /** Creates a deep copy of an archer. */
    public Archer(Archer a) {
        super(a);
        this.nbFleches = a.nbFleches;
    }

    /** Creates an archer with default attributes and ten arrows. */
    public Archer() {
        super();
        this.nbFleches = 10;
    }

    /** @return the number of arrows */
    public int getNbFleches() {
        return nbFleches;
    }

    /** @param nbFleches new number of arrows */
    public void setNbFleches(int nbFleches) {
        this.nbFleches = nbFleches;
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