package org.centrale.objet.WoE;

/** A basic playable character with no additional attributes. */
public class Paysan extends Personnage {

    /** Creates a peasant with the supplied attributes. */
    public Paysan(String nom, int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, int distAttMax,
            Point2D pos) {
        super(nom, ptVie, degAtt, ptPar, pageAtt, pagePar, distAttMax, pos);
    }

    /** Creates a deep copy of a peasant. */
    public Paysan(Paysan p) {
        super(p);
    }

    /** Creates a peasant with default attributes. */
    public Paysan() {
        super();
    }

}
