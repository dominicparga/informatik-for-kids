package inforkids.entities;

import inforkids.core.graph.Field;
import inforkids.vis.style.BasicFieldStyleSheet;
import inforkids.vis.style.FieldStyleSheet;

/**
 * @author Dominic Parga Cacheiro
 */
public class FieldEntity {

    private Field logic;
    private FieldStyleSheet visualization;


    public Field getLogic() {
        return logic;
    }

    public void setLogic(Field logic) {
        this.logic = logic;
    }

    public FieldStyleSheet getVisualization() {
        return visualization;
    }

    public void setVisualization(BasicFieldStyleSheet visualization) {
        this.visualization = visualization;
    }
}
