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
		//throw new InvalidTurnException("Not yet implemented.", null, -1);
		
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new GameTurn(new GameBoardSize(10, 10), 1);
	}

}
