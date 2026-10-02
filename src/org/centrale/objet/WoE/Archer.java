package org.centrale.objet.WoE;

public class Archer extends Personnage {

    private int nbFleches;

    public Archer(String nom, int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, int distAttMax,
            Point2D pos, int nbFleches) {
        super(nom, ptVie, degAtt, ptPar, pageAtt, pagePar, distAttMax, pos);
        this.nbFleches = nbFleches;
    }

    public Archer(Archer a) {
        super(a);
        this.nbFleches = a.nbFleches;
    }

    public Archer() {
        super();
        this.nbFleches = 10;
    }

    public int getNbFleches() {
        return nbFleches;
    }

    public void setNbFleches(int nbFleches) {
        this.nbFleches = nbFleches;
    }

}