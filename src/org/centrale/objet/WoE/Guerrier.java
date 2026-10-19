package org.centrale.objet.WoE;

/** A warrior able to fight creatures. */
public class Guerrier extends Personnage {

    /** Creates a warrior with the supplied attributes. */
    public Guerrier(String nom, int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar,
            int distAttMax, Point2D pos) {
        super(nom, ptVie, degAtt, ptPar, pageAtt, pagePar, distAttMax, pos);
    }

    /** Creates a warrior. */
    public Guerrier() {
        super();
    }

}
