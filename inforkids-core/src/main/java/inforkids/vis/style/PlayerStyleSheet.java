package inforkids.vis.style;

import inforkids.core.player.Player;

import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public interface PlayerStyleSheet {

    void update(long delta);

    Image get(Player player);
}
