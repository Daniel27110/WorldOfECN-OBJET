package org.centrale.objet.WoE;

public class TestWoE {
    public static void main(String[] args) {

        World loadedWorld = null;

        // Charger le fichier de sauvegarde
        try {
            System.out.println("Sauvegarde chargée avec les elements suivants : \n");
            loadedWorld = World.sauvegardePartie("Sauvegarde-WoE.txt");
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }

        // Exporter le monde chargé dans un nouveau fichier de sauvegarde
        try {
            System.out.println("\nSauvegarde exportée dans le fichier 'Nouvelle-Sauvegarde-WoE.txt'");
            loadedWorld.sauvegarderPartie("Nouvelle-Sauvegarde-WoE.txt");
        } catch (Exception e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }
}