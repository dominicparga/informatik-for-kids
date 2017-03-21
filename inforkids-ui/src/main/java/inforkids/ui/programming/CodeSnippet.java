package inforkids.ui.programming;

import inforkids.ui.style.ProgrammingStyleSheet;
import inforkids.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Dominic Parga Cacheiro
 */
public class CodeSnippet extends JPanel {

    /* general */
    protected final ProgrammingStyleSheet style;
    private int lineNumber;
    private int lineCount;

    /* components */
    protected final JPanel center;
    private JLabel number;
    private final CodeSnippetButton buttonInstruction;
    private final CodeSnippetButton buttonLoop;
    private final CodeSnippetButton buttonCondition;


    public CodeSnippet(ProgrammingStyleSheet style) {

        super(new BorderLayout());
        this.style = style;
        lineNumber = 0;
        lineCount = 1;


        /* add center panel */
        center = new JPanel(null);
        center.setBackground(Color.CYAN);
        add(center, BorderLayout.CENTER);


        /* add bottom buttons */
        JPanel bottom = new JPanel(null);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));

        buttonInstruction = new CodeSnippetButton(this);
        buttonInstruction.setText("웃");
        bottom.add(buttonInstruction);

        buttonLoop = new CodeSnippetButton(this);
        buttonLoop.setText("⟲");
        bottom.add(buttonLoop);

        buttonCondition = new CodeSnippetButton(this);
        buttonCondition.setText("?");
        bottom.add(buttonCondition);

        add(bottom, BorderLayout.PAGE_END);


        /* add west panel */
        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(Color.GREEN);

        number = new JLabel();
        number.setFont(style.getFont());
        number.setForeground(Color.BLACK);
        number.setAlignmentX(RIGHT_ALIGNMENT);
        left.add(number, BorderLayout.LINE_START);

        JPanel border = new JPanel();
        border.setBackground(Color.BLACK);
        border.setPreferredSize(new Dimension(5, 1)); // height doesn't matter due to BorderLayout
        left.add(border, BorderLayout.LINE_END);

        add(left, BorderLayout.LINE_START);
    }


    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber, int digits) {
        this.lineNumber = lineNumber;
        number.setText(StringUtils.toString(lineNumber, digits) + " ");
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }


    /*
    |==================|
    | action listeners |
    |==================|
    */
    public void addActionListenerForInstructions(ActionListener l) {
        buttonInstruction.addActionListener(l);
    }

    public void addActionListenerForLoops(ActionListener l) {
        buttonLoop.addActionListener(l);
    }

    public void addActionListenerForConditions(ActionListener l) {
        buttonCondition.addActionListener(l);
    }


    public class CodeSnippetButton extends JButton {
        public final CodeSnippet SNIPPET;

        public CodeSnippetButton(CodeSnippet snippet) {
            this.SNIPPET = snippet;
        }
    }
}