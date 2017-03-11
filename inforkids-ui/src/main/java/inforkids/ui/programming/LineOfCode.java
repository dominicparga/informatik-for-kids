package inforkids.ui.programming;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class LineOfCode extends JPanel {

    public LineOfCode(String lineNo) {
        super(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();


        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel label = new JLabel(lineNo);
        label.setFont(new Font("Courier", Font.BOLD, 24));
        label.setForeground(Color.BLACK);
        label.setAlignmentX(RIGHT_ALIGNMENT);
        add(label, constraints);


        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.weightx = 1;
        JPanel gap = new JPanel();
        add(gap, constraints);


        constraints.fill = GridBagConstraints.NONE;
    }
}
