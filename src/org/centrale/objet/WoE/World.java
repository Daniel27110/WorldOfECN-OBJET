package org.centrale.objet.WoE;

import java.util.ArrayList;
import java.util.Random;

/** Contains the creatures and dimensions of a game world. */
public class World {

    private static final int WORLD_HEIGHT = 2000;
    private static final int WORLD_WIDTH = 2000;

    private ArrayList<Personnage> protagonistes = new ArrayList<>();

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
                    trouverPositionLibre());
            protagonistes.add(personnage);
        }

    }

    private Point2D trouverPositionLibre() {
        Point2D position = Point2D.randomPoint(WORLD_HEIGHT, WORLD_WIDTH);
        while (positionOccupee(position)) {
            position = Point2D.randomPoint(WORLD_HEIGHT, WORLD_WIDTH);
        }
        return position;
    }

    private boolean positionOccupee(Point2D position) {
        for (Personnage personnage : protagonistes) {
            if (personnage.getPos().getX() == position.getX()
                    && personnage.getPos().getY() == position.getY()) {
                return true;
            }
        }
        return false;
    }

    /** @return the protagonists stored in this world */
    public ArrayList<Personnage> getProtagonistes() {
        return protagonistes;
    }
}
