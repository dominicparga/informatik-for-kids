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
    private JSpinner spinner;


    private Loop(ProgrammingStyleSheet style, Type type, boolean isStart) {

        super(style, type);
        this.isStart = isStart;
    }

    public static Loop createStart(ProgrammingStyleSheet style) {

        Loop loop = new Loop(style, Type.LOOP_START, true);
        loop.center.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();


        JLabel label = new JLabel("Wiederhole ");
        label.setFont(FONT);
        label.setForeground(Color.BLACK);

        constraints.weightx = 0;
        loop.center.add(label, constraints);


        loop.spinner = new JSpinner(new CyclingSpinnerNumberModel(0, 0, 42, 1));
        JSpinner.DefaultEditor editor = ((JSpinner.DefaultEditor) loop.spinner.getEditor());
        loop.spinner.setBorder(null);
        editor.getTextField().setEditable(false);
        editor.setBackground(style.getBackgroundColor());
        editor.getTextField().setBackground(style.getBackgroundColor());

        loop.center.add(loop.spinner);


        label = new JLabel(" Mal (    ");
        label.setFont(FONT);
        label.setForeground(Color.BLACK);

        loop.center.add(label);


        constraints.weightx = 1;
        loop.center.add(new JPanel(null), constraints);


        return loop;
    }

    public static Loop createEnd(ProgrammingStyleSheet style) {

        Loop loop = new Loop(style, Type.LOOP_END, false);
        loop.center.setLayout(new BorderLayout());


        JLabel label = new JLabel(")");
        label.setFont(FONT);
        label.setForeground(Color.BLACK);

        loop.center.add(label, BorderLayout.LINE_START);


        JPanel gap = new JPanel(null);
        loop.backgrounds.add(gap);
        loop.center.add(gap, BorderLayout.CENTER);


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

    public int getLoopCount() {
        return (int) spinner.getValue();
    }
}