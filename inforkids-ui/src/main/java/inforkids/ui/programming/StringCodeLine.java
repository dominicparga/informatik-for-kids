package inforkids.ui.programming;

import inforkids.ui.style.ProgrammingStyleSheet;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class StringCodeLine extends CodeLine {

    public StringCodeLine(ProgrammingStyleSheet style, String text) {
        super(style);

        center.setLayout(new BorderLayout());


        JLabel label = new JLabel(text);
        label.setFont(new Font("Dialog", Font.PLAIN, 20));
        label.setForeground(Color.BLACK);

        center.add(label, BorderLayout.CENTER);
    }
}