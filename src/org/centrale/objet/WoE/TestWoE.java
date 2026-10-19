package org.centrale.objet.WoE;

import javax.swing.SwingUtilities;

/** Application entry point for the graphical game. */
public class TestWoE {
    /** Starts the game on the Swing event dispatch thread. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Chargement du monde:\n");
                World world = World.sauvegardePartie("Sauvegarde-WoE.txt");
                world.creerMondeAleatoire(10);
                new WorldGraphic(world).setVisible(true);
            } catch (Exception exception) {
                System.err.println("Impossible d'ouvrir le monde : " + exception.getMessage());
            }
        });
    }
}
