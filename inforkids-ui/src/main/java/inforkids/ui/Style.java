package inforkids.ui;

import inforkids.vis.style.BasicGUIStyleSheet;
import inforkids.vis.style.GUIStyleSheet;

import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public abstract class Style {

    public final static LabyrinthStyle LABYRINTH = new LabyrinthStyle();
    public final static ProgrammingStyle PROGRAMMING = new ProgrammingStyle();

    public static class LabyrinthStyle {

        public final Color BACKGROUND_COLOR;

        public LabyrinthStyle() {
            GUIStyleSheet styleSheet = new BasicGUIStyleSheet();
            BACKGROUND_COLOR = styleSheet.getBackgroundColor();
        }
    }

    public static class ProgrammingStyle {

        public final Color BACKGROUND_COLOR;

        public ProgrammingStyle() {
            BACKGROUND_COLOR = Color.PINK;
        }
    }
}
