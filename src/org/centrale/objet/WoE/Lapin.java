package org.centrale.objet.WoE;

public class Lapin extends Monstre {

    public Lapin(int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, Point2D pos) {
        super(ptVie, degAtt, ptPar, pageAtt, pagePar, pos);
    }

    public Lapin(Lapin l) {
        super(l);
    }

    public Lapin() {
        super();
    }

}