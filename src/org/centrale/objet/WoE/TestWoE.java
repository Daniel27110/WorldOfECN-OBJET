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

        world.robin.setPageAtt(100);
        world.robin.setDistAttMax(5);
        world.robin.setPos(new Point2D(0, 0));
        world.bugs.setPos(new Point2D(1, 0));
        world.robin.combattre(world.bugs);

        world.bugs.setPos(new Point2D(3, 0));
        world.robin.combattre(world.bugs);
        System.out.println("\nAprès les combats, points de vie du lapin : "
                + world.bugs.getPtVie() + ", flèches restantes : "
                + world.robin.getNbFleches());
    }
}