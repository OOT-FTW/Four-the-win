package de.hsMannheim.informatik.oot.ss17.ttt.model;

import java.util.*;

public class AIPlayerEasy implements Player {
	
    private String name;
    
    public AIPlayerEasy() {
        this.name = "Leichter Computergegner";
    }
    
    public AIPlayerEasy(String Name) {
        this.name = Name;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public GameTurn getNextTurn(GameBoard board) throws InvalidTurnException {
        GameTurn[] validTurns = getValidTurn(board);
        Random turn = new Random();
        return validTurns[turn.nextInt(validTurns.length)];
    }
    
    private GameTurn[] getValidTurn(GameBoard board) {
        int maxNumberOfMoves = board.getColumns() * board.getRows();
        GameTurn[] temp = new GameTurn[maxNumberOfMoves];
        for (int i = 1; i <= maxNumberOfMoves; i++) {
            try {
                temp[i - 1] = createGameTurn(board, i);
            } catch (InvalidTurnException e) {
                temp[i - 1] = null;
            }
        }
        int numberOfValidPoints = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                numberOfValidPoints++;
            }
        }
        GameTurn[] validMoves = new GameTurn[numberOfValidPoints];
        int index = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                validMoves[index] = temp[i];
                index++;
            }
        }
        return validMoves;
    }
    
    private GameTurn createGameTurn(GameBoard board, int input) throws InvalidTurnException {
        GameTurn turn;
        if (input > 0 && input <= board.getColumns()) {
            turn = new GameTurn(CompassDirection.NORTH, input);
        } else if (input > board.getColumns() && input <= board.getColumns() + board.getRows()) {
            turn = new GameTurn(CompassDirection.EAST, input - board.getColumns());
        } else if (input > board.getColumns() + board.getRows() && input <= 2 * board.getColumns() + board.getRows()) {
            turn = new GameTurn(CompassDirection.SOUTH,
                    board.getColumns() - (input - board.getColumns() - board.getRows()) + 1);
        } else if (input > 2 * board.getColumns() + board.getRows()
                && input <= 2 * board.getColumns() + 2 * board.getRows()) {
            turn = new GameTurn(CompassDirection.WEST,
                    board.getRows() - (input - 2 * board.getColumns() - board.getRows()) + 1);
        } else {
            throw new InvalidTurnException("Die Eingabe ist ungültig. Sie muss zwischen 1 und "
                    + (2 * board.getColumns() + 2 * board.getRows()) + " (inklusive) liegen.");
        }
        if (turn == null || !board.canInsert(turn.getDirection(), turn.getLine())) {
            throw new InvalidTurnException(
                    "Die Eingabe ist ungültig, da der Stein an dieser Stelle nicht eingefügt werden kann.");
        } else {
            return turn;
        }
    }
    
}