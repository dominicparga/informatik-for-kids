package labyrinth.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class MainPanel extends JPanel {

    public MainPanel() {
        this(800, 480);
    }

    public MainPanel(int width, int height) {
        super(new GridBagLayout());
        setPreferredSize(new Dimension(width, height));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.6;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new LabyrinthPanel(), constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.4;
        constraints.weighty = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(new ProgrammingPanel(), constraints);
    }
}
