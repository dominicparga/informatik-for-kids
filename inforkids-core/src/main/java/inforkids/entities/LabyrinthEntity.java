package inforkids.entities;

import inforkids.core.graph.Labyrinth;
import inforkids.vis.VisLabyrinth;

/**
 * @author Dominic Parga Cacheiro
 */
public class LabyrinthEntity {

    private Labyrinth logic;
    private VisLabyrinth visualization;


    public Labyrinth getLogic() {
        return logic;
    }

    public void setLogic(Labyrinth logic) {
        this.logic = logic;
    }

    public VisLabyrinth getVisualization() {
        return visualization;
    }

    public void setVisualization(VisLabyrinth visualization) {
        this.visualization = visualization;
    }
}
