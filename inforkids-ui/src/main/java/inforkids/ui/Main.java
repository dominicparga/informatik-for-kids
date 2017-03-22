package inforkids.ui;

import inforkids.core.graph.impl.BasicLabyrinth;
import inforkids.core.player.BasicPlayer;
import inforkids.ui.panels.MainPanel;
import inforkids.vis.style.ConsoleStyleSheet;
import microtrafficsim.utils.logging.EasyMarkableLogger;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class Main {

    public static void main(String[] args) {

        EasyMarkableLogger.setEnabledGlobally(false, true, true, true, true);

        guiExample();
    }


    private static void guiExample() {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        UIManager.put("swing.boldMetal", Boolean.FALSE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Informatik für Kinder");
            window.add(new MainPanel());
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setResizable(true);
            window.setMinimumSize(new Dimension(200, 1));
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }


    private static void consoleExample() {

//        Labyrinth labyrinth = new Labyrinth(5, 5);
        BasicLabyrinth labyrinth = new BasicLabyrinth(5, 5, "WWWWW WGGGW W*WGW WGGGW WWWWW");
        BasicPlayer player = new BasicPlayer(labyrinth.get(3, 2));

        ConsoleStyleSheet styleSheet = new ConsoleStyleSheet();
        System.out.println(styleSheet.get(labyrinth, player));

        for (int i = 0; i < 20; i++) {
            player.walkRandomSingleMove();
            System.out.println(styleSheet.get(labyrinth, player));
        }
    }


    private static void scrollExample() {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame();

            final JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.red));
            panel.setPreferredSize(new Dimension(800, 600));

            final JScrollPane scroll = new JScrollPane(panel);

            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(scroll, BorderLayout.CENTER);
            frame.setSize(300, 300);
            frame.setVisible(true);
        });
    }
}