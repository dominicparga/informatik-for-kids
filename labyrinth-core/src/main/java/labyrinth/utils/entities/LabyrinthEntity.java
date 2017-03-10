package labyrinth.utils.entities;

import labyrinth.core.graph.Labyrinth;
import labyrinth.utils.components.VisLabyrinth;

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
