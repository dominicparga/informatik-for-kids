package labyrinth.ui;

import labyrinth.vis.style.BasicGUIStyleSheet;
import labyrinth.vis.style.GUIStyleSheet;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class MainPanel extends JPanel {

    public MainPanel() {
        this(new BuildSetup());
    }

    public MainPanel(BuildSetup buildSetup) {
        super(new GridBagLayout());
        setPreferredSize(new Dimension(buildSetup.width, buildSetup.height));
        setBackground(buildSetup.style.getBackgroundColor());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.6;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(12, 12, 12, 6);
        add(new LabyrinthPanel(buildSetup.style), constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.4;
        constraints.weighty = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(12, 6, 12, 12);
        add(new ProgrammingPanel(), constraints);
    }



    public static class BuildSetup {

        public int width;
        public int height;
        public GUIStyleSheet style;

        public BuildSetup() {
            width = 800;
            height = 480;
            style = new BasicGUIStyleSheet();
        }
    }
}
