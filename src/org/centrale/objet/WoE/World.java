package org.centrale.objet.WoE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A game board and all the elements placed on it. */
public class World {
    private static final Path SAVE_FILES = Path.of("saveFiles");

    private int largeur;
    private int hauteur;
    private Joueur joueur;
    private final List<ElementDeJeu> elements = new ArrayList<>();

    public World() {
    }

    public World(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public List<ElementDeJeu> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void ajouterElement(ElementDeJeu element) {
        elements.add(element);
    }

    /** Loads a save file from the {@code saveFiles} folder. */
    public static World load(String nomFichier) throws IOException {
        return load(SAVE_FILES.resolve(nomFichier));
    }

    /** Loads a save file from the supplied path. */
    public static World load(Path fichier) throws IOException {
        World world = new World();
        for (String ligne : Files.readAllLines(fichier)) {
            ligne = ligne.trim();
            if (ligne.isEmpty() || ligne.startsWith("#")) {
                continue;
            }
            String[] t = ligne.split("\\s+");

            if ("Largeur".equals(t[0])) {
                world.largeur = Integer.parseInt(t[1]);
                System.out.println("Largeur: " + world.largeur);
            } else if ("Hauteur".equals(t[0])) {
                world.hauteur = Integer.parseInt(t[1]);
                System.out.println("Hauteur: " + world.hauteur);
            } else if ("Joueur".equals(t[0])) {
                world.joueur = new Joueur(personnage(t, 1));
                world.joueur.getPerso().afficher();
            } else if ("Inventaire".equals(t[0])) {
                Objet objet = objet(t, 1);
                world.joueur.ajouterObjet(objet);
                objet.afficher();
            } else {
                ElementDeJeu element = element(t);
                world.ajouterElement(element);
                element.afficher();
            }
        }
        return world;
    }

    private static ElementDeJeu element(String[] t) {
        if ("Guerrier".equals(t[0]) || "Archer".equals(t[0])
                || "Paysan".equals(t[0])) {
            return personnage(t, 0);
        }
        if ("Loup".equals(t[0]) || "Lapin".equals(t[0])) {
            return monstre(t);
        }
        if ("NuageToxique".equals(t[0])) {
            return nuageToxique(t);
        }
        return objet(t, 0);
    }

    private static NuageToxique nuageToxique(String[] t) {
        int pourcentageAttaque = Integer.parseInt(t[1]);
        int degats = Integer.parseInt(t[2]);
        Point2D pos = new Point2D(Integer.parseInt(t[3]), Integer.parseInt(t[4]));
        return new NuageToxique(pourcentageAttaque, degats, pos);
    }

    private static Personnage personnage(String[] t, int debut) {
        String type = t[debut];
        String nom = t[debut + 1];
        int vie = Integer.parseInt(t[debut + 2]);
        int deg = Integer.parseInt(t[debut + 3]);
        int par = Integer.parseInt(t[debut + 4]);
        int att = Integer.parseInt(t[debut + 5]);
        int parade = Integer.parseInt(t[debut + 6]);
        int distance = Integer.parseInt(t[debut + 7]);
        int position = debut + 8;
        Point2D pos;

        if ("Archer".equals(type)) {
            int fleches = Integer.parseInt(t[position++]);
            pos = new Point2D(Integer.parseInt(t[position]), Integer.parseInt(t[position + 1]));
            return new Archer(nom, vie, deg, par, att, parade, distance, pos, fleches);
        }

        pos = new Point2D(Integer.parseInt(t[position]), Integer.parseInt(t[position + 1]));
        if ("Guerrier".equals(type)) {
            return new Guerrier(nom, vie, deg, par, att, parade, distance, pos);
        }
        return new Paysan(nom, vie, deg, par, att, parade, distance, pos);
    }

    private static Creature monstre(String[] t) {
        int parade = t.length == 8 ? Integer.parseInt(t[5]) : 0;
        int position = t.length == 8 ? 6 : 5;
        Point2D pos = new Point2D(Integer.parseInt(t[position]), Integer.parseInt(t[position + 1]));
        int vie = Integer.parseInt(t[1]);
        int deg = Integer.parseInt(t[2]);
        int par = Integer.parseInt(t[3]);
        int att = Integer.parseInt(t[4]);
        if ("Loup".equals(t[0])) {
            return new Loup(vie, deg, par, att, parade, pos);
        }
        return new Lapin(vie, deg, par, att, parade, pos);
    }

    private static Objet objet(String[] t, int debut) {
        int valeur = Integer.parseInt(t[debut + 1]);
        int position = debut + 2;
        Point2D pos = t.length > position + 1
                ? new Point2D(Integer.parseInt(t[position]), Integer.parseInt(t[position + 1]))
                : new Point2D();

        if ("PotionSoin".equals(t[debut])) {
            return new PotionSoin(valeur, pos);
        }
        if ("Epee".equals(t[debut])) {
            return new Epee(valeur, pos);
        }
        if ("Poisson".equals(t[debut])) {
            return new Poisson(valeur, pos);
        }
        if ("Miel".equals(t[debut])) {
            return new Miel(valeur, pos);
        }
        return new Eau(valeur, pos);
    }
}
