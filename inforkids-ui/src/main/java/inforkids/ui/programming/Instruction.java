package inforkids.ui.programming;

import inforkids.core.move.SingleMove;
import inforkids.ui.style.ProgrammingStyleSheet;

/**
 * @author Dominic Parga Cacheiro
 */
public class Instruction extends StringCodeLine {

    private SingleMove move;

    public Instruction(ProgrammingStyleSheet style, SingleMove move) {
        super(style, Type.INSTRUCTION, move.toString());

        this.move = move;
    }


    public SingleMove getMove() {
        return move;
    }
}