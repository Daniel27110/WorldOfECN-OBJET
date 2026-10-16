package org.centrale.objet.WoE;

/** A sword object. */
public class Epee extends Objet {
    private final int bonusDegAtt;

    public Epee() {
        this(0, new Point2D());
    }

    public Epee(int bonusDegAtt, Point2D pos) {
        super(pos);
        this.bonusDegAtt = bonusDegAtt;
    }

    public int getBonusDegAtt() {
        return bonusDegAtt;
    }
}
