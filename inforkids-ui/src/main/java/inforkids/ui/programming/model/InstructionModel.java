package inforkids.ui.programming.model;

import inforkids.core.move.SingleMove;
import inforkids.ui.programming.entities.CodeLineEntity;
import inforkids.ui.programming.view.Instruction;

/**
 * @author Dominic Parga Cacheiro
 */
public class InstructionModel extends CodeLineModel {

    private SingleMove move;
    private CodeLineEntity<Instruction, InstructionModel> entity;

    public InstructionModel(SingleMove move) {
        super(Type.INSTRUCTION);
        this.move = move;
    }


    public SingleMove getMove() {
        return move;
    }


    /*
    |===================|
    | (i) CodeLineModel |
    |===================|
    */
    @Override
    public InstructionModel deepcopy() {

        InstructionModel model = new InstructionModel(move);

        CodeLineEntity<Instruction, InstructionModel> entity = new CodeLineEntity<>();
        entity.setView(getEntity().getView());
        entity.setModel(model);
        model.setEntity(entity);

        return model;
    }

    @Override
    public CodeLineEntity<Instruction, InstructionModel> getEntity() {
        return entity;
    }

    public void setEntity(CodeLineEntity<Instruction, InstructionModel> entity) {
        this.entity = entity;
    }
}