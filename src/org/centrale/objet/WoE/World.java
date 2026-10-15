package org.centrale.objet.WoE;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    /**
     * Creates a random world with a player and the requested number of elements.
     */
    public void creerMondeAleatoire(int nombreElements) {
        Random random = new Random();
        largeur = 100 + random.nextInt(100);
        hauteur = 100 + random.nextInt(100);
        elements.clear();

        joueur = new Joueur(new Guerrier("joueur", 100, 10, 5, 75, 50, 1,
                Point2D.randomPoint(hauteur, largeur)));

        for (int i = 0; i < nombreElements; i++) {
            elements.add(elementAleatoire(random));
        }
    }

    /** Creates n random elements and adds them to the world. */
    private ElementDeJeu elementAleatoire(Random random) {
        Point2D position = Point2D.randomPoint(hauteur, largeur);
        int type = random.nextInt(10);
        if (type == 0) {
            return new Guerrier("guerrier", 20 + random.nextInt(81), 5 + random.nextInt(16),
                    random.nextInt(11), random.nextInt(101), random.nextInt(101),
                    1 + random.nextInt(5), position);
        }
        if (type == 1) {
            return new Archer("archer", 20 + random.nextInt(81), 5 + random.nextInt(16),
                    random.nextInt(11), random.nextInt(101), random.nextInt(101),
                    1 + random.nextInt(5), position,
                    1 + random.nextInt(20));
        }
        if (type == 2) {
            return new Paysan("paysan", 20 + random.nextInt(81), 5 + random.nextInt(16),
                    random.nextInt(11), random.nextInt(101), random.nextInt(101),
                    0, position);
        }
        type -= 3;
        if (type == 0) {
            return new Loup(20 + random.nextInt(81), 5 + random.nextInt(16),
                    random.nextInt(11), random.nextInt(101), random.nextInt(101), position);
        }
        if (type == 1) {
            return new Lapin(20 + random.nextInt(81), 5 + random.nextInt(16),
                    random.nextInt(11), random.nextInt(101), random.nextInt(101), position);
        }
        if (type == 2) {
            return new PotionSoin(10 + random.nextInt(91), position);
        }
        if (type == 3) {
            return new Epee(5 + random.nextInt(31), position);
        }
        if (type == 4) {
            return new Poisson(5 + random.nextInt(31), position);
        }
        if (type == 5) {
            return new Miel(5 + random.nextInt(31), position);
        }
        if (type == 6) {
            return new Eau(5 + random.nextInt(31), position);
        }
        return new NuageToxique(20 + random.nextInt(81), 5 + random.nextInt(16), position);
    }

    /** Loads a save file from the {@code saveFiles} folder. */
    public static World sauvegardePartie(String nomFichier) throws IOException {
        return sauvegardePartie(SAVE_FILES.resolve(nomFichier));
    }

    /**
     * Loads a save file with automatic name convention (Sauvegarde1.txt,
     * Sauvegarde2.txt etc)
     */
    public static World sauvegardePartie() throws IOException {
        int i = 1;
        while (true) {
            Path fichier = SAVE_FILES.resolve("Sauvegarde" + i + ".txt");
            if (fichier.toFile().exists()) {
                return sauvegardePartie(fichier);
            }
            i++;
        }
    }

    /** Loads a save file from the supplied path. */
    public static World sauvegardePartie(Path fichier) throws IOException {
        World world = new World();
        try (BufferedReader lecteur = new BufferedReader(new FileReader(fichier.toFile()))) {
            String ligne;
            while ((ligne = lecteur.readLine()) != null) {
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
        }
        return world;
    }

    /** Exports this world to a save file in the {@code saveFiles} folder. */
    public void sauvegarderPartie(String nomFichier) throws IOException {
        sauvegarderPartie(SAVE_FILES.resolve(nomFichier));
    }

    /** Exports this world to the supplied save-file path. */
    public void sauvegarderPartie(Path fichier) throws IOException {
        try (BufferedWriter ecrivain = new BufferedWriter(new FileWriter(fichier.toFile()))) {
            ecrivain.write("Largeur " + largeur);
            ecrivain.newLine();
            ecrivain.write("Hauteur " + hauteur);
            ecrivain.newLine();
            for (ElementDeJeu element : elements) {
                ecrivain.write(description(element));
                ecrivain.newLine();
            }
            ecrivain.write("Joueur " + description(joueur.getPerso()));
            ecrivain.newLine();
            for (Objet objet : joueur.getInventaire()) {
                ecrivain.write("Inventaire " + description(objet, false));
                ecrivain.newLine();
            }
        }
    }

    private static String description(ElementDeJeu element) {
        if (element instanceof Personnage) {
            return description((Personnage) element);
        }
        if (element instanceof Creature) {
            Creature creature = (Creature) element;
            return element.getClass().getSimpleName() + " " + creature.getPtVie() + " "
                    + creature.getDegAtt() + " " + creature.getPtPar() + " "
                    + creature.getPageAtt() + " " + creature.getPagePar() + " "
                    + creature.getPos().getX() + " " + creature.getPos().getY();
        }
        if (element instanceof NuageToxique) {
            NuageToxique nuage = (NuageToxique) element;
            return "NuageToxique " + nuage.getPourcentageAttaque() + " "
                    + nuage.getDegats() + " " + nuage.getPos().getX() + " "
                    + nuage.getPos().getY();
        }
        return description((Objet) element, true);
    }

    private static String description(Personnage personnage) {
        String description = personnage.getClass().getSimpleName() + " "
                + personnage.getNom() + " " + personnage.getPtVie() + " "
                + personnage.getDegAtt() + " " + personnage.getPtPar() + " "
                + personnage.getPageAtt() + " " + personnage.getPagePar() + " "
                + personnage.getDistAttMax();
        if (personnage instanceof Archer) {
            description += " " + ((Archer) personnage).getNbFleches();
        }
        return description + " " + personnage.getPos().getX() + " "
                + personnage.getPos().getY();
    }

    private static String description(Objet objet, boolean position) {
        int valeur = 0;
        if (objet instanceof PotionSoin) {
            valeur = ((PotionSoin) objet).getSoin();
        } else if (objet instanceof Epee) {
            valeur = ((Epee) objet).getBonusDegAtt();
        } else if (objet instanceof Nourriture) {
            valeur = ((Nourriture) objet).getSoin();
        }
        String description = objet.getClass().getSimpleName() + " " + valeur;
        if (position) {
            description += " " + objet.getPos().getX() + " " + objet.getPos().getY();
        }
        return description;
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
