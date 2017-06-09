package de.hsMannheim.informatik.oot.ss17.ttt.model;

import java.util.ArrayList;

public class AIPlayerHard implements Player {
	
	private static final int SEARCH_DEPTH = 3; // Standard: 3
	private static final boolean printTime = true;
	
	private int[] ratings;
	private String name;
	private int playerNumber;
	
	/**
	 * Creates a new AI player with the difficulty level hard.
	 * The name is the standard name "Schwerer Computergegner <number>"
	 * with <number> representing the player number.
	 * @param number The player number of the AI player.
	 */
	public AIPlayerHard(int number) {
		this.name = "Schwerer Computergegner " + number;
		this.playerNumber = number;
	}
	
	/**
	 * Creates a new AI player with the difficulty level hard.
	 * @param number The player number of the AI player.
	 * @param name The displayed name of the AI player.
	 */
	public AIPlayerHard(int number, String name) {
		this.name = name;
		this.playerNumber = number;
	}
	
	/**
	 * This method returns the name of the player.
	 * @return name of the player.
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * This method gets the next game turn from the player.
	 * The best next turn is determined by a limited depth search.
	 * The calculation is parallelized to increase the performance
	 * especially on multi-core computers.
	 * @param board The current board situation.
	 * @return Returns the next turn that should be executed.
	 */
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
			
			(threads[i] = new HardAIThread(i, this, board, start, SEARCH_DEPTH, nextTurns[i])).start();
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
	 * @see HardAIThread.class
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
