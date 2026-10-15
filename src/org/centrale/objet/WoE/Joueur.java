package org.centrale.objet.WoE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Human player and the character controlled by that player. */
public class Joueur {
    private final Personnage perso;
    private final List<Objet> inventaire = new ArrayList<>();

    public Joueur(Personnage perso) {
        if (perso == null) {
            throw new IllegalArgumentException("Le personnage du joueur ne peut pas être nul.");
        }
        this.perso = perso;
    }

    public Personnage getPerso() {
        return perso;
    }

    public List<Objet> getInventaire() {
        return Collections.unmodifiableList(inventaire);
    }

    public void ajouterObjet(Objet objet) {
        if (objet == null) {
            throw new IllegalArgumentException("Un objet nul ne peut pas être ajouté.");
        }
        inventaire.add(objet);
    }
}
