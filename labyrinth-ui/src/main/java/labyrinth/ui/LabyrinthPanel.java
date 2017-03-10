package labyrinth.ui;

import labyrinth.core.graph.Labyrinth;
import labyrinth.core.graph.impl.BasicLabyrinth;
import labyrinth.utils.components.VisLabyrinth;
import labyrinth.utils.entities.LabyrinthEntity;
import labyrinth.vis.style.BasicImageStyleSheet;
import microtrafficsim.utils.logging.EasyMarkableLogger;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;

public class LabyrinthPanel extends JPanel {

    public static final Logger logger = new EasyMarkableLogger(LabyrinthPanel.class);


    private LabyrinthEntity entity;

	public LabyrinthPanel() {
        super(null);
        setBackground(Color.DARK_GRAY);

        entity = new LabyrinthEntity();
        Labyrinth logic = new BasicLabyrinth(5, 6, "WWWWWW WGGGWW WGGGGW WGWWGW WWWWWW");
        VisLabyrinth visualization = new VisLabyrinth(entity, new BasicImageStyleSheet());
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