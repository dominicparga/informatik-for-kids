package inforkids.ui.programming;

import inforkids.ui.style.ProgrammingStyleSheet;
import inforkids.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Dominic Parga Cacheiro
 */
public abstract class CodeLine extends JPanel {

    /* general */
    protected final ProgrammingStyleSheet style;
    private final Type TYPE;
    private int lineNumber;

    /* components */
    protected final JPanel center;
    protected final LinkedList<JPanel> backgrounds;
    private JLabel numberJLabel;
    private JPanel codeLevelPanels;
    private ArrayList<JPanel> codeLevelPanelsBackgrounds;
    private final JButton buttonDelete;
    private final JButton buttonMoveUp;
    private final JButton buttonMoveDown;


    public CodeLine(ProgrammingStyleSheet style, Type type) {

        super(new BorderLayout());
        this.style = style;
        this.TYPE = type;
        lineNumber = 0;
        backgrounds = new LinkedList<>();


        /* add center panel */
        center = new JPanel(null);
        backgrounds.add(center);

        add(center, BorderLayout.CENTER);


        /* add west panel */
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.X_AXIS));
        backgrounds.add(westPanel);

        numberJLabel = new JLabel();
        numberJLabel.setFont(style.getFont());
        numberJLabel.setForeground(Color.BLACK);
        westPanel.add(numberJLabel);

        codeLevelPanelsBackgrounds = new ArrayList<>();
        codeLevelPanels = new JPanel();
        codeLevelPanels.setLayout(new BoxLayout(codeLevelPanels, BoxLayout.X_AXIS));
        westPanel.add(codeLevelPanels);

        add(westPanel, BorderLayout.LINE_START);


        /* add east panel */
        JPanel eastPanel = new JPanel(new GridBagLayout());
        backgrounds.add(eastPanel);
        GridBagConstraints constraints = new GridBagConstraints();

        int innervgap = 4; // gap between vertical components
        int outervgap = 12; // gap between vertical components
        int hgap = 6; // gap between horizontal components

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.insets = new Insets(outervgap / 2, 0, outervgap / 2, hgap / 2);
        buttonDelete = new JButton("X");
        eastPanel.add(buttonDelete, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.insets = new Insets(outervgap / 2, hgap / 2, innervgap / 2, 0);
        buttonMoveUp = new JButton("▲");
        eastPanel.add(buttonMoveUp, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(innervgap / 2, hgap / 2, outervgap / 2, 0);
        buttonMoveDown = new JButton("▼");
        eastPanel.add(buttonMoveDown, constraints);

        add(eastPanel, BorderLayout.LINE_END);


        /* set background initially */
        setHighlighted(false);
    }


    public void setHighlighted(boolean isHighlighted) {
        if (isHighlighted) {
            backgrounds.forEach(jPanel -> jPanel.setBackground(style.getHighlightedBackgroundColor()));
            codeLevelPanelsBackgrounds.forEach(jPanel -> jPanel.setBackground(style.getHighlightedBackgroundColor()));
        } else {
            backgrounds.forEach(jPanel -> jPanel.setBackground(style.getBackgroundColor()));
            codeLevelPanelsBackgrounds.forEach(jPanel -> jPanel.setBackground(style.getBackgroundColor()));
        }
    }

    public Type getType() {
        return TYPE;
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

        while (codeLevelPanels.getComponentCount() > codeLevel) {
            codeLevelPanels.remove(0);
            codeLevelPanelsBackgrounds.remove(0);
            codeLevelPanelsBackgrounds.remove(0);
        }

        while (codeLevelPanels.getComponentCount() < codeLevel) {

            JPanel codeLevelPanel = new JPanel(new BorderLayout());
            codeLevelPanel.setPreferredSize(new Dimension(36, 1)); // height doesn't matter due to Layout
            codeLevelPanel.setBackground(style.getBackgroundColor());
            codeLevelPanelsBackgrounds.add(codeLevelPanel);


            JPanel center = new JPanel();
            center.setBackground(style.getBackgroundColor());
            codeLevelPanelsBackgrounds.add(center);
            codeLevelPanel.add(center, BorderLayout.CENTER);


            JPanel border = new JPanel();
            border.setBackground(style.getCodeLevelBarColor());
            border.setPreferredSize(new Dimension(6, 1)); // height doesn't matter due to Layout


            codeLevelPanel.add(border, BorderLayout.LINE_START);

            codeLevelPanels.add(codeLevelPanel);
        }
    }


    public void addDeleteListener(ActionListener l) {
        buttonDelete.addActionListener(l);
    }

    public void addMoveUpListener(ActionListener l) {
        buttonMoveUp.addActionListener(l);
    }

    public void addMoveDownListener(ActionListener l) {
        buttonMoveDown.addActionListener(l);
    }


    public enum Type {
        INSTRUCTION, LOOP_START, LOOP_END
    }
}