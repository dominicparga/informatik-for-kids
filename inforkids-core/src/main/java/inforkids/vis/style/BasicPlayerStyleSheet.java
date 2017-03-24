package inforkids.vis.style;

import inforkids.core.player.Player;
import inforkids.utils.io.ImageLoader;
import inforkids.vis.VisPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Dominic Parga Cacheiro
 */
public class BasicPlayerStyleSheet implements PlayerStyleSheet {

    private BufferedImage[][] PLAYER;


    public BasicPlayerStyleSheet() {
        PLAYER = ImageLoader.loadSprite("/player.png", 320, 320);
    }


    @Override
    public Image get(Player player) {
        VisPlayer visPlayer = player.getEntity().getVisualization();
        return PLAYER[visPlayer.getOrientation().IDX][visPlayer.getAction().IDX];
    }
}
