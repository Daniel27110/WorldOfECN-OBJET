package org.centrale.objet.WoE;

/**
 * A playable character.
 *
 * <p>Combat statistics, position and movement are inherited from
 * {@link Creature}.</p>
 */
public class Personnage extends Creature {
    private String nom;
    private int distAttMax;

    /**
     * Creates a character with the supplied attributes.
     *
     * @param nom character name
     * @param ptVie life points
     * @param degAtt attack damage
     * @param ptPar protection points
     * @param pageAtt attack percentage
     * @param pagePar defence percentage
     * @param distAttMax maximum attack distance
     * @param pos initial position
     */
    public Personnage(String nom, int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, int distAttMax,
            Point2D pos) {
        super(ptVie, degAtt, ptPar, pageAtt, pagePar, pos);
        this.nom = nom;
        this.distAttMax = distAttMax;
    }

    /** Creates a deep copy of a character. */
    public Personnage(Personnage p) {
        super(p);
        this.nom = p.nom;
        this.distAttMax = p.distAttMax;
    }

    /** Creates a character with default attributes. */
    public Personnage() {
        super();
        this.nom = "Personnage";
        this.distAttMax = 1;
    }

    /** @return the character name */
    public String getNom() {
        return nom;
    }

    /** @param nom new character name */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /** @return the maximum attack distance */
    public int getDistAttMax() {
        return distAttMax;
    }

    /** @param distAttMax new maximum attack distance */
    public void setDistAttMax(int distAttMax) {
        this.distAttMax = distAttMax;
    }

    /** Displays the character name and position. */
    @Override
    public void afficher() {
        System.out.println("Personnage: nom=" + nom + ", pos=(" + getPos().getX()
                + ", " + getPos().getY() + ")");
    }
}
