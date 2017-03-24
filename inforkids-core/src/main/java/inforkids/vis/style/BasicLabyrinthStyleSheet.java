package inforkids.vis.style;

import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class BasicLabyrinthStyleSheet implements LabyrinthStyleSheet {

    private final FieldStyleSheet fieldStyle;
    private final PlayerStyleSheet playerStyle;

    public BasicLabyrinthStyleSheet() {

        fieldStyle = new BasicFieldStyleSheet();
        playerStyle = new BasicPlayerStyleSheet();
    }

    @Override
    public PlayerStyleSheet getPlayerStyle() {
        return playerStyle;
    }

    @Override
    public FieldStyleSheet getFieldStyle() {
        return fieldStyle;
    }

    @Override
    public Color getBackgroundColor() {
        return new Color(0xEEEEEE);
    }


    /*
    |=======|
    | utils |
    |=======|
    */
    /**
     * @param left   true <=> left neighbour is ground
     * @param bottom true <=> bottom neighbour is ground
     * @param right  true <=> right neighbour is ground
     * @param top    true <=> top neighbour is ground
     * @return the specified field image
     */
//    private Image getGroundImage(boolean left, boolean bottom, boolean right, boolean top) {
//
//        String tmp = "";
//        tmp += left ? "L" : "x";
//        tmp += bottom ? "B" : "x";
//        tmp += right ? "R" : "x";
//        tmp += top ? "T" : "x";
//
//        switch (tmp) {
//            case "LBRT":
//                return FIELD_GROUND[0][0];
//            case "LBRx":
//                return FIELD_GROUND[0][1];
//            case "LBxT":
//                return FIELD_GROUND[0][2];
//            case "LBxx":
//                return FIELD_GROUND[0][3];
//            case "LxRT":
//                return FIELD_GROUND[1][0];
//            case "LxRx":
//                return FIELD_GROUND[1][1];
//            case "LxxT":
//                return FIELD_GROUND[1][2];
//            case "Lxxx":
//                return FIELD_GROUND[1][3];
//            case "xBRT":
//                return FIELD_GROUND[2][0];
//            case "xBRx":
//                return FIELD_GROUND[2][1];
//            case "xBxT":
//                return FIELD_GROUND[2][2];
//            case "xBxx":
//                return FIELD_GROUND[2][3];
//            case "xxRT":
//                return FIELD_GROUND[3][0];
//            case "xxRx":
//                return FIELD_GROUND[3][1];
//            case "xxxT":
//                return FIELD_GROUND[3][2];
//            default: // xxxx
//                return FIELD_GROUND[3][3];
//        }
//    }
}
