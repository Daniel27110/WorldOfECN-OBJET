package org.centrale.objet.WoE;

/** A healing potion object. */
public class PotionSoin extends Objet {
    private final int soin;

    public PotionSoin() {
        this(0, new Point2D());
    }

    public PotionSoin(int soin, Point2D pos) {
        super(pos);
        this.soin = soin;
    }

    public int getSoin() {
        return soin;
    }
}
