package inforkids.ui.programming.model;

import inforkids.ui.programming.entities.CodeLineEntity;

/**
 * @author Dominic Parga Cacheiro
 */
public abstract class CodeLineModel {

    private final Type TYPE;
    private int lineNumber;
    private int codeLevel;


    public CodeLineModel(Type type) {
        TYPE = type;
        lineNumber = 0;
        codeLevel = 0;
    }


    public abstract CodeLineModel deepcopy();

    public abstract CodeLineEntity getEntity();


    public Type getType() {
        return TYPE;
    }

    public int getCodeLevel() {
        return codeLevel;
    }

    public void incCodeLevel() {
        codeLevel++;
    }

    public void decCodeLevel() {
        codeLevel--;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }


    public enum Type {
        INSTRUCTION, LOOP_START, LOOP_END
    }
}