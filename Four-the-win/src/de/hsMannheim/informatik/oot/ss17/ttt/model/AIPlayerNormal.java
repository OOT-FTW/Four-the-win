package de.hsMannheim.informatik.oot.ss17.ttt.model;

public class AIPlayerNormal implements Player {

	private String name;
	
	public AIPlayerNormal() {
		this.name = "Normaler Computergegner";
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public GameTurn getNextTurn(GameBoard board) throws InvalidTurnException {
		// TODO AI algorithm here
		throw new InvalidTurnException("Not yet implemented.", null, -1);
	}

}
