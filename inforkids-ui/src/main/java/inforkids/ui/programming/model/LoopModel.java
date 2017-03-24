package inforkids.ui.programming.model;

import inforkids.ui.programming.entities.CodeLineEntity;
import inforkids.ui.programming.view.Loop;

/**
 * @author Dominic Parga Cacheiro
 */
public class LoopModel extends CodeLineModel {

    private int loopCount;
    private CodeLineEntity<Loop, LoopModel> entity;


    public LoopModel(Type type) {
        super(type);
        loopCount = 0;
    }


    public int getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }

    public boolean isStart() {
        return getType() == CodeLineModel.Type.LOOP_START;
    }


    /*
    |===================|
    | (i) CodeLineModel |
    |===================|
    */
    @Override
    public LoopModel deepcopy() {

        LoopModel model = new LoopModel(getType());
        model.setLoopCount(loopCount);

        CodeLineEntity<Loop, LoopModel> entity = new CodeLineEntity<>();
        entity.setView(getEntity().getView());
        entity.setModel(model);
        model.setEntity(entity);

        return model;
    }

    @Override
    public CodeLineEntity<Loop, LoopModel> getEntity() {
        return entity;
    }

    public void setEntity(CodeLineEntity<Loop, LoopModel> entity) {
        this.entity = entity;
    }
}