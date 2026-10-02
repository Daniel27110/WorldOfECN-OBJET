package org.centrale.objet.WoE;

/** Contains the creatures and dimensions of a game world. */
public class World {

    /** Default archer character. */
    public Archer robin = new Archer();
    /** Default peasant character. */
    public Paysan peon = new Paysan();
    /** Default rabbit monster. */
    public Lapin bugs = new Lapin();
    /** Default rabbit monster 2. */
    public Lapin bugs2 = new Lapin();
    /** Default warrior character. */
    public Guerrier guillaumeT = new Guerrier();
    /** Default wolf monster. */
    public Loup wolfie = new Loup();

    private int worldHeight = 100;
    private int worldWidth = 100;

    /** Creates a world containing default creatures. */
    public World() {

    }

    /** Assigns random positions and names to the default creatures. */
    public void creerMondeAleatoire() {

        robin.setNom("Robin");
        peon.setNom("Peon");
        guillaumeT.setNom("Guillaume");

        // Set random positions for the characters
        robin.setPos(Point2D.randomPoint(worldHeight, worldWidth));
        peon.setPos(Point2D.randomPoint(worldHeight, worldWidth));
        bugs.setPos(Point2D.randomPoint(worldHeight, worldWidth));
        bugs2.setPos(Point2D.randomPoint(worldHeight, worldWidth));
        guillaumeT.setPos(Point2D.randomPoint(worldHeight, worldWidth));
        wolfie.setPos(Point2D.randomPoint(worldHeight, worldWidth));

    }

    public void tourDeJeu() {

        robin.deplacer();
        peon.deplacer();
        bugs.deplacer();
        bugs2.deplacer();
        guillaumeT.deplacer();
        wolfie.deplacer();

    }

    public void afficherMonde() {

        robin.afficher();
        peon.afficher();
        bugs.afficher();
        bugs2.afficher();
        guillaumeT.afficher();
        wolfie.afficher();

    }

}
