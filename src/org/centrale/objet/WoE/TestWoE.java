package org.centrale.objet.WoE;

public class TestWoE {
    public static void main(String[] args) {

        // Load file in SaveFiles/Sauvegarde-WoE.txtS
        try {

            World loadedWorld = World.load("Sauvegarde-WoE.txt");

        } catch (Exception e) {

            System.out.println("Error loading file: " + e.getMessage());

        }
    }
}
