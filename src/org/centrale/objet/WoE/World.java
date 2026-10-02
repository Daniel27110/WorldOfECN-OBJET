package org.centrale.objet.WoE;

public class World {

    public Archer robin = new Archer();
    public Paysan peon = new Paysan();
    public Lapin bugs = new Lapin();

    private int worldHeight = 100;
    private int worldWidth = 100;

    public World() {

    }

    public void creerMondeAleatoire() {

        robin.setNom("Robin");
        peon.setNom("Peon");

        // Set random positions for the characters
        robin.setPos(Point2D.randomPoint(worldHeight, worldWidth));
        peon.setPos(Point2D.randomPoint(worldHeight, worldWidth));
        bugs.setPos(Point2D.randomPoint(worldHeight, worldWidth));

    }

}
