package labyrinth.vis.style;

import labyrinth.core.graph.Field;
import labyrinth.core.graph.Labyrinth;
import labyrinth.core.player.Player;

/**
 * @author Dominic Parga Cacheiro
 */
public class ConsoleStyleSheet implements StringStyleSheet {

    @Override
    public String get(Labyrinth labyrinth) {
        StringBuilder builder = new StringBuilder();

        int k = 0;
        for (Field field : labyrinth) {
            System.out.println(field);
            builder.append(get(field));

            if (++k % labyrinth.getColumns() == 0)
                builder.append("\n");
        }

        return builder.toString();
    }

    @Override
    public String get(Labyrinth labyrinth, Player player) {
        StringBuilder builder = new StringBuilder();

        int k = 0;
        for (Field field : labyrinth) {
            if (field == player.getField())
                builder.append(get(player));
            else
                builder.append(get(field));

            if (++k % labyrinth.getColumns() == 0)
                builder.append("\n");
        }

        return builder.toString();
    }

    @Override
    public String get(Field field) {
        switch (field.getType()) {
            case WALL:
                return "|W|";
            case GROUND:
                if (field.isGoalField())
                    return "  ";
                else
                    return "   ";
        }

        return "###";
    }

    @Override
    public String get(Player player) {
        return "O.o";
    }
}