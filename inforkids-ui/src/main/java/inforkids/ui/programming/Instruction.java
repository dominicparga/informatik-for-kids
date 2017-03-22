package inforkids.ui.programming;

import inforkids.core.move.SingleMove;
import inforkids.ui.style.ProgrammingStyleSheet;

/**
 * @author Dominic Parga Cacheiro
 */
public class Instruction extends StringCodeLine {

    public Instruction(ProgrammingStyleSheet style, SingleMove move) {
        super(style, move.toString());
    }
}