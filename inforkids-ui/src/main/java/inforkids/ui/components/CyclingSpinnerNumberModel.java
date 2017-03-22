package inforkids.ui.components;

import javax.swing.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class CyclingSpinnerNumberModel extends SpinnerNumberModel {

    public CyclingSpinnerNumberModel(Number value, Comparable minimum, Comparable maximum, Number stepSize) {
        super(value, minimum, maximum, stepSize);
    }

    public CyclingSpinnerNumberModel(int value, int minimum, int maximum, int stepSize) {
        super(value, minimum, maximum, stepSize);
    }

    public CyclingSpinnerNumberModel(double value, double minimum, double maximum, double stepSize) {
        super(value, minimum, maximum, stepSize);
    }

    public CyclingSpinnerNumberModel() {
        super();
    }

    @Override
    public Object getNextValue() {
        if (getValue().equals(getMaximum()))
            return getMinimum();

        return super.getNextValue();
    }

    @Override
    public Object getPreviousValue() {
        if (getValue().equals(getMinimum()))
            return getMaximum();

        return super.getPreviousValue();
    }
}
