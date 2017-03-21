package inforkids.ui.style;

import inforkids.vis.style.BasicLabyrinthStyleSheet;
import inforkids.vis.style.LabyrinthStyleSheet;

import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class FirstStyleSheet implements GUIStyleSheet {

    public final LabyrinthStyleSheet LABYRINTH;
    public final ProgrammingStyleSheet PROGRAMMING;

    public FirstStyleSheet() {
        LABYRINTH = new BasicLabyrinthStyleSheet();
        PROGRAMMING = new ProgrammingStyleSheet() {
            @Override
            public Color getBackgroundColor() {
                return Color.PINK;
            }

            @Override
            public Font getFont() {
                return new Font("Courier", Font.BOLD, 20);
            }
        };
    }


    @Override
    public Color getBackgroundColor() {
        return LABYRINTH.getBackgroundColor();
    }

    @Override
    public LabyrinthStyleSheet getLabyrinthStyle() {
        return LABYRINTH;
    }

    @Override
    public ProgrammingStyleSheet getProgrammingStyle() {
        return PROGRAMMING;
    }
}
