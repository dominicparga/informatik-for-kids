package inforkids.vis;

import inforkids.core.move.SingleMove;
import inforkids.core.player.Player;
import inforkids.entities.PlayerEntity;
import inforkids.vis.style.PlayerStyleSheet;
import microtrafficsim.math.MathUtils;
import microtrafficsim.math.random.distributions.impl.Random;

/**
 * @author Dominic Parga Cacheiro
 */
public class VisPlayer {

    /* attributes */
    private Orientation orientation;
    private Action action;

    /* general */
    private PlayerEntity entity;
    private PlayerStyleSheet style;

    /* animation utils - general */
    private final Random random;
    /* animation utils - blinking */
    private long timePassedBlinking;
    private long blinkTimeStamp;
    private final long BLINK_DURATION;
    /* animation utils - orientation */
    private long timePassedWithoutWalking;
    private long turnToFrontTimeStamp;
    /* animation utils - position */
    private long timePassedWalking;
    private long timePassedSameAction;
    private long actionTimeStamp;


    public VisPlayer(PlayerStyleSheet style) {

        /* attributes */
        orientation = Orientation.FRONT;
        action = Action.STANDING;


        /* general */
        this.style = style;


        /* animation utils - general */
        random = new Random();
        /* animation utils - blinking */
        timePassedBlinking = 0;
        blinkTimeStamp = 3000;
        BLINK_DURATION = 300;
        /* animation utils - orientation */
        timePassedWithoutWalking = 0;
        turnToFrontTimeStamp = 2000;
        /* animation utils - position */
        timePassedWalking = 0;
        timePassedSameAction = 0;
        actionTimeStamp = 230;

    }


    public double getWalkingXFraction() {

        Player player = entity.getLogic();

        double fraction = ((double)timePassedWalking) / GraphicSettings.WALKING_ANIMATION_DURATION;
        MathUtils.clamp(fraction, 0, 1);

        if (player.getCurrentMove() == SingleMove.LEFT)
            return -1 * fraction;
        if (player.getCurrentMove() == SingleMove.RIGHT)
            return fraction;
        else
            return 0;
    }

    public double getWalkingYFraction() {

        Player player = entity.getLogic();

        double fraction = ((double)timePassedWalking) / GraphicSettings.WALKING_ANIMATION_DURATION;
        MathUtils.clamp(fraction, 0, 1);

        if (player.getCurrentMove() == SingleMove.UP)
            return -1 * fraction;
        if (player.getCurrentMove() == SingleMove.DOWN)
            return fraction;
        else
            return 0;
    }


    public Orientation getOrientation() {
        return orientation;
    }

    public Action getAction() {
        return action;
    }

    public PlayerEntity getEntity() {
        return entity;
    }

    public void setEntity(PlayerEntity entity) {
        this.entity = entity;
    }


    public void update(long delta) {

        updateBlinking(delta);
        updateOrientation(delta);
        updateAction(delta);
    }

    private void updateBlinking(long delta) {

        timePassedBlinking += delta;

        if (timePassedBlinking >= blinkTimeStamp + BLINK_DURATION) {
            timePassedBlinking = 0;
            blinkTimeStamp = random.nextInt(2000) + 4000;
        }

        Player player = entity.getLogic();
        if (!player.isWalking()) {
            if (timePassedBlinking < blinkTimeStamp)
                action = Action.STANDING;
            else
                action = Action.BLINKING;
        }
    }

    private void updateOrientation(long delta) {

        Player player = entity.getLogic();
        if (player.isWalking()) {
            /* reset timing stuff */
            timePassedWithoutWalking = 0;

            /* update orientation */
            SingleMove currentMove = player.getCurrentMove();

            if (currentMove == SingleMove.LEFT)
                orientation = Orientation.LEFT;
            else if (currentMove == SingleMove.DOWN)
                orientation = Orientation.FRONT;
            else if (currentMove == SingleMove.RIGHT)
                orientation = Orientation.RIGHT;
            else if (currentMove == SingleMove.UP)
                orientation = Orientation.BACK;
        } else {
            timePassedWithoutWalking += delta;

            if (timePassedWithoutWalking >= turnToFrontTimeStamp) {
                timePassedWithoutWalking = 0;
                orientation = Orientation.FRONT;
            }
        }
    }

    private void updateAction(long delta) {

        Player player = entity.getLogic();

        if (!player.isWalking()
                || timePassedWalking >= GraphicSettings.WALKING_ANIMATION_DURATION) {
            timePassedWalking = 0;
            timePassedSameAction = actionTimeStamp;
        }

        if (player.isWalking()) {
            timePassedWalking += delta;

            timePassedSameAction += delta;
            if (timePassedSameAction >= actionTimeStamp) {
                timePassedSameAction = 0;
                if (action == Action.RIGHT_FOOT_MOVES)
                    action = Action.STANDING;
                else if (action == Action.STANDING)
                    action = Action.LEFT_FOOT_MOVES;
                else
                    action = Action.RIGHT_FOOT_MOVES;
            }
        }
    }


    public enum Orientation {
        FRONT(0), LEFT(1), BACK(2), RIGHT(3);

        public final int IDX;

        Orientation(int idx) {
            this.IDX = idx;
        }
    }

    public enum Action {
        STANDING(0), BLINKING(1), RIGHT_FOOT_MOVES(2), LEFT_FOOT_MOVES(3);

        public final int IDX;

        Action(int idx) {
            this.IDX = idx;
        }
    }
}