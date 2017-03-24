package inforkids.entities;

import inforkids.core.player.Player;
import inforkids.vis.VisPlayer;

/**
 * @author Dominic Parga Cacheiro
 */
public class PlayerEntity {

    private Player logic;
    private VisPlayer visualization;


    public Player getLogic() {
        return logic;
    }

    public void setLogic(Player logic) {
        this.logic = logic;
    }

    public VisPlayer getVisualization() {
        return visualization;
    }

    public void setVisualization(VisPlayer visualization) {
        this.visualization = visualization;
    }
}