package inforkids.ui.programming;

import inforkids.ui.style.ProgrammingStyleSheet;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class Instruction extends CodeSnippet {

    public Instruction(ProgrammingStyleSheet style, String instruction) {
        super(style);
        setLineCount(1);

        center.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();


        JLabel label = new JLabel(instruction);
        label.setFont(style.getFont());
        label.setForeground(Color.BLACK);
        label.setAlignmentX(RIGHT_ALIGNMENT);

        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(4, 8, 4, 4);
        center.add(label, constraints);
    }
}