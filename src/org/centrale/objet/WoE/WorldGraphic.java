package org.centrale.objet.WoE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/** Simple grid display for a world. */
@SuppressWarnings("serial")
public class WorldGraphic extends JFrame {
    private int turn;
    private final JLabel turnLabel = new JLabel("Tour : 0", SwingConstants.CENTER);

    private WorldGraphic(World world) {
        super("World of ECN");

        JButton nextTurn = new JButton("Tour suivant");
        nextTurn.addActionListener(event -> turnLabel.setText("Tour : " + ++turn));

        setLayout(new BorderLayout());
        add(new Board(world), BorderLayout.CENTER);
        JPanel controls = new JPanel();
        controls.add(turnLabel);
        controls.add(nextTurn);
        add(controls, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                World world = new World();
                world.creerMondeAleatoire(20);
                new WorldGraphic(world).setVisible(true);
            } catch (RuntimeException exception) {
                System.err.println("Impossible d'ouvrir le monde : " + exception.getMessage());
            }
        });
    }

    @SuppressWarnings("serial")
    private static class Board extends JPanel {
        private final World world;

        Board(World world) {
            this.world = world;
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(760, 560));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int columns = Math.max(1, world.getLargeur());
            int rows = Math.max(1, world.getHauteur());
            int cell = Math.max(1, Math.min(getWidth() / columns, getHeight() / rows));
            int left = (getWidth() - columns * cell) / 2;
            int top = (getHeight() - rows * cell) / 2;

            g.setColor(new Color(225, 225, 225));
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
            try {
                Image image = ImageIO.read(Path.of("pngs",
                        element.getClass().getSimpleName() + ".png").toFile());
                g.drawImage(image, x, y, cell, cell, this);
            } catch (IOException exception) {
                g.setColor(element instanceof Personnage ? Color.BLUE : Color.ORANGE);
                g.fillRect(x, y, cell, cell);
            }
        }
    }
}
