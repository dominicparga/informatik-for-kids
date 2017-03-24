package inforkids.vis.style;

import inforkids.core.player.Player;
import inforkids.utils.io.ImageLoader;
import microtrafficsim.math.random.distributions.impl.Random;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Dominic Parga Cacheiro
 */
public class BasicPlayerStyleSheet implements PlayerStyleSheet {

    private BufferedImage[][] PLAYER;
    private Orientation orientation;
    private Action action;

    private final Random random;

    private long timePassedBlinking;
    private long blinkTimeStamp;
    private final long BLINK_DURATION;


    public BasicPlayerStyleSheet() {

        PLAYER = ImageLoader.loadSprite("/player.png", 320, 320);
        orientation = Orientation.FRONT;
        action = Action.STANDING;

        random = new Random();

        /* blinking time stamping */
        timePassedBlinking = 0;
        blinkTimeStamp = 3000;
        BLINK_DURATION = 300;

        /* orientation time stamping */

    }


    @Override
    public void update(long delta) {

        updateBlinking(delta);
        updateOrientation(delta);
    }

    private void updateOrientation(long delta) {


    }

    private void updateBlinking(long delta) {

        timePassedBlinking += delta;

        if (timePassedBlinking >= blinkTimeStamp + BLINK_DURATION) {
            timePassedBlinking = 0;
            blinkTimeStamp = random.nextInt(2000) + 4000;
        }

        if (timePassedBlinking < blinkTimeStamp)
            action = Action.STANDING;
        else
            action = Action.BLINKING;
    }

    @Override
    public Image get(Player player) {
        return PLAYER[orientation.IDX][action.IDX];
    }


    public enum Orientation {
        FRONT(0), LEFT(1), BACK(2), RIGHT(3);

        private final int IDX;

        Orientation(int idx) {
            this.IDX = idx;
        }
    }

    public enum Action {
        STANDING(0), BLINKING(1), RIGHT_FOOT_MOVES(2), LEFT_FOOT_MOVES(3);

        private final int IDX;

        Action(int idx) {
            this.IDX = idx;
        }
    }
}
