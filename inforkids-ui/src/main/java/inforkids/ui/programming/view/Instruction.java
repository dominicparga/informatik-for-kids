package inforkids.ui.programming.view;

import inforkids.core.move.SingleMove;
import inforkids.ui.programming.entities.CodeLineEntity;
import inforkids.ui.programming.model.CodeLineModel;
import inforkids.ui.programming.model.InstructionModel;
import inforkids.ui.style.ProgrammingStyleSheet;

/**
 * @author Dominic Parga Cacheiro
 */
public class Instruction extends StringCodeLine {

    private InstructionModel model;

    public Instruction(ProgrammingStyleSheet style, SingleMove move) {
        super(style, move.toString());

        model = new InstructionModel(move);
        CodeLineEntity entity = new CodeLineEntity();
        entity.setView(this);
        entity.setModel(model);
        model.setEntity(entity);
    }

    @Override
    public CodeLineModel getModel() {
        return model;
    }
}