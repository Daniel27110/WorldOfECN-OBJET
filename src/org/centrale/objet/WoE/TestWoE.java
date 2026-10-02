package org.centrale.objet.WoE;

public class TestWoE {

    public static void main(String[] args) {

        World world = new World();
        world.creerMondeAleatoire();

        world.robin.afficher();
        world.peon.afficher();
        world.bugs.afficher();
    }
}