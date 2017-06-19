package de.hsMannheim.informatik.oot.ss17.ttt.model;

import java.util.ArrayList;

public class AIPlayerNormal implements Player {

	private static final int SEARCH_DEPTH = 0;
	private static final boolean printTime = true;
	
	private String name;
	private int[] ratings;
	private int playerNumber;
	
	public AIPlayerNormal(int number) {
		this(number, "Normaler Computergegner");
	}
	
	public AIPlayerNormal(int number, String name) {
		this.name = name;
		this.playerNumber = number;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public GameTurn getNextTurn(GameBoard board) throws InvalidTurnException {
		long startTime = System.currentTimeMillis();
		
		GameTurn[] nextTurns = createValidGameTurns(board);
		ratings = new int[nextTurns.length];
		Thread[] threads = new Thread[nextTurns.length];
		
		// Start a new thread for each valid game turn to calculate the rating with a specific search depth
		for(int i = 0; i < nextTurns.length; i++) {
			GameToken start;
			if(playerNumber == 2) {
				start = GameToken.SECOND_PLAYER;
			}
			else {
				start = GameToken.FIRST_PLAYER;
			}
			
			(threads[i] = new AIThread(i, this, board, start, SEARCH_DEPTH, nextTurns[i])).start();
		}
		
		// Wait for the calculating threads to end
		for(Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.out.println("An error occured.");
				e.printStackTrace();
			}
		}
		
		// Choose the best calculated game turn
		int best = 0;
		int bestIndex = -1;
		for(int i = 0; i < nextTurns.length; i++) {
			int rating = ratings[i];
			if(bestIndex == -1 || rating > best) {
				best = rating;
				bestIndex = i;
			}
		}
		
		// Print the time needed to calculate the best next turn to the console.
		long deltaTime = System.currentTimeMillis() - startTime;
		if(printTime) {
			System.out.println("Errechnet in " + (deltaTime / 60000) + "min " + ((deltaTime % 60000) / 1000) + "sec " + (deltaTime % 1000) + "ms.");
		}
		
		// Print best game turn to the console
		if(bestIndex == -1) {
			throw new InvalidTurnException("No possible game turn found.");
		}
		else {
			int nextTurn = -1;
			if(nextTurns[bestIndex].getDirection().equals(CompassDirection.NORTH)) {
				nextTurn = nextTurns[bestIndex].getLine();
			}
			else if(nextTurns[bestIndex].getDirection().equals(CompassDirection.EAST)) {
				nextTurn = nextTurns[bestIndex].getLine();
				nextTurn += board.getColumns();
			}
			else if(nextTurns[bestIndex].getDirection().equals(CompassDirection.SOUTH)) {
				nextTurn = board.getColumns() - nextTurns[bestIndex].getLine() + 1;
				nextTurn += board.getColumns();
				nextTurn += board.getRows();
			}
			else if(nextTurns[bestIndex].getDirection().equals(CompassDirection.WEST)) {
				nextTurn = board.getRows() - nextTurns[bestIndex].getLine() + 1;
				nextTurn += 2 * board.getColumns();
				nextTurn += board.getRows();
			}
			
			System.out.println("Zug des Computergegners: " + nextTurn);
			
			// Return best next game turn
			return nextTurns[bestIndex];
		}
	}
	
	/**
	 * Sets the calculated rating of the following game tree of a specific turn, specified by the index.
	 * @param index Index of the game rated game turn.
	 * @param value Rating of the game turn.
	 * 
	 * @see AIThread.class
	 */
	public synchronized void setTurnRating(int index, int value) {
		ratings[index] = value;
	}
	
	/**
	 * Creates an array containing all valid turns that could be executed on a specific board.
	 * @param board The board to find all valid turns for.
	 * @return All valid turns, empty array if there are no more valid turns.
	 */
	private GameTurn[] createValidGameTurns(GameBoard board) {
		ArrayList<GameTurn> turns = new ArrayList<GameTurn>();
		
		// Direction: North / South
		for(int line = 1; line <= board.getColumns(); line++) {
			if(board.canInsert(CompassDirection.NORTH, line)) {
				turns.add(new GameTurn(CompassDirection.NORTH, line));
			}
			if(board.canInsert(CompassDirection.SOUTH, line)) {
				turns.add(new GameTurn(CompassDirection.SOUTH, line));
			}
		}
		
		// Direction: EAST / WEST
		for(int line = 1; line <= board.getRows(); line++) {
			if(board.canInsert(CompassDirection.EAST, line)) {
				turns.add(new GameTurn(CompassDirection.EAST, line));
			}
			if(board.canInsert(CompassDirection.WEST, line)) {
				turns.add(new GameTurn(CompassDirection.WEST, line));
			}
		}
		
		
		GameTurn[] validTurns = new GameTurn[turns.size()];
		for(int i = 0; i < turns.size(); i++) {
			validTurns[i] = turns.get(i);
		}
		return validTurns;
	}
	
}

