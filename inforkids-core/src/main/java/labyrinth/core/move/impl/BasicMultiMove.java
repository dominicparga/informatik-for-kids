package labyrinth.core.move.impl;

import labyrinth.core.move.MultiMove;
import labyrinth.core.move.SingleMove;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dominic Parga Cacheiro
 */
public class BasicMultiMove implements MultiMove {

    private final List<SingleMove> moves;

    public BasicMultiMove() {
        moves = new LinkedList<>();
    }


    /*
    |===============|
    | (i) MultiMove |
    |===============|
    */
    @Override
    public void add(SingleMove move) {
        moves.add(move);
    }

    @Override
    public void add(MultiMove move) {
        for (SingleMove singleMove : move)
            moves.add(singleMove);
    }


    /*
    |==============|
    | (i) Iterable |
    |==============|
    */
    @Override
    public Iterator<SingleMove> iterator() {
        return moves.iterator();
    }
}
