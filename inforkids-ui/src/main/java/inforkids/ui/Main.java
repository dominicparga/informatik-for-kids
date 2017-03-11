package inforkids.ui;

import inforkids.core.graph.impl.BasicLabyrinth;
import inforkids.core.player.BasicPlayer;
import inforkids.vis.style.ConsoleStyleSheet;
import microtrafficsim.utils.logging.EasyMarkableLogger;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class Main {

    public static void main(String[] args) {

        EasyMarkableLogger.setEnabledGlobally(true, true, true, true, true);

        guiExample();
    }


    public static void guiExample() {

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


    public static void consoleExample() {

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
}