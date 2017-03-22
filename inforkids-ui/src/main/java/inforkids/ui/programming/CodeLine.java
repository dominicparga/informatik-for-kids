package inforkids.ui.programming;

import inforkids.ui.style.ProgrammingStyleSheet;
import inforkids.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Dominic Parga Cacheiro
 */
public abstract class CodeLine extends JPanel {

    /* general */
    protected final ProgrammingStyleSheet style;
    private int lineNumber;

    /* components */
    protected final JPanel center;
    private JLabel numberJLabel;
    private JPanel codeLevelPanels;
    private final JButton buttonDelete;
    private final JButton buttonMoveDown;


    public CodeLine(ProgrammingStyleSheet style) {

        super(new BorderLayout());
        this.style = style;
        lineNumber = 0;


        /* add center panel */
        center = new JPanel(null);
        center.setBackground(Color.CYAN);

        add(center, BorderLayout.CENTER);


        /* add west panel */
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.X_AXIS));
        westPanel.setBackground(Color.GREEN);

        numberJLabel = new JLabel();
        numberJLabel.setFont(style.getFont());
        numberJLabel.setForeground(Color.BLACK);
        westPanel.add(numberJLabel);

        codeLevelPanels = new JPanel();
        codeLevelPanels.setLayout(new BoxLayout(codeLevelPanels, BoxLayout.X_AXIS));
        westPanel.add(codeLevelPanels);

        add(westPanel, BorderLayout.LINE_START);


        /* add east panel */
        JPanel eastPanel = new JPanel(new GridLayout(2, 1));

        buttonDelete = new JButton("X");
        eastPanel.add(buttonDelete);

        buttonMoveDown = new JButton("▼");
        eastPanel.add(buttonMoveDown);

        add(eastPanel, BorderLayout.LINE_END);
    }


    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber, int digits) {
        this.lineNumber = lineNumber;
        numberJLabel.setText(StringUtils.toString(lineNumber, digits) + " ");
    }

    public int getCodeLevel() {
        return codeLevelPanels.getComponentCount();
    }

    public void setCodeLevel(int codeLevel) {

        while (codeLevelPanels.getComponentCount() > codeLevel)
            codeLevelPanels.remove(0);

        while (codeLevelPanels.getComponentCount() < codeLevel) {
            JPanel border = new JPanel();
            border.setBackground(Color.PINK);
            border.setPreferredSize(new Dimension(36, 1)); // height doesn't matter due to Layout
            codeLevelPanels.add(border);
        }
    }


    public void addDeleteListener(ActionListener l) {
        buttonDelete.addActionListener(l);
    }

    public void addMoveDownListener(ActionListener l) {
        buttonMoveDown.addActionListener(l);
    }
}