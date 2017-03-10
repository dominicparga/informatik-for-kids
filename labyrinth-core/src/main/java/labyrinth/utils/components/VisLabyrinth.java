package labyrinth.utils.components;

import labyrinth.core.graph.Labyrinth;
import labyrinth.utils.entities.LabyrinthEntity;
import labyrinth.vis.style.ImageStyleSheet;
import microtrafficsim.utils.logging.EasyMarkableLogger;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class VisLabyrinth extends JComponent {

    public static final Logger logger = new EasyMarkableLogger(VisLabyrinth.class);


    private LabyrinthEntity entity;
    private final ImageStyleSheet style;

    public VisLabyrinth(LabyrinthEntity entity, ImageStyleSheet style) {
        this.entity = entity;
        this.style = style;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Labyrinth labyrinth = entity.getLogic();
        int width = getWidth() / labyrinth.getColumns();
        int height = getHeight() / labyrinth.getRows();
        for (int row = 0; row < labyrinth.getRows(); row++) {
            for (int column = 0; column < labyrinth.getColumns(); column++) {
                int x = column * width;
                int y = row * height;
                g.drawImage(style.get(labyrinth.get(row, column)), x, y, width, height, null);
            }
        }
    }
}
