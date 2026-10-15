package org.centrale.objet.WoE;

public class TestWoE {
    public static void main(String[] args) {

        // Charger le fichier de sauvegarde
        System.out.println("Sauvegarde chargée avec les elements suivants : \n");
        World loadedWorld = null;

        try {
            loadedWorld = World.sauvegardePartie("Sauvegarde-WoE.txt");
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }

        // Exporter le monde chargé dans un nouveau fichier de sauvegarde
        System.out.println("\n\nExportation du monde charge dans un nouveau fichier de sauvegarde\n");

        try {
            loadedWorld.sauvegarderPartie("Sauvegarde-Export-WoE.txt");
        } catch (Exception e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }

        // Créer un monde aléatoire
        System.out.println("\n\nExportation d'un monde aleatoire dans un fichier de sauvegarde\n");
        World randomWorld = new World();
        randomWorld.creerMondeAleatoire(20);

        // Exporter un monde aleatoire dans un fichier de sauvegarde
        try {
            randomWorld.sauvegarderPartie("Sauvegarde-Random-WoE.txt");
        } catch (Exception e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }

        // Charger le monde aléatoire depuis le fichier de sauvegarde
        System.out.println("\n\nSauvegarde du monde aleatoire chargee avec les elements suivants : \n");
        World loadedRandomWorld = null;
        try {
            loadedRandomWorld = World.sauvegardePartie("Sauvegarde-Random-WoE.txt");
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }

        // Exporter le monde aléatoire chargé dans un deuxième fichier de sauvegarde
        System.out.println("\n\nExportation du monde aleatoire charge dans un deuxieme fichier de sauvegarde\n");
        try {
            loadedRandomWorld.sauvegarderPartie("Sauvegarde-Random-Export-WoE.txt");
        } catch (Exception e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }

        // les deux fichiers de sauvegarde "Sauvegarde-Random-WoE.txt" et
        // "Sauvegarde-Random-Export-WoE.txt" doivent être identiques
    }
}