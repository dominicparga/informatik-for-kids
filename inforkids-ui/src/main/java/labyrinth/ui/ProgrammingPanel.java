package labyrinth.ui;

import labyrinth.utils.io.ImageLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class ProgrammingPanel extends JPanel {

    private JButton addJButton;

    public ProgrammingPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.BLACK, 6));

        addJButton = new JButton("+");
        addJButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(addJButton);
        addJButton.addActionListener(e -> System.out.println("press"));
    }

    private JButton createAddButton() {

        Image image = ImageLoader.loadImage(getClass(), "/undefined.png");
        image = image.getScaledInstance(42, 42, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);


        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setBorder(null);
        //button.setFocusable(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setContentAreaFilled(false);
        button.setIcon(icon);
        button.setRolloverIcon(icon);
        button.setPressedIcon(icon);
        button.setDisabledIcon(icon);

        return button;
    }
}
