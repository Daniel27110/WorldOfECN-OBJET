package org.centrale.objet.WoE;

public class Paysan extends Personnage {

    public Paysan(String nom, int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, int distAttMax,
            Point2D pos) {
        super(nom, ptVie, degAtt, ptPar, pageAtt, pagePar, distAttMax, pos);
    }

    public Paysan(Paysan p) {
        super(p);
    }

    public Paysan() {
        super();
    }

}
