package org.centrale.objet.WoE;

/**
 * Base class for every living entity in the world.
 *
 * <p>This class owns the attributes and behaviour shared by characters and
 * monsters: combat statistics, position and random movement.</p>
 */
public class Creature {
    private int ptVie;
    private int degAtt;
    private int ptPar;
    private int pageAtt;
    private int pagePar;
    private Point2D pos;

    /** Creates a creature with the default statistics at the origin. */
    public Creature() {
        this(100, 10, 5, 5, 5, new Point2D());
    }

    /**
     * Creates a creature with the supplied statistics and position.
     *
     * @param ptVie life points
     * @param degAtt attack damage
     * @param ptPar protection points
     * @param pageAtt attack percentage
     * @param pagePar defence percentage
     * @param pos initial position
     */
    public Creature(int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, Point2D pos) {
        this.ptVie = ptVie;
        this.degAtt = degAtt;
        this.ptPar = ptPar;
        this.pageAtt = pageAtt;
        this.pagePar = pagePar;
        this.pos = new Point2D(pos);
    }

    /**
     * Creates a deep copy of a creature.
     *
     * @param creature creature to copy
     */
    public Creature(Creature creature) {
        this(creature.ptVie, creature.degAtt, creature.ptPar, creature.pageAtt, creature.pagePar,
                creature.pos);
    }

    /** Returns the life points. @return the life points */
    public int getPtVie() {
        return ptVie;
    }

    /** Changes the life points. @param ptVie new life points */
    public void setPtVie(int ptVie) {
        this.ptVie = ptVie;
    }

    /** Returns the attack damage. @return the attack damage */
    public int getDegAtt() {
        return degAtt;
    }

    /** Changes the attack damage. @param degAtt new attack damage */
    public void setDegAtt(int degAtt) {
        this.degAtt = degAtt;
    }

    /** Returns the protection points. @return the protection points */
    public int getPtPar() {
        return ptPar;
    }

    /** Changes the protection points. @param ptPar new protection points */
    public void setPtPar(int ptPar) {
        this.ptPar = ptPar;
    }

    /** Returns the attack percentage. @return the attack percentage */
    public int getPageAtt() {
        return pageAtt;
    }

    /** Changes the attack percentage. @param pageAtt new attack percentage */
    public void setPageAtt(int pageAtt) {
        this.pageAtt = pageAtt;
    }

    /** Returns the defence percentage. @return the defence percentage */
    public int getPagePar() {
        return pagePar;
    }

    /** Changes the defence percentage. @param pagePar new defence percentage */
    public void setPagePar(int pagePar) {
        this.pagePar = pagePar;
    }

    /** Returns the current position. @return the current position */
    public Point2D getPos() {
        return pos;
    }

    /** Changes the current position. @param pos new position */
    public void setPos(Point2D pos) {
        this.pos = pos;
    }

    /** Moves the creature by a random offset of -1, 0 or 1 on each axis. */
    public void deplacer() {
        int dx = (int) (Math.random() * 3) - 1;
        int dy = (int) (Math.random() * 3) - 1;
        pos.translate(dx, dy);
    }

    /** Displays the creature type, life points and position. */
    public void afficher() {
        System.out.println(getClass().getSimpleName() + ": ptVie=" + ptVie
                + ", pos=(" + pos.getX() + ", " + pos.getY() + ")");
    }

    /**
     * Performs a contact attack when the opponent is on an adjacent cell.
     *
     * @param c opponent
     */
    public void combattre(Creature c) {
        if (c == null) {
            return;
        }
        if (pos.distance(c.pos) == 1 && jetReussi(pageAtt)) {
            int degats = degAtt;
            if (jetReussi(c.pagePar)) {
                degats -= c.ptPar;
            }
            c.ptVie = Math.max(0, c.ptVie - Math.max(0, degats));
        }
    }

    /** @return whether a percentage-based die roll succeeds */
    protected boolean jetReussi(int pourcentage) {
        return 1 + (int) (Math.random() * 100) <= pourcentage;
    }
}
