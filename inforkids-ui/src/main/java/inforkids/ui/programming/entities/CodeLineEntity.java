package inforkids.ui.programming.entities;

import inforkids.ui.programming.model.CodeLineModel;
import inforkids.ui.programming.view.CodeLine;

/**
 * @author Dominic Parga Cacheiro
 */
public class CodeLineEntity<V extends CodeLine, M extends CodeLineModel> {

    private V view;
    private M model;


    public V getView() {
        return view;
    }

    public void setView(V view) {
        this.view = view;
    }

    public M getModel() {
        return model;
    }

    public void setModel(M model) {
        this.model = model;
    }
}