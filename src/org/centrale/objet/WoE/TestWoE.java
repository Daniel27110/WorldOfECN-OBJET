package org.centrale.objet.WoE;

import javax.swing.SwingUtilities;

public class TestWoE {
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
