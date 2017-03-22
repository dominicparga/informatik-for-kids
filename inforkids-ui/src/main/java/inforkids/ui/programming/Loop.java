package inforkids.ui.programming;

import inforkids.ui.components.CyclingSpinnerNumberModel;
import inforkids.ui.style.ProgrammingStyleSheet;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class Loop extends CodeLine {

    private final boolean isStart;
    private Loop counterpart;
    private static final Font FONT = new Font("Dialog", Font.PLAIN, 20);
    private static int ID = 0;


    private Loop(ProgrammingStyleSheet style, boolean isStart) {

        super(style);
        this.isStart = isStart;
    }

    public static Loop createStart(ProgrammingStyleSheet style) {

        Loop loop = new Loop(style, true);
        loop.center.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();


        JLabel label = new JLabel(ID + "Wiederhole ");
        label.setFont(FONT);
        label.setForeground(Color.BLACK);

        constraints.weightx = 0;
        loop.center.add(label, constraints);


        JSpinner spinner = new JSpinner(new CyclingSpinnerNumberModel(0, 0, 42, 1));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);

        loop.center.add(spinner);


        label = new JLabel(" Mal [");
        label.setFont(FONT);
        label.setForeground(Color.BLACK);

        loop.center.add(label);


        constraints.weightx = 1;
        loop.center.add(new JPanel(null), constraints);


        return loop;
    }

    public static Loop createEnd(ProgrammingStyleSheet style) {

        Loop loop = new Loop(style, false);
        loop.center.setLayout(new BorderLayout());


        JLabel label = new JLabel((ID++) + "]");
        label.setFont(FONT);
        label.setForeground(Color.BLACK);

        loop.center.add(label, BorderLayout.LINE_START);


        loop.center.add(new JPanel(null), BorderLayout.CENTER);


        return loop;
    }


    public Loop getCounterpart() {
        return counterpart;
    }

    public void setCounterpart(Loop counterpart) {
        this.counterpart = counterpart;
    }

    public boolean isStart() {
        return isStart;
    }
}