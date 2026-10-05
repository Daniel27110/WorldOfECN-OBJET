package org.centrale.objet.WoE;

import java.util.LinkedList;

public class TestWoE {
    public static void main(String[] args) {

        // Ajoutez 100 protagonistes aléatoirement
        World world = new World();
        world.creerMondeAleatoire(1000);
        LinkedList<Personnage> protagonistes = world.getProtagonistes();

        // Mesurez le temps nécessaire pour calculer le nombre total des points de vie
        // avec une boucle basée sur la taille du conteneur
        long start = System.nanoTime();
        long totalHP = 0;

        for (int i = 0; i < protagonistes.size(); i++) {
            totalHP += protagonistes.get(i).getPtVie();
        }

        long end = System.nanoTime();
        long duration = end - start;

        System.out.println("Boucle basée sur la taille du conteneur :");
        System.out.println("Points de vie totaux : " + totalHP);
        System.out.println("Durée du calcul : " + duration + " nanosecondes");

        // Mesurez le temps nécessaire pour calculer le nombre total des points de vie
        // avec une boucle basée sur les itérateurs

        start = System.nanoTime();
        totalHP = 0;

        for (Personnage personnage : protagonistes) {
            totalHP += personnage.getPtVie();
        }

        end = System.nanoTime();
        duration = end - start;

        System.out.println();
        System.out.println("Boucle basée sur les itérateurs :");
        System.out.println("Points de vie totaux : " + totalHP);
        System.out.println("Durée du calcul : " + duration + " nanosecondes");
    }

}
