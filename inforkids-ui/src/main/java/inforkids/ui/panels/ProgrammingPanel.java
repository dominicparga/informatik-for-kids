package inforkids.ui.panels;

import inforkids.core.move.SingleMove;
import inforkids.ui.programming.model.CodeLineModel;
import inforkids.ui.programming.model.InstructionModel;
import inforkids.ui.programming.view.CodeLine;
import inforkids.ui.programming.view.Instruction;
import inforkids.ui.programming.view.Loop;
import inforkids.ui.style.ProgrammingStyleSheet;
import inforkids.utils.functional.Procedure;
import microtrafficsim.math.MathUtils;
import microtrafficsim.utils.logging.EasyMarkableLogger;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Dominic Parga Cacheiro
 */
public class ProgrammingPanel extends JPanel {

    public static final Logger logger = new EasyMarkableLogger(ProgrammingPanel.class);


    /* general */
    private ProgrammingStyleSheet style;
    private final Dimension dimension;
    /* needed data structures */
    private final ArrayList<CodeLine> codeLines;
    private final JPanel panelOfLines;
    /* user input */
    private ReentrantLock guiLock;
    private AtomicBoolean isGUIEnabled;

    public ProgrammingPanel(ProgrammingStyleSheet style) {
        super();

        /* general */
        this.style = style;
        dimension = new Dimension();
        /* needed data structures */
        codeLines = new ArrayList<>();
        /* user input */
        guiLock = new ReentrantLock();
        isGUIEnabled = new AtomicBoolean(true);


        /* own layout */
        setLayout(new BorderLayout());
        setBackground(style.getBackgroundColor());


        /* scroll content */
        JPanel content = new ScrollablePanel(new BorderLayout());
        content.setMinimumSize(new Dimension(1, 1));
        content.setMaximumSize(new Dimension(480, 1));

        /* scroll pane */
        JScrollPane pane = new JScrollPane(content);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        pane.setBorder(style.getBorder());
        pane.setBackground(style.getBackgroundColor());
        pane.getViewport().setBackground(style.getBackgroundColor());

        add(pane, BorderLayout.CENTER);


        /* programming lines */
        panelOfLines = new JPanel(null);
        panelOfLines.setLayout(new BoxLayout(panelOfLines, BoxLayout.Y_AXIS));
        panelOfLines.setBackground(style.getBackgroundColor());
        content.add(panelOfLines, BorderLayout.CENTER);


        /* add gap panel at bottom for nice width */
        JPanel gap = new JPanel(null);
        gap.setBackground(style.getBackgroundColor());
        gap.setPreferredSize(new Dimension(375, 78));

        content.add(gap, BorderLayout.PAGE_END);


        /* add bottom buttons */
//        JPanel bottom = new JPanel(new FlowLayout());
        JPanel topPanelForButtons = new JPanel(new GridBagLayout());
        topPanelForButtons.setBackground(style.getBackgroundColor());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.gridy = 0;
        constraints.insets = new Insets(12, 6, 24, 6);

        int width = 63;
        int height = 42;

        JButton button = new JButton(SingleMove.LEFT.toString());
        button.setPreferredSize(new Dimension(width, height));
        button.addActionListener(e -> nonblockingSingleGUIExecution(() -> createAndAddInstruction(SingleMove.LEFT)));
        topPanelForButtons.add(button, constraints);

        button = new JButton(SingleMove.UP.toString());
        button.setPreferredSize(new Dimension(width, height));
        button.addActionListener(e -> nonblockingSingleGUIExecution(() -> createAndAddInstruction(SingleMove.UP)));
        topPanelForButtons.add(button, constraints);

        button = new JButton(SingleMove.DOWN.toString());
        button.setPreferredSize(new Dimension(width, height));
        button.addActionListener(e -> nonblockingSingleGUIExecution(() -> createAndAddInstruction(SingleMove.DOWN)));
        topPanelForButtons.add(button, constraints);

        button = new JButton(SingleMove.RIGHT.toString());
        button.setPreferredSize(new Dimension(width, height));
        button.addActionListener(e -> nonblockingSingleGUIExecution(() -> createAndAddInstruction(SingleMove.RIGHT)));
        topPanelForButtons.add(button, constraints);

        button = new JButton("⟲");
        button.setPreferredSize(new Dimension(width, height));
        button.addActionListener(e -> nonblockingSingleGUIExecution(this::createAndAddLoop));
        topPanelForButtons.add(button, constraints);

        button = new JButton("?");
        button.setPreferredSize(new Dimension(width, height));
        button.addActionListener(e -> nonblockingSingleGUIExecution(this::createAndAddCondition));
//        bottom.add(button, constraints);

        add(topPanelForButtons, BorderLayout.PAGE_START);
    }

    
    /*
    |=========================|
    | utils for outer queries |
    |=========================|
    */
    /**
     * ATTENTION: This method does NOT blocking the gui, so be careful when using it.
     */
    public CodeLineModel[] getCode() {

        // todo not concurrent
        CodeLineModel[] deepcopy = new CodeLineModel[codeLines.size()];

        int idx = 0;
        for (CodeLine codeLine : codeLines)
            deepcopy[idx++] = codeLine.getModel().deepcopy();

        return deepcopy;
    }
    

    /*
    |===============================|
    | utils for CodeLines in detail |
    |===============================|
    */
    private void createAndAddInstruction(SingleMove move) {

        /* prepare new instruction */
        Instruction instruction = new Instruction(style, move);
        instruction.addDeleteListener(e -> nonblockingSingleGUIExecution(() -> removeCodeLineAndUpdate(instruction)));
        instruction.addMoveUpListener(e -> nonblockingSingleGUIExecution(() -> moveUpAndUpdate(instruction)));
        instruction.addMoveDownListener(e -> nonblockingSingleGUIExecution(() -> moveDownAndUpdate(instruction)));

        /* add new instruction */
        addCodeLineAndUpdate(instruction);
    }

    private void createAndAddLoop() {

        /* prepare new loop */
        Loop start = Loop.createStart(style);
        Loop end = Loop.createEnd(style);
        start.setCounterpart(end);
        end.setCounterpart(start);

        start.addDeleteListener(e -> nonblockingSingleGUIExecution(() -> removeLoop(start)));
        end.addDeleteListener(e -> nonblockingSingleGUIExecution(() -> removeLoop(end)));
        start.addMoveUpListener(e -> nonblockingSingleGUIExecution(() -> moveUpAndUpdate(start)));
        end.addMoveUpListener(e -> nonblockingSingleGUIExecution(() -> moveUpAndUpdate(end)));
        start.addMoveDownListener(e -> nonblockingSingleGUIExecution(() -> moveDownAndUpdate(start)));
        end.addMoveDownListener(e -> nonblockingSingleGUIExecution(() -> moveDownAndUpdate(end)));


        /* add new loop */
        addCodeLine(start);
        addCodeLineAndUpdate(end);
    }

    private void removeLoop(Loop loop) {

        if (loop.getModel().isStart()) {
            removeCodeLineAndUpdate(loop.getCounterpart());
            removeCodeLineAndUpdate(loop);
        } else {
            removeCodeLineAndUpdate(loop);
            removeCodeLineAndUpdate(loop.getCounterpart());
        }
    }

    private void createAndAddCondition() {
        // todo
    }


    /*
    |================================|
    | utils for CodeLines in general |
    |================================|
    */
    private void addCodeLine(CodeLine codeLine) {
        panelOfLines.add(codeLine);
        panelOfLines.add(Box.createRigidArea(new Dimension(1, 0))); // width doesn't matter due to vertical BoxLayout
        codeLines.add(codeLine);
    }

    private void addCodeLineAndUpdate(CodeLine codeLine) {
        addCodeLine(codeLine);
        updateLineNumbers();
        getRootPane().revalidate();
    }

    private void removeCodeLine(CodeLine codeLine) {

        int idx = codeLine.getModel().getLineNumber() - 1;

        codeLines.remove(idx);

        // twice due to gap panels
        idx *= 2;
        panelOfLines.remove(idx);
        panelOfLines.remove(idx);
    }

    private void removeCodeLineAndUpdate(CodeLine codeLine) {
        removeCodeLine(codeLine);
        updateLineNumbers();
        getRootPane().revalidate();
    }

    private void moveUp(CodeLine bottomCodeLine) {

        if (bottomCodeLine.getModel().getLineNumber() == 1)
            return;

        int bottomIdx = bottomCodeLine.getModel().getLineNumber() - 1;
        int topIdx = bottomIdx - 1;

        moveDown(codeLines.get(topIdx));
    }

    private void moveDown(CodeLine topCodeLine) {

        /* check if top codeline is last codeline */
        if (topCodeLine.getModel().getLineNumber() == codeLines.size())
            return;


        /* get swap indices */
        int topIdx = topCodeLine.getModel().getLineNumber() - 1;
        int bottomIdx = topIdx + 1;
        CodeLine bottomCodeLine = codeLines.get(bottomIdx);


        /**
         * several cases:
         * - the number in arrows stands for the number of swaps
         * - ins stands for instruction
         * - loo stands for any loop (start or and)
         * - ls0 stands for "loop_start_0"
         * - le0 stands for "loop_end_0"
         *
         * (1)                (ins, ins)  -1-> (ins, ins)
         * (2)                (ins, loo) <-1-> (loo, ins)
         * (3)           (ls0, le0, ins)  -2-> (ins, ls0, le0)
         * (4)           (ls0, le0, ls1)  -2-> (ls1, ls0, le0)
         * (5)           (ls0, le0, le1)  -2-> (le1, ls0, le0)
         * (6) (ls0, ls1, ins, le1, le0)  -1-> (ls1, ls0, ins, le0, le1) inclusive loop-counterpart-swap
         * (7)                (le0, le1)  -1-> (le0, le1) inclusive loop-counterpart-swap
         * (8)           (le0, ls1, le1)  -1-> (ls1, le1, le0) inclusive loop-counterpart-swap
         */


        /* case 1 and 2 */
        if ((topCodeLine instanceof Instruction) || (bottomCodeLine instanceof Instruction)) {
            logger.debug("swap neighbours case 1 and 2");
            swap(topIdx, bottomIdx);
            return;
        }


        /* now: both are loops */
        Loop topLoop = (Loop) topCodeLine;
        Loop bottomLoop = (Loop) bottomCodeLine;


        /* case 7: both are loop ends */
        if (!topLoop.getModel().isStart() && !bottomLoop.getModel().isStart()) {
            logger.debug("swap neighbours case 7: both are loop ends");
            return;
        }


        /* case 6: both are loop starts */
        if (topLoop.getModel().isStart() && bottomLoop.getModel().isStart()) {
            logger.debug("swap neighbours case 6: both are loop starts");
            swap(topIdx, bottomIdx);

            /* swap counterparts */
            bottomIdx = topLoop.getCounterpart().getModel().getLineNumber() - 1;
            topIdx = bottomLoop.getCounterpart().getModel().getLineNumber() - 1;
            swap(topIdx, bottomIdx);

            return;
        }


        /* now: one is start and one is end */
        /* case 8 */
        if (!topLoop.getModel().isStart()) {
            logger.debug("swap neighbours case 8: first is loop end");
            swap(topIdx, bottomIdx);

            /* swap counterparts */
            topIdx = bottomIdx;
            bottomIdx = bottomLoop.getCounterpart().getModel().getLineNumber() - 1;
            swap(topIdx, bottomIdx);

            return;
        }


        /* cases 3 to 5 */
        logger.debug("swap neighbours case 3 to 5");
        if (bottomIdx + 1 == codeLines.size())
            return;
        swap(bottomIdx, bottomIdx + 1);
        swap(topIdx, bottomIdx);
    }

    private void swap(int i, int j) {

        Collections.swap(codeLines, i, j);

        /* update gui */
        panelOfLines.add(codeLines.get(i), 2 * i); // twice due to gap panels
        panelOfLines.add(codeLines.get(j), 2 * j); // twice due to gap panels
    }

    private void moveUpAndUpdate(CodeLine bottomCodeLine) {

        moveUp(bottomCodeLine);

        updateLineNumbers();
        getRootPane().revalidate();

    }

    private void moveDownAndUpdate(CodeLine topCodeLine) {

        moveDown(topCodeLine);

        updateLineNumbers();
        getRootPane().revalidate();
    }

    private void updateLineNumbers() {

        int digits = 1 + inforkids.utils.MathUtils.getDigitCountDecimal(codeLines.size());

        int lineNumber = 1;
        int codeLevel = 0;
        for (CodeLine codeLine : codeLines) {

            /* go code level back if needed */
            if (codeLine instanceof Loop)
                if (!((Loop) codeLine).getModel().isStart())
                    codeLevel--;

            /* set line number */
            codeLine.setLineNumber(lineNumber, digits);
            codeLine.setCodeLevel(codeLevel);

            /* update */
            lineNumber++;
            /* go code level forward if needed */
            if (codeLine instanceof Loop)
                if (((Loop) codeLine).getModel().isStart())
                    codeLevel++;
        }
    }

    /*
    |======================|
    | utils multithreading |
    |======================|
    */
    public void setGUIEnabled(boolean isGUIEnabled) {
        guiLock.lock();
        // todo deadlock if buttons are pressed with circa speed of light
        this.isGUIEnabled.set(isGUIEnabled);
        guiLock.unlock();
    }

    public void nonblockingSingleGUIExecution(Procedure procedure) {
        if (guiLock.tryLock()) {

            if (isGUIEnabled.compareAndSet(true, false)) {
                new Thread(() -> {

                    procedure.invoke();

                    isGUIEnabled.set(true);
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
    private class ScrollablePanel extends JPanel implements Scrollable {


        public ScrollablePanel(LayoutManager layout, boolean isDoubleBuffered) {
            super(layout, isDoubleBuffered);
        }

        public ScrollablePanel(LayoutManager layout) {
            super(layout);
        }

        public ScrollablePanel(boolean isDoubleBuffered) {
            super(isDoubleBuffered);
        }

        public ScrollablePanel() {
            super();
        }

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
}