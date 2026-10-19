package org.centrale.objet.WoE;

/** A board object that restores life. */
public abstract class Nourriture extends Objet {
    private final int soin;

    protected Nourriture(int soin, Point2D pos) {
        super(pos);
        this.soin = soin;
    }

    /** @return the number of life points restored */
    public int getSoin() { return soin; }
}
