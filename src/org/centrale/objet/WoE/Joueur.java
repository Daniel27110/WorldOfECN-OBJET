package org.centrale.objet.WoE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Human player and the character controlled by that player. */
public class Joueur {
    private final Personnage perso;
    private final List<Objet> inventaire = new ArrayList<>();

    /** Creates a player controlling the supplied character. */
    public Joueur(Personnage perso) {
        if (perso == null) {
            throw new IllegalArgumentException("Le personnage du joueur ne peut pas être nul.");
        }
        this.perso = perso;
    }

    /** @return the character controlled by this player */
    public Personnage getPerso() {
        return perso;
    }

    /** @return an unmodifiable view of the inventory */
    public List<Objet> getInventaire() {
        return Collections.unmodifiableList(inventaire);
    }

    /** Adds an object to the inventory. */
    public void ajouterObjet(Objet objet) {
        if (objet == null) {
            throw new IllegalArgumentException("Un objet nul ne peut pas être ajouté.");
        }
        inventaire.add(objet);
    }
}
