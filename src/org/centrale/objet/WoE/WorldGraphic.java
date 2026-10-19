package org.centrale.objet.WoE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.Timer;

/** Simple grid display for a world. */
public class WorldGraphic extends JFrame {
    private static final long serialVersionUID = 1L;
    private int turn;
    private final JLabel turnLabel = new JLabel("Tour : 0", SwingConstants.CENTER);
    private final JTextArea playerStats = new JTextArea();
    private final JTextArea actions = new JTextArea();

    public WorldGraphic(World world) {
        super("World of ECN");
        Board board = new Board(world);
        JButton skipTurn = new JButton("Passer le tour");
        skipTurn.addActionListener(event -> advanceTurn(world, board));
        JButton fight = new JButton("Combattre");
        fight.addActionListener(event -> fight(world, board));
        JButton save = new JButton("Sauvegarder");
        save.addActionListener(event -> saveWorld(world));
        JButton load = new JButton("Charger");
        load.addActionListener(event -> loadWorld());
        JButton newGame = new JButton("Nouvelle partie");
        newGame.addActionListener(event -> newGame());

        setLayout(new BorderLayout());
        add(board, BorderLayout.CENTER);
        add(actionLog(), BorderLayout.WEST);
        add(sidePanel(world), BorderLayout.EAST);
        JPanel controls = new JPanel();
        controls.add(turnLabel);
        controls.add(skipTurn);
        controls.add(fight);
        controls.add(save);
        controls.add(load);
        controls.add(newGame);
        add(controls, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        addMovementKeys(board, world);
        updatePlayerStats(world);
        log("Le jeu commence.");
    }

    private void advanceTurn(World world, Board board) {
        world.deplacerCreatures();
        turnLabel.setText("Tour : " + ++turn);
        log("Tour passé : les créatures se déplacent.");
        updatePlayerStats(world);
        board.repaint();
    }

    private void fight(World world, Board board) {
        Creature target = world.combattreJoueur();
        if (target == null) {
            log("Aucune créature à combattre à portée.");
            return;
        }
        log("Combat contre " + target.getClass().getSimpleName() + ".");
        log("Vous infligez " + world.getDernierDegatsInfliges() + " dégâts.");
        log("L'ennemi vous inflige " + world.getDerniersDegatsRecus() + " dégâts.");
        if (target.getPtVie() <= 0) {
            log("La créature est vaincue.");
        } else {
            log("Il lui reste " + target.getPtVie() + " points de vie.");
        }
        world.deplacerCreatures();
        turnLabel.setText("Tour : " + ++turn);
        updatePlayerStats(world);
        board.repaint();
    }

    private void saveWorld(World world) {
        String fileName = JOptionPane.showInputDialog(this,
                "Nom du fichier de sauvegarde :", "Sauvegarder",
                JOptionPane.QUESTION_MESSAGE);
        if (fileName == null || fileName.trim().isEmpty()) {
            return;
        }
        try {
            world.sauvegarderPartie(fileName.trim());
            JOptionPane.showMessageDialog(this, "Partie sauvegardée.",
                    "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this, "Erreur : " + exception.getMessage(),
                    "Sauvegarde impossible", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadWorld() {
        String fileName = JOptionPane.showInputDialog(this,
                "Nom du fichier à charger :", "Charger",
                JOptionPane.QUESTION_MESSAGE);
        if (fileName == null || fileName.trim().isEmpty()) {
            return;
        }
        try {
            World loadedWorld = World.sauvegardePartie(fileName.trim());
            new WorldGraphic(loadedWorld).setVisible(true);
            dispose();
        } catch (IOException | RuntimeException exception) {
            JOptionPane.showMessageDialog(this, "Erreur : " + exception.getMessage(),
                    "Chargement impossible", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void newGame() {
        String answer = JOptionPane.showInputDialog(this,
                "Taille du plateau (carré) :", "Nouvelle partie",
                JOptionPane.QUESTION_MESSAGE);
        if (answer == null || answer.trim().isEmpty()) {
            return;
        }
        try {
            int size = Integer.parseInt(answer.trim());
            if (size <= 0 || size > 100) {
                throw new NumberFormatException();
            }
            String[] classes = { "Guerrier", "Archer", "Paysan" };
            String characterClass = (String) JOptionPane.showInputDialog(this,
                    "Choisissez une classe :", "Nouveau personnage",
                    JOptionPane.QUESTION_MESSAGE, null, classes, classes[0]);
            if (characterClass == null) {
                return;
            }

            String name = JOptionPane.showInputDialog(this,
                    "Nom du personnage :", "Nouveau personnage",
                    JOptionPane.QUESTION_MESSAGE);
            if (name == null || name.trim().isEmpty()) {
                return;
            }

            World newWorld = new World();
            newWorld.creerMondeAleatoire(size, size, Math.min(20, size * size - 1),
                    createCharacter(characterClass, name.trim()));
            new WorldGraphic(newWorld).setVisible(true);
            dispose();
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer une taille entière positive.",
                    "Taille invalide", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Personnage createCharacter(String characterClass, String name) {
        Personnage character;
        if ("Archer".equals(characterClass)) {
            character = new Archer();
        } else if ("Paysan".equals(characterClass)) {
            character = new Paysan();
        } else {
            character = new Guerrier();
        }
        character.setNom(name);
        return character;
    }

    private JPanel sidePanel(World world) {
        JTextArea text = new JTextArea(
                "Comment jouer:\n\n"
                        + "Déplacement :\n"
                        + "• W pour aller vers le haut.\n"
                        + "• S pour aller vers le bas.\n"
                        + "• A pour aller vers la gauche.\n"
                        + "• D pour aller vers la droite.\n"
                        + "• C ou le bouton Combattre pour attaquer une créature adjacente.\n"
                        + "• Marcher sur un objet pour le ajouter à l'inventaire.\n"
                        + "• Passer le tour fait déplacer les créatures.\n\n");
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setOpaque(false);
        text.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        playerStats.setEditable(false);
        playerStats.setOpaque(false);
        playerStats.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(titled("Commandes", text), BorderLayout.NORTH);
        panel.add(titled("Personnage", playerStats), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(210, 0));
        return panel;
    }

    private JPanel actionLog() {
        actions.setEditable(false);
        actions.setLineWrap(true);
        actions.setWrapStyleWord(true);
        actions.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Actions"));
        panel.add(new JScrollPane(actions), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(180, 0));
        return panel;
    }

    private void log(String message) {
        actions.append("• " + message + "\n");
        actions.setCaretPosition(actions.getDocument().getLength());
    }

    private JPanel titled(String title, JTextArea content) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private void updatePlayerStats(World world) {
        if (world.getJoueur() == null) {
            playerStats.setText("Aucun joueur");
            return;
        }
        Personnage player = world.getJoueur().getPerso();
        playerStats.setText("Nom : " + player.getNom()
                + "\nVie : " + player.getPtVie()
                + "\nAttaque : " + player.getDegAtt()
                + "\nDéfense : " + player.getPtPar()
                + "\nPosition : (" + player.getPos().getX() + ", "
                + player.getPos().getY() + ")");
    }

    private void addMovementKeys(Board board, World world) {
        board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("W"), "up");
        board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("S"), "down");
        board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("A"), "left");
        board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("D"), "right");
        board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("C"), "fight");

        board.getActionMap().put("up", movement(world, 0, -1, board));
        board.getActionMap().put("down", movement(world, 0, 1, board));
        board.getActionMap().put("left", movement(world, -1, 0, board));
        board.getActionMap().put("right", movement(world, 1, 0, board));
        board.getActionMap().put("fight", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {
                fight(world, board);
            }
        });
    }

    private AbstractAction movement(World world, int dx, int dy, Board board) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {
                int inventorySize = world.getJoueur().getInventaire().size();
                if (world.deplacerJoueur(dx, dy)) {
                    if (world.getJoueur().getInventaire().size() > inventorySize) {
                        Objet object = world.getJoueur().getInventaire().get(inventorySize);
                        log("Objet ramassé : " + object.getClass().getSimpleName() + ".");
                    } else {
                        log("Déplacement effectué.");
                    }
                    world.deplacerCreatures();
                    turnLabel.setText("Tour : " + ++turn);
                    updatePlayerStats(world);
                    board.repaint();
                }
            }
        };
    }

    private static class Board extends JPanel {
        private static final long serialVersionUID = 1L;
        private final transient World world;
        private final transient Map<String, GifSprite> sprites = new HashMap<>();
        private final Random random = new Random();
        private int[][] emptyTileVariants;

        Board(World world) {
            this.world = world;
            setPreferredSize(new Dimension(760, 560));
            new Timer(100, event -> {
                sprites.values().stream()
                        .filter(sprite -> sprite != null)
                        .forEach(GifSprite::nextFrame);
                repaint();
            }).start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int columns = Math.max(1, world.getLargeur());
            int rows = Math.max(1, world.getHauteur());
            int cell = Math.max(1, Math.min(getWidth() / columns, getHeight() / rows));
            int left = (getWidth() - columns * cell) / 2;
            int top = (getHeight() - rows * cell) / 2;

            if (emptyTileVariants == null
                    || emptyTileVariants.length != columns
                    || emptyTileVariants[0].length != rows) {
                emptyTileVariants = new int[columns][rows];
                for (int x = 0; x < columns; x++) {
                    for (int y = 0; y < rows; y++) {
                        emptyTileVariants[x][y] = random.nextInt(3) + 1;
                    }
                }
            }
            for (int x = 0; x < columns; x++) {
                for (int y = 0; y < rows; y++) {
                    GifSprite emptyTile = getSprite("Empty" + emptyTileVariants[x][y]);
                    if (emptyTile != null) {
                        drawSprite(g, emptyTile, left + x * cell, top + y * cell, cell);
                    }
                }
            }

            for (int x = 0; x <= columns; x++) {
                g.drawLine(left + x * cell, top, left + x * cell, top + rows * cell);
            }
            for (int y = 0; y <= rows; y++) {
                g.drawLine(left, top + y * cell, left + columns * cell, top + y * cell);
            }

            for (ElementDeJeu element : world.getElements()) {
                drawElement(g, element, left, top, cell, columns, rows);
            }
            if (world.getJoueur() != null) {
                drawElement(g, world.getJoueur().getPerso(), left, top, cell, columns, rows);
                Point2D playerPosition = world.getJoueur().getPerso().getPos();
                Graphics2D border = (Graphics2D) g.create();
                border.setColor(new Color(218, 165, 32));
                border.setStroke(new BasicStroke(4));
                border.drawRect(left + playerPosition.getX() * cell,
                        top + playerPosition.getY() * cell, cell, cell);
                border.dispose();
            }
        }

        private void drawElement(Graphics g, ElementDeJeu element, int left, int top,
                int cell, int columns, int rows) {
            Point2D position = element.getPos();
            if (position.getX() < 0 || position.getX() >= columns
                    || position.getY() < 0 || position.getY() >= rows) {
                return;
            }

            int x = left + position.getX() * cell;
            int y = top + position.getY() * cell;
            GifSprite sprite = getSprite(element.getClass().getSimpleName());
            if (sprite != null) {
                int size = cell;
                if (element instanceof Creature) {
                    size = (int) (cell * 1.2);
                    if (element instanceof Lapin || element instanceof Paysan) {
                        size = (int) (cell * 0.8);
                    }
                } else if (element instanceof Objet && !(element instanceof Eau)) {
                    size = Math.max(1, (int) (cell * 0.5));
                }
                drawSprite(g, sprite, x + (cell - size) / 2, y + (cell - size) / 2, size);
                return;
            }
            g.drawString(element.getClass().getSimpleName(), x, y + g.getFontMetrics().getAscent());
        }

        private void drawSprite(Graphics g, GifSprite sprite, int x, int y, int cell) {
            BufferedImage frame = sprite.currentFrame();
            Graphics2D scaledGraphics = (Graphics2D) g.create();
            scaledGraphics.translate(x, y);
            scaledGraphics.drawImage(frame, 0, 0, cell, cell, this);
            scaledGraphics.dispose();
        }

        private GifSprite getSprite(String name) {
            if (sprites.containsKey(name)) {
                return sprites.get(name);
            }

            Path path = Path.of("sprites", name + ".gif");
            GifSprite sprite = Files.isRegularFile(path) ? GifSprite.load(path) : null;
            sprites.put(name, sprite);
            return sprite;
        }
    }

    private static final class GifSprite {
        private final List<BufferedImage> frames;
        private int frame;

        private GifSprite(List<BufferedImage> frames) {
            this.frames = frames;
        }

        private BufferedImage currentFrame() {
            return frames.get(frame);
        }

        private void nextFrame() {
            frame = (frame + 1) % frames.size();
        }

        private static GifSprite load(Path path) {
            List<BufferedImage> frames = new ArrayList<>();
            try (ImageInputStream input = ImageIO.createImageInputStream(path.toFile())) {
                ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
                reader.setInput(input);
                for (int index = 0; index < reader.getNumImages(true); index++) {
                    frames.add(reader.read(index));
                }
                reader.dispose();
            } catch (IOException exception) {
                System.err.println("Impossible de lire " + path + " : " + exception.getMessage());
            }
            return frames.isEmpty() ? null : new GifSprite(frames);
        }
    }
}