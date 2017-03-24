package inforkids.ui.panels;

import inforkids.core.graph.Labyrinth;
import inforkids.core.player.Player;
import inforkids.entities.LabyrinthEntity;
import inforkids.entities.PlayerEntity;
import inforkids.vis.VisLabyrinth;
import inforkids.vis.VisPlayer;
import inforkids.vis.style.BasicLabyrinthStyleSheet;
import inforkids.vis.style.LabyrinthStyleSheet;
import microtrafficsim.utils.logging.EasyMarkableLogger;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;

public class LabyrinthPanel extends JPanel {

    public static final Logger logger = new EasyMarkableLogger(LabyrinthPanel.class);


    private LabyrinthEntity entity;

	public LabyrinthPanel() {
	    this(new BasicLabyrinthStyleSheet());
    }

    public LabyrinthPanel(LabyrinthStyleSheet style) {
        super(null);
        setBackground(style.getBackgroundColor());

        /* setup labyrinth entity */
        entity = new LabyrinthEntity();
        VisLabyrinth visualization = new VisLabyrinth(entity, style);
        entity.setVisualization(visualization);

        add(visualization);
    }


    public Labyrinth getLabyrinth() {
	    return entity.getLogic();
    }

    public void setLabyrinth(Labyrinth labyrinth) {

	    entity.setLogic(labyrinth);


        /* setup player entity */
        Player player = labyrinth.getPlayer();
        VisPlayer visPlayer = new VisPlayer(entity.getVisualization().getStyle().getPlayerStyle());

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setLogic(player);
        playerEntity.setVisualization(visPlayer);

        player.setEntity(playerEntity);
        visPlayer.setEntity(playerEntity);



        repaint();
    }


    public void update(long delta) {
	    entity.getVisualization().update(delta);
    }

    @Override
    protected void paintComponent(Graphics g) {
        logger.trace(getClass().getSimpleName() + ".paintComponent");
        super.paintComponent(g);


        Labyrinth labyrinth = entity.getLogic();
        if (labyrinth == null)
            return;

        /* size */
        // width per field
        int width = Math.min(getWidth() / labyrinth.getColumns(), getHeight() / labyrinth.getRows());
        // total height
        int height = width * labyrinth.getRows();
        // total width
        width *= labyrinth.getColumns();

        /* location */
        int x = getWidth() / 2 - width / 2;
        int y = getHeight() / 2 - height / 2;

        entity.getVisualization().setBounds(x, y, width, height);
    }
}