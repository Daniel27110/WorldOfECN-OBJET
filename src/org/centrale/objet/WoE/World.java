package org.centrale.objet.WoE;

import java.util.LinkedList;
import java.util.Random;

/** Contains the creatures and dimensions of a game world. */
public class World {

    private static final int WORLD_HEIGHT = 2000;
    private static final int WORLD_WIDTH = 2000;

    private LinkedList<Personnage> protagonistes = new LinkedList<>();

    /** Creates an empty world. */
    public World() {
    }

    /**
     * Randomly generated protagonists.
     *
     * @param nombre number of protagonists to create
     */
    public void creerMondeAleatoire(int nombre) {

        Random random = new Random();

        for (int i = 0; i < nombre; i++) {
            Personnage personnage = new Personnage(
                    "Personnage-" + i,
                    50 + random.nextInt(151),
                    1 + random.nextInt(20),
                    random.nextInt(11),
                    1 + random.nextInt(100),
                    1 + random.nextInt(100),
                    1 + random.nextInt(10),
                    Point2D.randomPoint(WORLD_HEIGHT, WORLD_WIDTH));
            protagonistes.add(personnage);
        }
    }

    /** @return the protagonists stored in this world */
    public LinkedList<Personnage> getProtagonistes() {
        return protagonistes;
    }
}
