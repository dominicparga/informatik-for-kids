package inforkids.ui;

import inforkids.ui.programming.LineOfCode;
import inforkids.utils.io.ImageLoader;
import microtrafficsim.utils.concurrency.delegation.StaticThreadDelegator;
import microtrafficsim.utils.concurrency.delegation.ThreadDelegator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class ProgrammingPanel extends JPanel {

    private JButton addJButton;
    private JPanel  lines;

    public ProgrammingPanel() {
        super();
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.BLACK, 6));


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty = 0;

        /* add line panel */
        lines = new JPanel(new GridBagLayout());
//        constraints.insets = new Insets(12, 12, 12, 6);
        add(lines, constraints);


        /* add button */
        addJButton = createAddButton();
//        constraints.insets = new Insets(12, 12, 12, 6);
        add(addJButton, constraints);


        /* add empty panel */
        JPanel gap = new JPanel();
        gap.setBackground(Color.RED);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
//        constraints.insets = new Insets(12, 12, 12, 6);
        add(gap, constraints);
    }

    private JButton createAddButton() {

        JButton button;

        boolean withIcon = false;

        if (!withIcon) {
            button = new JButton("+");
        } else {
            Image image = ImageLoader.loadImage(getClass(), "/undefined.png");
            image = image.getScaledInstance(42, 42, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);


            button = new JButton();
            button.setBorderPainted(false);
            button.setBorder(null);
            //button.setFocusable(false);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setContentAreaFilled(false);
            button.setIcon(icon);
            button.setRolloverIcon(icon);
            button.setPressedIcon(icon);
            button.setDisabledIcon(icon);
        }

        button.addActionListener(e -> createAndAddLine());
        return button;
    }

    private void createAndAddLine() {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(6, 6, 6, 6);

//        lines.add(createAddButton(), constraints);
        lines.add(new LineOfCode(" 6"), constraints);
        lines.add(new LineOfCode("12"), constraints);
        lines.add(new LineOfCode("24"), constraints);
        lines.add(new LineOfCode("42"), constraints);
        updateUI();
    }
}
