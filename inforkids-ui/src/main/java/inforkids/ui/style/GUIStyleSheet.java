package inforkids.ui.style;

import inforkids.vis.style.LabyrinthStyleSheet;

import javax.swing.border.Border;
import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public interface GUIStyleSheet {

    Color getBackgroundColor();

    LabyrinthStyleSheet getLabyrinthStyle();

    ProgrammingStyleSheet getProgrammingStyle();
}
