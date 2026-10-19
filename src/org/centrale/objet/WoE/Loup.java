package org.centrale.objet.WoE;

/** A wolf monster. */
public class Loup extends Monstre {

    /** Creates a wolf with the supplied attributes. */
    public Loup(int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, Point2D pos) {
        super(ptVie, degAtt, ptPar, pageAtt, pagePar, pos);
    }

    /** Creates a wolf with default attributes. */
    public Loup() {
        super();
    }
}
