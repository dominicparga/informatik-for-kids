package inforkids.ui.panels;

import inforkids.core.graph.Labyrinth;
import inforkids.core.graph.impl.BasicLabyrinth;
import inforkids.core.player.Player;
import inforkids.vis.GraphicSettings;
import inforkids.ui.programming.model.CodeLineModel;
import inforkids.ui.programming.model.InstructionModel;
import inforkids.ui.programming.model.LoopModel;
import inforkids.ui.programming.view.Loop;
import inforkids.ui.style.FirstStyleSheet;
import inforkids.ui.style.GUIStyleSheet;
import inforkids.utils.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Dominic Parga Cacheiro
 */
public class MainPanel extends JPanel {

    /* gui components */
    private final LabyrinthPanel labyrinthPanel;
    private final ProgrammingPanel programmingPanel;

    /* map stuff */
    private final JFileChooser mapfileChooser;


    public MainPanel() {
        this(new BuildSetup());
    }

    public MainPanel(BuildSetup buildSetup) {

        super(new BorderLayout());

        mapfileChooser = new JFileChooser();
        mapfileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        mapfileChooser.setFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return ".labyrinth";
            }

            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) return true;

                String extension = null;

                String filename = file.getName();
                int    i        = filename.lastIndexOf('.');

                if (i > 0)
                    if (i < filename.length() - 1)
                        extension = filename.substring(i + 1).toLowerCase();

                if (extension == null) return false;

                switch (extension) {
                    case "labyrinth": return true;
                    default:    return false;
                }
            }
        });


        setPreferredSize(new Dimension(buildSetup.width, buildSetup.height));
        setBackground(buildSetup.style.getBackgroundColor());

        labyrinthPanel = new LabyrinthPanel(buildSetup.style.getLabyrinthStyle());
        addLabyrinthPanel(buildSetup.style);

        programmingPanel = new ProgrammingPanel(buildSetup.style.getProgrammingStyle());
        addProgrammingPanel(buildSetup.style);


        startGraphicLoop();
    }


    /*
    |============|
    | user input |
    |============|
    */
    private void runLabyrinthProgram() {

        programmingPanel.nonblockingSingleGUIExecution(() -> {

            /* check if labyrinth is set */
            Labyrinth labyrinth = labyrinthPanel.getLabyrinth();
            if (labyrinth == null)
                return;


            /* reset player position if not on start field */
            Player player = labyrinth.getPlayer();
            if (player.getField() != labyrinth.getStartField()) {
                player.setField(labyrinth.getStartField());
                return;
            }


            /* roll over code */
            CodeLineModel[] code = programmingPanel.getCode();
            Stack<Integer> loopCounts = new Stack<>();
            Stack<Integer> loopLineStartIndices = new Stack<>();

            int lineIdx = 0;

            while (lineIdx < code.length) {
                CodeLineModel codeLineModel = code[lineIdx];

                switch (codeLineModel.getType()) {
                    case INSTRUCTION:
                        InstructionModel instruction = (InstructionModel) codeLineModel;
                        player.startWalking(instruction.getMove());
                        lineIdx++;
                        break;
                    case LOOP_START:
                        LoopModel loopModel = (LoopModel) codeLineModel;
                        Loop loopView = loopModel.getEntity().getView();
                        LoopModel counterPartModel = loopView.getCounterpart().getModel();

                        if (loopModel.getLoopCount() == 0) {
                            loopCounts.push(0);
                            loopLineStartIndices.push(0);
                            lineIdx = counterPartModel.getLineNumber() - 1;
                        } else {
                            loopCounts.push(loopModel.getLoopCount());
                            loopLineStartIndices.push(++lineIdx);
                        }
                        break;
                    case LOOP_END:
                        int count = loopCounts.pop() - 1;

                        if (count <= 0) {
                            lineIdx++;
                            loopLineStartIndices.pop();
                        } else {
                            loopCounts.push(count);
                            lineIdx = loopLineStartIndices.peek();
                        }
                        break;
                }

                codeLineModel.getEntity().getView().setHighlighted(true);

                try {
                    Thread.sleep(GraphicSettings.WALKING_ANIMATION_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /* move player */
                if (player.isWalking())
                    player.walk(player.getCurrentMove());
                player.getEntity().getVisualization().update(0);
                codeLineModel.getEntity().getView().setHighlighted(false);
            }
        });
    }

    private void loadMap() {
        programmingPanel.nonblockingSingleGUIExecution(() -> new Thread(() -> {

            File file = askForMapFile();
            if (file != null) {
                try {
                    String fieldStr = StringUtils.readFile(file);
                    labyrinthPanel.setLabyrinth(new BasicLabyrinth(fieldStr));
                } catch (Exception exception) {}
            }

        }).start());
    }


    /*
    |==============|
    | graphic loop |
    |==============|
    */
    private void startGraphicLoop() {

        final long[] lastTimeStamp = { System.currentTimeMillis() };
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                long delta = System.currentTimeMillis() - lastTimeStamp[0];
                labyrinthPanel.update(delta);
                labyrinthPanel.repaint();

                lastTimeStamp[0] = System.currentTimeMillis();
            }
        };
        new Timer().schedule(timerTask, 0, 1000 / GraphicSettings.FPS);
    }


    /*
    |=======|
    | utils |
    |=======|
    */
    private File askForMapFile() {
        int action = mapfileChooser.showOpenDialog(null);
        if (action == JFileChooser.APPROVE_OPTION)
            return mapfileChooser.getSelectedFile();

        return null;
    }


    /*
    |=========================|
    | utils for panel creation|
    |=========================|
    */
    private void addLabyrinthPanel(GUIStyleSheet style) {

        /* create top panel containing buttons and gap panel */
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(style.getBackgroundColor());

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(style.getBackgroundColor());

//        JButton button = new JButton("▶");
        JButton button = new JButton("Start");
        button.addActionListener(e -> runLabyrinthProgram());
        buttons.add(button);

        button = new JButton("Neue Karte laden");
        button.addActionListener(e -> loadMap());
        buttons.add(button);


        topPanel.add(buttons, BorderLayout.LINE_START);
        JPanel gap = new JPanel();
        gap.setBackground(style.getBackgroundColor());
        topPanel.add(gap, BorderLayout.CENTER);


        /* init labyrinth panel */


        /* combine all */
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(topPanel, BorderLayout.PAGE_START);
        leftPanel.add(labyrinthPanel, BorderLayout.CENTER);

        add(leftPanel, BorderLayout.CENTER);
    }

    private void addProgrammingPanel(GUIStyleSheet style) {

        programmingPanel.setMinimumSize(new Dimension(1, 1));
        programmingPanel.setMaximumSize(new Dimension(480, 1));


        JScrollPane pane = new JScrollPane(programmingPanel);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        pane.setBorder(style.getBorder());
        pane.setBackground(style.getBackgroundColor());
        pane.getViewport().setBackground(style.getBackgroundColor());


        JPanel panel = new JPanel(new BorderLayout());
        panel.add(pane, BorderLayout.CENTER);

        add(panel, BorderLayout.LINE_END);
    }

    public static class BuildSetup {

        public int width;
        public int height;
        public GUIStyleSheet style;

        public BuildSetup() {
            width = 800;
            height = 480;
            style = new FirstStyleSheet();
        }
    }
}