package de.hsMannheim.informatik.oot.ss17.ttt.model;

/**
 * This class represents a player that has a specific name and the ability
 * to calculate a game turn and return it.
 */
public interface Player {

	/**
	 * This method returns the name of the player.
	 * @return name of the player.
	 */
	public String getName();
	
	/**
	 * This method gets the next game turn from the player.
	 * @param board The current board situation.
	 * @return Returns the next turn that should be executed.
	 */
	public GameTurn getNextTurn(GameBoard board);
}
