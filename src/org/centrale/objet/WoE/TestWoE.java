package org.centrale.objet.WoE;

/** Demonstrates world creation, display and creature movement. */
public class TestWoE {

    /** Runs the demonstration program. @param args command-line arguments */
    public static void main(String[] args) {

        World world = new World();

        world.creerMondeAleatoire();

        System.out.println("\nAffichage des créatures avant déplacement :\n");

        world.afficherMonde();

        System.out.println("\nAffichage des créatures après déplacement :\n");

        world.tourDeJeu();

        world.afficherMonde();
    }
}