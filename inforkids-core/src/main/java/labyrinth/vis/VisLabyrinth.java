package labyrinth.vis;

import labyrinth.core.graph.Labyrinth;
import labyrinth.entities.LabyrinthEntity;
import labyrinth.vis.style.GUIStyleSheet;
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
    private final GUIStyleSheet style;

    public VisLabyrinth(LabyrinthEntity entity, GUIStyleSheet style) {
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
