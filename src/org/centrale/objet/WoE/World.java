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
    private int dernierDegatsInfliges;
    private int derniersDegatsRecus;
    private final List<ElementDeJeu> elements = new ArrayList<>();

    /** Creates an empty world. */
    public World() {
    }

    /**
     * Creates an empty world with the supplied dimensions.
     *
     * @param largeur world width
     * @param hauteur world height
     */
    public World(int largeur, int hauteur) {
        if (largeur < 0 || hauteur < 0) {
            throw new IllegalArgumentException("La taille du monde ne peut pas être négative.");
        }
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    /** @return the world width */
    public int getLargeur() {
        return largeur;
    }

    /** @return the world height */
    public int getHauteur() {
        return hauteur;
    }

    /** @return an unmodifiable view of the elements on the board */
    public List<ElementDeJeu> getElements() {
        return Collections.unmodifiableList(elements);
    }

    /** @return the player, or {@code null} when the world has no player */
    public Joueur getJoueur() {
        return joueur;
    }

    /** @return damage dealt by the player's most recent combat */
    public int getDernierDegatsInfliges() {
        return dernierDegatsInfliges;
    }

    /** @return damage received by the player in the most recent combat */
    public int getDerniersDegatsRecus() {
        return derniersDegatsRecus;
    }

    /** Adds an element to the world. */
    public void ajouterElement(ElementDeJeu element) {
        if (element == null) {
            throw new IllegalArgumentException("Un élément nul ne peut pas être ajouté.");
        }
        elements.add(element);
    }

    /** Moves the player one cell when the destination is valid. */
    public boolean deplacerJoueur(int dx, int dy) {
        if (joueur == null || Math.abs(dx) > 1 || Math.abs(dy) > 1
                || (dx == 0 && dy == 0)) {
            return false;
        }

        Point2D position = joueur.getPerso().getPos();
        int x = position.getX() + dx;
        int y = position.getY() + dy;
        if (!dansLeMonde(x, y)) {
            return false;
        }

        ElementDeJeu destination = elementA(x, y);
        if (destination instanceof Creature) {
            return false;
        } else if (destination instanceof Objet) {
            position.setPosition(x, y);
            ramasserObjet((Objet) destination);
        } else if (destination == null) {
            position.setPosition(x, y);
        } else {
            return false;
        }
        return true;
    }

    /** Picks up the object on the player's current position. */
    public Objet ramasserObjet(Objet objet) {
        if (joueur == null || objet == null
                || objet.getPos().getX() != joueur.getPerso().getPos().getX()
                || objet.getPos().getY() != joueur.getPerso().getPos().getY()) {
            return null;
        }
        elements.remove(objet);
        joueur.ajouterObjet(objet);
        if (objet instanceof PotionSoin potion) {
            soignerJoueur(potion.getSoin());
        } else if (objet instanceof Nourriture nourriture) {
            soignerJoueur(nourriture.getSoin());
        } else if (objet instanceof Epee epee) {
            joueur.getPerso().setDegAtt(joueur.getPerso().getDegAtt()
                    + epee.getBonusDegAtt());
        }
        return objet;
    }

    private void soignerJoueur(int soin) {
        joueur.getPerso().setPtVie(joueur.getPerso().getPtVie() + soin);
    }

    /** Attacks the first creature within the player's attack range. */
    public Creature combattreJoueur() {
        if (joueur == null) {
            return null;
        }
        for (ElementDeJeu element : new ArrayList<>(elements)) {
            if (element instanceof Creature
                    && joueur.getPerso().getPos().distanceDeGrille(element.getPos()) <= joueur.getPerso()
                            .getDistAttMax()) {
                Creature cible = (Creature) element;
                int vieCibleAvant = cible.getPtVie();
                int vieJoueurAvant = joueur.getPerso().getPtVie();
                joueur.getPerso().combattre(cible);
                if (cible.getPtVie() > 0) {
                    cible.combattre(joueur.getPerso());
                }
                dernierDegatsInfliges = vieCibleAvant - cible.getPtVie();
                derniersDegatsRecus = vieJoueurAvant - joueur.getPerso().getPtVie();
                if (cible.getPtVie() <= 0) {
                    elements.remove(cible);
                }
                return cible;
            }
        }
        return null;
    }

    /** Moves every creature in the world by one free random cell. */
    public void deplacerCreatures() {
        int[][] directions = {
                { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 },
                { 1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }
        };
        Random random = new Random();
        for (ElementDeJeu element : new ArrayList<>(elements)) {
            if (!(element instanceof Creature)) {
                continue;
            }
            Creature creature = (Creature) element;
            int[] direction = directions[random.nextInt(directions.length)];
            int dx = direction[0];
            int dy = direction[1];
            int x = creature.getPos().getX() + dx;
            int y = creature.getPos().getY() + dy;
            if (dansLeMonde(x, y) && elementA(x, y) == null
                    && !joueurA(x, y)) {
                creature.getPos().setPosition(x, y);
            }
        }
    }

    private boolean dansLeMonde(int x, int y) {
        return x >= 0 && x < largeur && y >= 0 && y < hauteur;
    }

    private ElementDeJeu elementA(int x, int y) {
        for (ElementDeJeu element : elements) {
            if (element.getPos().getX() == x && element.getPos().getY() == y) {
                return element;
            }
        }
        return null;
    }

    private boolean joueurA(int x, int y) {
        return joueur != null && joueur.getPerso().getPos().getX() == x
                && joueur.getPerso().getPos().getY() == y;
    }

    /**
     * Creates a random world with a player and the requested number of elements.
     */
    public void creerMondeAleatoire(int nombreElements) {
        creerMondeAleatoire(10, 10, nombreElements);
    }

    /** Creates a random world with the supplied size and number of elements. */
    public void creerMondeAleatoire(int nouvelleLargeur, int nouvelleHauteur,
            int nombreElements) {
        creerMondeAleatoire(nouvelleLargeur, nouvelleHauteur, nombreElements,
                new Guerrier("joueur", 100, 10, 5, 75, 50, 1,
                        Point2D.randomPoint(nouvelleHauteur, nouvelleLargeur)));
    }

    /** Creates a random world with a supplied player character. */
    public void creerMondeAleatoire(int nouvelleLargeur, int nouvelleHauteur,
            int nombreElements, Personnage personnage) {
        if (nouvelleLargeur <= 0 || nouvelleHauteur <= 0) {
            throw new IllegalArgumentException("La taille du monde doit etre positive.");
        }
        if (nombreElements < 0 || nombreElements > nouvelleLargeur * nouvelleHauteur - 1) {
            throw new IllegalArgumentException("Trop d'elements pour cette taille de monde.");
        }
        if (personnage == null) {
            throw new IllegalArgumentException("Le personnage du joueur ne peut pas être nul.");
        }
        Random random = new Random();
        largeur = nouvelleLargeur;
        hauteur = nouvelleHauteur;
        elements.clear();

        personnage.setPos(Point2D.randomPoint(hauteur, largeur));
        joueur = new Joueur(personnage);

        for (int i = 0; i < nombreElements; i++) {
            Point2D position = positionLibre(random);
            elements.add(elementAleatoire(random, position));
        }
    }

    /** Creates n random elements and adds them to the world. */
    private ElementDeJeu elementAleatoire(Random random, Point2D position) {
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

    private Point2D positionLibre(Random random) {
        if (elements.size() >= largeur * hauteur - 1) {
            throw new IllegalArgumentException("Le monde ne peut pas contenir autant d'elements.");
        }

        Point2D position;
        do {
            position = Point2D.randomPoint(hauteur, largeur);
        } while (joueurA(position.getX(), position.getY()) || elementA(position.getX(), position.getY()) != null);
        return position;
    }

    /**
     * Loads a world from a file in the save directory.
     *
     * @param nomFichier save file name
     * @return the loaded world
     * @throws IOException if the file cannot be read
     */
    public static World sauvegardePartie(String nomFichier) throws IOException {
        return sauvegardePartie(SAVE_FILES.resolve(nomFichier));
    }

    /**
     * Loads the first save file found using the default name convention.
     *
     * @return the default saved world
     * @throws IOException if the file cannot be read
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

    /**
     * Loads a world from the supplied path.
     *
     * @param fichier save file path
     * @return the loaded world
     * @throws IOException if the file cannot be read
     */
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

    /**
     * Saves this world in the save directory.
     *
     * @param nomFichier save file name
     * @throws IOException if the file cannot be written
     */
    public void sauvegarderPartie(String nomFichier) throws IOException {
        sauvegarderPartie(SAVE_FILES.resolve(nomFichier));
    }

    /**
     * Saves this world to the supplied path.
     *
     * @param fichier save file path
     * @throws IOException if the file cannot be written
     */
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
