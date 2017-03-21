package inforkids.ui.panels;

import inforkids.ui.style.FirstStyleSheet;
import inforkids.ui.style.GUIStyleSheet;
import inforkids.vis.style.BasicLabyrinthStyleSheet;
import inforkids.vis.style.LabyrinthStyleSheet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class MainPanel extends JPanel {

    public MainPanel() {
        this(new BuildSetup());
    }

    public MainPanel(BuildSetup buildSetup) {

        super(new BorderLayout());
        setPreferredSize(new Dimension(buildSetup.width, buildSetup.height));
        setBackground(buildSetup.style.getBackgroundColor());


        /* CENTER */
        add(new LabyrinthPanel(buildSetup.style.getLabyrinthStyle()), BorderLayout.CENTER);


        /* EAST */
        ProgrammingPanel content = new ProgrammingPanel(buildSetup.style.getProgrammingStyle());
        content.setMinimumSize(new Dimension(1, 1));
        content.setMaximumSize(new Dimension(400, 1));

        JScrollPane pane = new JScrollPane(content);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBorder(new LineBorder(Color.BLACK, 6));
        add(pane, BorderLayout.LINE_END);
    }



    public static class BuildSetup {

        public int width;
        public int height;
        public GUIStyleSheet style;

        public BuildSetup() {
            width = 800;
            height = 480;
            style = new FirstStyleSheet();
        }
    }
}
