package org.centrale.objet.WoE;

public class TestWoE {
    public static void main(String[] args) {

        System.out.println("Sauvegarde chargée avec les elements suivants : \n");

        // Charger le fichier de sauvegarde
        try {

            World loadedWorld = World.load("Sauvegarde-WoE.txt");

        } catch (Exception e) {

            System.out.println("Error loading file: " + e.getMessage());

        }
    }
}
