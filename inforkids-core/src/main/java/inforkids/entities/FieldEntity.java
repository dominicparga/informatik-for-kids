package inforkids.entities;

import inforkids.core.graph.Field;
import inforkids.vis.style.BasicFieldStyleSheet;

/**
 * @author Dominic Parga Cacheiro
 */
public class FieldEntity {

    private Field logic;
    private BasicFieldStyleSheet visualization;


    public Field getLogic() {
        return logic;
    }

    public void setLogic(Field logic) {
        this.logic = logic;
    }

    public BasicFieldStyleSheet getVisualization() {
        return visualization;
    }

    public void setVisualization(BasicFieldStyleSheet visualization) {
        this.visualization = visualization;
    }
}
