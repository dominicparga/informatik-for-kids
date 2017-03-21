package inforkids.ui.panels;

import inforkids.ui.programming.CodeSnippet;
import inforkids.ui.programming.Instruction;
import inforkids.ui.style.ProgrammingStyleSheet;
import inforkids.utils.functional.Procedure;
import microtrafficsim.math.MathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Dominic Parga Cacheiro
 */
public class ProgrammingPanel extends JPanel implements Scrollable {

    /* general */
    private ProgrammingStyleSheet style;
    private final Dimension dimension;
    /* needed data structures */
    private final ArrayList<CodeSnippet> snippets;
    /* user input */
    private ReentrantLock guiLock;
    private AtomicBoolean isAddingCodeSnippet;

    public ProgrammingPanel(ProgrammingStyleSheet style) {
        super();

        /* general */
        this.style = style;
        dimension = new Dimension();
        /* needed data structures */
        snippets = new ArrayList<>();
        /* user input */
        guiLock = new ReentrantLock();
        isAddingCodeSnippet = new AtomicBoolean(false);


        /* layout components */
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(style.getBackgroundColor());

        CodeSnippet emptyCodeSnippet = new CodeSnippet(style);
        addActionListeners(emptyCodeSnippet);
        add(emptyCodeSnippet);
        snippets.add(emptyCodeSnippet);
    }


    /*
    |=======|
    | utils |
    |=======|
    */
    private void createAndAddInstruction(ActionEvent event) {

        /* prepare new instruction */
        int lineNumber = 1 + ((CodeSnippet.CodeSnippetButton) event.getSource()).SNIPPET.getLineNumber();
        Instruction instruction = new Instruction(style, "instruction " + lineNumber);
        addActionListeners(instruction);

        /* add new instruction */
        addSnippetAndUpdate(instruction);
    }

    private void createAndAddLoop(ActionEvent event) {

    }

    private void createAndAddCondition(ActionEvent event) {

    }

    private void addActionListeners(CodeSnippet codeSnippet) {
        codeSnippet.addActionListenerForInstructions(e -> changeGUI(() -> createAndAddInstruction(e)));
        codeSnippet.addActionListenerForLoops(e -> changeGUI(() -> createAndAddLoop(e)));
        codeSnippet.addActionListenerForConditions(e -> changeGUI(() -> createAndAddCondition(e)));
    }

    private void addSnippetAndUpdate(CodeSnippet snippet) {
        add(snippet);
        snippets.add(snippet);
        updateLineNumbers();
        getRootPane().revalidate();
    }

    private void updateLineNumbers() {

        int digits = 1 + inforkids.utils.MathUtils.getDigitCountDecimal(snippets.size() - 1);

        int lineNumber = 1;
        for (int i = 1; i < snippets.size(); i++) {
            CodeSnippet snippet = snippets.get(i);
            snippet.setLineNumber(lineNumber, digits);
            lineNumber += snippet.getLineCount();
        }
    }

    /*
    |======================|
    | utils multithreading |
    |======================|
    */
    private void changeGUI(Procedure procedure) {
        if (guiLock.tryLock()) {

            if (isAddingCodeSnippet.compareAndSet(false, true)) {
                new Thread(() -> {

                    procedure.invoke();

                    isAddingCodeSnippet.set(false);
                }).start();
            }

            guiLock.unlock();
        }
    }


    /*
    |================|
    | (i) Scrollable |
    |================|
    */
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        dimension.width = MathUtils.clamp(
                (int) getPreferredSize().getWidth(),
                (int) getMinimumSize().getWidth(),
                (int) getMaximumSize().getWidth());
        dimension.height = MathUtils.clamp(
                (int) getPreferredSize().getHeight(),
                (int) getMinimumSize().getHeight(),
                (int) getMaximumSize().getHeight()
        );
        return dimension;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 1; // todo play around
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 1; // todo play around
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
