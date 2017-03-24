package inforkids.ui.style;

import inforkids.vis.style.BasicLabyrinthStyleSheet;
import inforkids.vis.style.LabyrinthStyleSheet;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class FirstStyleSheet implements GUIStyleSheet {

    public final LabyrinthStyleSheet LABYRINTH;
    public final ProgrammingStyleSheet PROGRAMMING;

    public FirstStyleSheet() {

        final FirstStyleSheet tmp = this;

        LABYRINTH = new BasicLabyrinthStyleSheet();
        PROGRAMMING = new ProgrammingStyleSheet() {
            @Override
            public Color getBackgroundColor() {
                return tmp.getBackgroundColor();
            }

            @Override
            public Color getHighlightedBackgroundColor() {
                return new Color(0, 160, 0);
            }

            @Override
            public Color getCodeLevelBarColor() {
                return Color.BLACK;
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
    public Border getBorder() {
        return new LineBorder(Color.BLACK, 6);
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
