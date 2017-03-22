package inforkids.ui.panels;

import inforkids.core.move.SingleMove;
import inforkids.ui.programming.CodeLine;
import inforkids.ui.programming.Instruction;
import inforkids.ui.programming.Loop;
import inforkids.ui.style.ProgrammingStyleSheet;
import inforkids.utils.functional.Procedure;
import microtrafficsim.math.MathUtils;
import microtrafficsim.utils.logging.EasyMarkableLogger;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Dominic Parga Cacheiro
 */
public class ProgrammingPanel extends JPanel implements Scrollable {

    public static final Logger logger = new EasyMarkableLogger(ProgrammingPanel.class);


    /* general */
    private ProgrammingStyleSheet style;
    private final Dimension dimension;
    /* needed data structures */
    private final ArrayList<CodeLine> codeLines;
    private final JPanel panelOfLines;
    /* user input */
    private ReentrantLock guiLock;
    private AtomicBoolean isAddingCodeLine;

    public ProgrammingPanel(ProgrammingStyleSheet style) {
        super();

        /* general */
        this.style = style;
        dimension = new Dimension();
        /* needed data structures */
        codeLines = new ArrayList<>();
        /* user input */
        guiLock = new ReentrantLock();
        isAddingCodeLine = new AtomicBoolean(false);


        /* own layout */
        setLayout(new BorderLayout());
        setBackground(style.getBackgroundColor());


        /* programming lines */
        panelOfLines = new JPanel(null);
        panelOfLines.setLayout(new BoxLayout(panelOfLines, BoxLayout.Y_AXIS));
        panelOfLines.setBackground(style.getBackgroundColor());
        add(panelOfLines, BorderLayout.CENTER);


        /* add bottom buttons */
//        JPanel bottom = new JPanel(new FlowLayout());
        JPanel bottom = new JPanel(new GridLayout(1, 6));


        JButton button = new JButton(SingleMove.LEFT.toString());
        button.addActionListener(e -> changeGUI(() -> createAndAddInstruction(SingleMove.LEFT)));
        bottom.add(button);

        button = new JButton(SingleMove.UP.toString());
        button.addActionListener(e -> changeGUI(() -> createAndAddInstruction(SingleMove.UP)));
        bottom.add(button);

        button = new JButton(SingleMove.DOWN.toString());
        button.addActionListener(e -> changeGUI(() -> createAndAddInstruction(SingleMove.DOWN)));
        bottom.add(button);

        button = new JButton(SingleMove.RIGHT.toString());
        button.addActionListener(e -> changeGUI(() -> createAndAddInstruction(SingleMove.RIGHT)));
        bottom.add(button);

        button = new JButton("⟲");
        button.addActionListener(e -> changeGUI(this::createAndAddLoop));
        bottom.add(button);

        button = new JButton("?");
        button.addActionListener(e -> changeGUI(this::createAndAddCondition));
        bottom.add(button);

        add(bottom, BorderLayout.PAGE_END);
    }


    /*
    |===============================|
    | utils for CodeLines in detail |
    |===============================|
    */
    private void createAndAddInstruction(SingleMove move) {

        /* prepare new instruction */
        Instruction instruction = new Instruction(style, move);
        instruction.addDeleteListener(e -> changeGUI(() -> removeCodeLineAndUpdate(instruction)));
        instruction.addMoveDownListener(e -> changeGUI(() -> swapNeighborsAndUpdate(instruction)));

        /* add new instruction */
        addCodeLineAndUpdate(instruction);
    }

    private void createAndAddLoop() {

        /* prepare new loop */
        Loop start = Loop.createStart(style);
        Loop end = Loop.createEnd(style);
        start.setCounterpart(end);
        end.setCounterpart(start);

        start.addDeleteListener(e -> changeGUI(() -> removeLoop(start)));
        end.addDeleteListener(e -> changeGUI(() -> removeLoop(end)));
        start.addMoveDownListener(e -> changeGUI(() -> swapNeighborsAndUpdate(start)));
        end.addMoveDownListener(e -> changeGUI(() -> swapNeighborsAndUpdate(end)));


        /* add new loop */
        addCodeLine(start);
        addCodeLineAndUpdate(end);
    }

    private void removeLoop(Loop loop) {

        if (loop.isStart()) {
            removeCodeLineAndUpdate(loop.getCounterpart());
            removeCodeLineAndUpdate(loop);
        } else {
            removeCodeLineAndUpdate(loop);
            removeCodeLineAndUpdate(loop.getCounterpart());
        }
    }

    private void createAndAddCondition() {

    }


    /*
    |================================|
    | utils for CodeLines in general |
    |================================|
    */
    private void addCodeLine(CodeLine codeLine) {
        panelOfLines.add(codeLine);
        panelOfLines.add(Box.createRigidArea(new Dimension(1, 3))); // width doesn't matter due to vertical BoxLayout
        codeLines.add(codeLine);
    }

    private void addCodeLineAndUpdate(CodeLine codeLine) {
        addCodeLine(codeLine);
        updateLineNumbers();
        getRootPane().revalidate();
    }

    private void removeCodeLine(CodeLine codeLine) {

        int idx = codeLine.getLineNumber() - 1;

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

    private void swapNeighbors(CodeLine topCodeLine) {

        /* check if top codeline is last codeline */
        if (topCodeLine.getLineNumber() == codeLines.size())
            return;


        /* get swap indices */
        int topIdx = topCodeLine.getLineNumber() - 1;
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
        if (!topLoop.isStart() && !bottomLoop.isStart()) {
            logger.debug("swap neighbours case 7: both are loop ends");
            return;
        }


        /* case 6: both are loop starts */
        if (topLoop.isStart() && bottomLoop.isStart()) {
            logger.debug("swap neighbours case 6: both are loop starts");
            swap(topIdx, bottomIdx);

            /* swap counterparts */
            bottomIdx = topLoop.getCounterpart().getLineNumber() - 1;
            topIdx = bottomLoop.getCounterpart().getLineNumber() - 1;
            swap(topIdx, bottomIdx);

            return;
        }


        /* now: one is start and one is end */
        /* case 8 */
        if (!topLoop.isStart()) {
            logger.debug("swap neighbours case 8: first is loop end");
            swap(topIdx, bottomIdx);

            /* swap counterparts */
            topIdx = bottomIdx;
            bottomIdx = bottomLoop.getCounterpart().getLineNumber() - 1;
            swap(topIdx, bottomIdx);

            return;
        }


        /* cases 3 to 5 */
        logger.debug("swap neighbours case 3 to 5");
        if (bottomIdx + 1 == codeLines.size())
            return;
        swap(bottomIdx, bottomIdx + 1);
        swap(topIdx, bottomIdx);



//
//
//        /* if top codeline is a loop => "preprocessing" */
//        CodeLine bottomCodeLine = codeLines.get(bottomIdx);
//        if (topCodeLine instanceof Loop) {
//
//            Loop topLoop = (Loop) topCodeLine;
//            /* check whether bottom codeline is end of topLoop */
//            if (topLoop.getCounterpart() == bottomCodeLine) {
//                int i = bottomIdx;
//                int j = i + 1;
//                if (j >= codeLines.size())
//                    return;
//
//                swap(i, j);
//            }
//
//
//            /* if bottom codeline is a loop as well => swap counterparts */
//            if (bottomCodeLine instanceof Loop) {
//                Loop bottomLoop = (Loop) bottomCodeLine;
//                int i = topLoop.getCounterpart().getLineNumber() - 1;
//                int j = bottomLoop.getCounterpart().getLineNumber() - 1;
//                swap(i, j);
//            }
//        }
    }

    private void swap(int i, int j) {

        Collections.swap(codeLines, i, j);

        /* update gui */
        panelOfLines.add(codeLines.get(i), 2 * i); // twice due to gap panels
        panelOfLines.add(codeLines.get(j), 2 * j); // twice due to gap panels
    }

    private void swapNeighborsAndUpdate(CodeLine topCodeLine) {

        swapNeighbors(topCodeLine);

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
                if (!((Loop) codeLine).isStart())
                    codeLevel--;

            /* set line number */
            codeLine.setLineNumber(lineNumber, digits);
            codeLine.setCodeLevel(codeLevel);

            /* update */
            lineNumber++;
            /* go code level forward if needed */
            if (codeLine instanceof Loop)
                if (((Loop) codeLine).isStart())
                    codeLevel++;
        }
    }

    /*
    |======================|
    | utils multithreading |
    |======================|
    */
    private void changeGUI(Procedure procedure) {
        if (guiLock.tryLock()) {

            if (isAddingCodeLine.compareAndSet(false, true)) {
                new Thread(() -> {

                    procedure.invoke();

                    isAddingCodeLine.set(false);
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
