package inforkids.ui.programming.view;

import inforkids.ui.programming.model.CodeLineModel;
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

    /* components */
    protected final JPanel center;
    protected final LinkedList<JPanel> backgrounds;
    private JLabel numberJLabel;
    private JPanel codeLevelPanels;
    private ArrayList<JPanel> codeLevelPanelsBackgrounds;
    private final JButton buttonDelete;
    private final JButton buttonMoveUp;
    private final JButton buttonMoveDown;


    public CodeLine(ProgrammingStyleSheet style) {

        super(new GridBagLayout());
        this.style = style;
        backgrounds = new LinkedList<>();

        GridBagConstraints constraints = new GridBagConstraints();


        /* add center panel */
        center = new JPanel(null);
        backgrounds.add(center);

        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0;
        add(center, constraints);


        /* add gap panel */
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 1;
        JPanel gap = new JPanel(null);
        backgrounds.add(gap);
        add(gap, constraints);


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

        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        add(westPanel, constraints);


        /* add east panel with buttons */
        JPanel eastPanel = new JPanel(new GridBagLayout());
        backgrounds.add(eastPanel);
        constraints = new GridBagConstraints();

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


        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.weightx = 0;
        add(eastPanel, constraints);


        /* set background initially */
        setHighlighted(false);
    }


    public abstract CodeLineModel getModel();


    public void setHighlighted(boolean isHighlighted) {
        if (isHighlighted) {
            backgrounds.forEach(jPanel -> jPanel.setBackground(style.getHighlightedBackgroundColor()));
            codeLevelPanelsBackgrounds.forEach(jPanel -> jPanel.setBackground(style.getHighlightedBackgroundColor()));
        } else {
            backgrounds.forEach(jPanel -> jPanel.setBackground(style.getBackgroundColor()));
            codeLevelPanelsBackgrounds.forEach(jPanel -> jPanel.setBackground(style.getBackgroundColor()));
        }
    }

    public void setLineNumber(int lineNumber, int digits) {
        getModel().setLineNumber(lineNumber);
        numberJLabel.setText(StringUtils.toString(lineNumber, digits) + " ");
    }

    public void setCodeLevel(int codeLevel) {

        while (getModel().getCodeLevel() > codeLevel) {

            getModel().decCodeLevel();


            codeLevelPanels.remove(0);
            codeLevelPanelsBackgrounds.remove(0);
            codeLevelPanelsBackgrounds.remove(0);
        }

        while (getModel().getCodeLevel() < codeLevel) {

            getModel().incCodeLevel();


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
}