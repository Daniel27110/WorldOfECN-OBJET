package org.centrale.objet.WoE;

/** A rabbit monster. */
public class Lapin extends Monstre {

    /** Creates a rabbit with the supplied attributes. */
    public Lapin(int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, Point2D pos) {
        super(ptVie, degAtt, ptPar, pageAtt, pagePar, pos);
    }

    /** Creates a deep copy of a rabbit. */
    public Lapin(Lapin l) {
        super(l);
    }

    /** Creates a rabbit with default attributes. */
    public Lapin() {
        super();
    }

}