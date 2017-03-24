package inforkids.vis.style;

import inforkids.core.player.Player;
import inforkids.utils.io.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Dominic Parga Cacheiro
 */
public class BasicPlayerStyleSheet implements PlayerStyleSheet {

    private BufferedImage[][] PLAYER;
    private int i;
    private int j;


    public BasicPlayerStyleSheet() {

        PLAYER = ImageLoader.loadSprite("/player.png", 320, 320);
        i = 0;
        j = 0;
    }


    @Override
    public void update(long delta) {

    }

    @Override
    public Image get(Player player) {
//        return getGroundImage(
//                Field.isType(field.get(Direction.LEFT), Field.Type.GROUND),
//                Field.isType(field.get(Direction.DOWN), Field.Type.GROUND),
//                Field.isType(field.get(Direction.RIGHT), Field.Type.GROUND),
//                Field.isType(field.get(Direction.UP), Field.Type.GROUND)
//        );
        return PLAYER[i][j];
    }
}
