package org.centrale.objet.WoE;

/** Water that restores life when collected. */
public class Eau extends Nourriture {
    /** Creates water with the supplied healing value and position. */
    public Eau(int soin, Point2D pos) { super(soin, pos); }
}
