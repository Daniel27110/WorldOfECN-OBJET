package org.centrale.objet.WoE;

/** A sword object. */
public class Epee extends Objet {
    private final int bonusDegAtt;

    public Epee() {
        this(0, new Point2D());
    }

    /** Creates a sword with the supplied attack bonus and position. */
    public Epee(int bonusDegAtt, Point2D pos) {
        super(pos);
        this.bonusDegAtt = bonusDegAtt;
    }

    /** @return the attack damage bonus */
    public int getBonusDegAtt() {
        return bonusDegAtt;
    }
}
