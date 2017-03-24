package inforkids.ui.programming.view;

import inforkids.ui.components.CyclingSpinnerNumberModel;
import inforkids.ui.programming.entities.CodeLineEntity;
import inforkids.ui.programming.model.CodeLineModel;
import inforkids.ui.programming.model.InstructionModel;
import inforkids.ui.programming.model.LoopModel;
import inforkids.ui.style.ProgrammingStyleSheet;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class Loop extends CodeLine {

    private final LoopModel model;
    private Loop counterpart;
    private static final Font FONT = new Font("Dialog", Font.PLAIN, 20);
    private JSpinner spinner;


    private Loop(ProgrammingStyleSheet style, CodeLineModel.Type type) {
        super(style);
        model = new LoopModel(type);
        CodeLineEntity<Loop, LoopModel> entity = new CodeLineEntity<>();
        entity.setView(this);
        entity.setModel(model);
        model.setEntity(entity);
    }

    public static Loop createStart(ProgrammingStyleSheet style) {

        Loop loop = new Loop(style, CodeLineModel.Type.LOOP_START);
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

        loop.spinner.addChangeListener(e -> {
            JSpinner source = (JSpinner) e.getSource();
            int value = (int) source.getValue();
            loop.getModel().setLoopCount(value);
        });

        loop.center.add(loop.spinner);


        label = new JLabel(" Mal (    ");
        label.setFont(FONT);
        label.setForeground(Color.BLACK);

        loop.center.add(label);


        constraints.weightx = 1;
        JPanel gap = new JPanel(null);
        loop.backgrounds.add(gap);
        loop.center.add(gap, constraints);


        return loop;
    }

    public static Loop createEnd(ProgrammingStyleSheet style) {

        Loop loop = new Loop(style, CodeLineModel.Type.LOOP_END);
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


    @Override
    public LoopModel getModel() {
        return model;
    }
}