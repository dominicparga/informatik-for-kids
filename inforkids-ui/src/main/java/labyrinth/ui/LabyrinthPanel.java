package labyrinth.ui;

import labyrinth.core.graph.Labyrinth;
import labyrinth.core.graph.impl.BasicLabyrinth;
import labyrinth.vis.VisLabyrinth;
import labyrinth.entities.LabyrinthEntity;
import labyrinth.vis.style.BasicGUIStyleSheet;
import labyrinth.vis.style.GUIStyleSheet;
import microtrafficsim.utils.logging.EasyMarkableLogger;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;

public class LabyrinthPanel extends JPanel {

    public static final Logger logger = new EasyMarkableLogger(LabyrinthPanel.class);


    private LabyrinthEntity entity;

	public LabyrinthPanel() {
	    this(new BasicGUIStyleSheet());
    }

    public LabyrinthPanel(GUIStyleSheet style) {
        super(null);
        setBackground(style.getBackgroundColor());
        entity = new LabyrinthEntity();
        Labyrinth logic = new BasicLabyrinth(5, 6, "WWWWWW WGGGWW WGGGGW WGWWGW WWWWWW");
        VisLabyrinth visualization = new VisLabyrinth(entity, style);
        entity.setLogic(logic);
        entity.setVisualization(visualization);

        add(visualization);
    }

    @Override
    protected void paintComponent(Graphics g) {
        logger.trace(getClass().getSimpleName() + ".paintComponent");
        super.paintComponent(g);

        /* size */
        // width per field
        int width = Math.min(getWidth() / entity.getLogic().getColumns(), getHeight() / entity.getLogic().getRows());
        // total height
        int height = width * entity.getLogic().getRows();
        // total width
        width *= entity.getLogic().getColumns();

        /* location */
        int x = getWidth() / 2 - width / 2;
        int y = getHeight() / 2 - height / 2;

        entity.getVisualization().setBounds(x, y, width, height);
    }
}