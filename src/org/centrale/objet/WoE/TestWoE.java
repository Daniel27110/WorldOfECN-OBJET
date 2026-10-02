package org.centrale.objet.WoE;

/** Demonstrates world creation, display and creature movement. */
public class TestWoE {

    /** Runs the demonstration program. @param args command-line arguments */
    public static void main(String[] args) {

        World world = new World();

        world.creerMondeAleatoire();

        System.out.println("\nAffichage des créatures avant déplacement :\n");

        world.robin.afficher();
        world.peon.afficher();
        world.bugs.afficher();

        world.robin.deplacer();
        world.peon.deplacer();
        world.bugs.deplacer();

        System.out.println("\nAffichage des créatures après déplacement :\n");

        world.robin.afficher();
        world.peon.afficher();
        world.bugs.afficher();
    }
}