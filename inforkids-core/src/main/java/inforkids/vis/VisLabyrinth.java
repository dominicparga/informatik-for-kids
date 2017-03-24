package inforkids.vis;

import inforkids.core.graph.Field;
import inforkids.core.graph.Labyrinth;
import inforkids.core.player.Player;
import inforkids.entities.LabyrinthEntity;
import inforkids.vis.style.LabyrinthStyleSheet;
import microtrafficsim.utils.logging.EasyMarkableLogger;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class VisLabyrinth extends JComponent {

    public static final Logger logger = new EasyMarkableLogger(VisLabyrinth.class);


    private LabyrinthEntity labyrinthEntity;
    private final LabyrinthStyleSheet style;

    public VisLabyrinth(LabyrinthEntity entity, LabyrinthStyleSheet style) {
        this.labyrinthEntity = entity;
        this.style = style;
    }


    public LabyrinthStyleSheet getStyle() {
        return style;
    }


    public void update(long delta) {

        Labyrinth labyrinth = labyrinthEntity.getLogic();
        if (labyrinth == null)
            return;

        Player player = labyrinth.getPlayer();
        VisPlayer visPlayer = player.getEntity().getVisualization();
        visPlayer.update(delta);
    }

    @Override
    protected void paintComponent(Graphics g) {
        logger.trace(getClass().getSimpleName() + ".paintComponent");
        super.paintComponent(g);

        Labyrinth labyrinth = labyrinthEntity.getLogic();
        Player player = labyrinth.getPlayer();
        int playerX = 0;
        int playerY = 0;

        int width = getWidth() / labyrinth.getColumns();
        int height = getHeight() / labyrinth.getRows();
        for (int row = 0; row < labyrinth.getRows(); row++) {
            for (int column = 0; column < labyrinth.getColumns(); column++) {

                /* get field */
                Field field = labyrinth.get(row, column);

                /* draw field */
                int x = column * width;
                int y = row * height;
                g.drawImage(style.getFieldStyle().get(field), x, y, width, height, null);

                /* draw player */
                if (field == player.getField()) {
                    playerX = x;
                    playerY = y;
                }
            }
        }

        VisPlayer visPlayer = player.getEntity().getVisualization();
        playerX += width * visPlayer.getWalkingXFraction();
        playerY += height * visPlayer.getWalkingYFraction();
        g.drawImage(style.getPlayerStyle().get(player), playerX, playerY, width, height, null);
    }
}
