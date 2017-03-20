package inforkids.ui;

import inforkids.ui.programming.LineOfCode;
import inforkids.utils.io.ImageLoader;
import microtrafficsim.math.MathUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class ProgrammingPanel extends JPanel implements Scrollable {

    private JButton addJButton;
    private JPanel  lines;
    private Dimension scrollDimension;

    public ProgrammingPanel() {
        super();

        /* general */
        scrollDimension = new Dimension();


        /* layout components */
        setLayout(new GridBagLayout());
        setBackground(Style.PROGRAMMING.BACKGROUND_COLOR);

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
        lines.add(new LineOfCode(" 6 dddddddkdjflöasjföl"), constraints);
        lines.add(new LineOfCode("12"), constraints);
        lines.add(new LineOfCode("24"), constraints);
        lines.add(new LineOfCode("42"), constraints);
        getRootPane().revalidate();
    }


    /*
    |================|
    | (i) Scrollable |
    |================|
    */
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        scrollDimension.width = MathUtils.clamp(
                (int) getPreferredSize().getWidth(),
                (int) getMinimumSize().getWidth(),
                (int) getMaximumSize().getWidth());
        scrollDimension.height = MathUtils.clamp(
                (int) getPreferredSize().getHeight(),
                (int) getMinimumSize().getHeight(),
                (int) getMaximumSize().getHeight()
        );
        return scrollDimension;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 1;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 1;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
