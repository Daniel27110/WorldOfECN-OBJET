package org.centrale.objet.WoE;

/**
 * A non-playable hostile creature.
 *
 * <p>Combat statistics, position, display and movement are inherited from
 * {@link Creature}.</p>
 */
public class Monstre extends Creature {

    /**
     * Creates a monster with the supplied attributes.
     *
     * @param ptVie life points
     * @param degAtt attack damage
     * @param ptPar protection points
     * @param pageAtt attack percentage
     * @param pagePar defence percentage
     * @param pos initial position
     */
    public Monstre(int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, Point2D pos) {
        super(ptVie, degAtt, ptPar, pageAtt, pagePar, pos);
    }

    /** Creates a deep copy of a monster. */
    public Monstre(Monstre m) {
        super(m);
    }

    /** Creates a monster with default attributes. */
    public Monstre() {
        super();
    }
}
