package de.hsMannheim.informatik.oot.ss17.ttt.model;

public class AIPlayer implements Player {

	private String name;
	private AIDifficultyLevel level;
	
	public AIPlayer(AIDifficultyLevel level) {
		this.name = "Computer";
		switch(level) {
		case EASY:		this.name = "Leichter Computergegner"; break;
		case NORMAL:	this.name = "Normaler Computergegner"; break;
		case HARD:		this.name = "Schwerer Computergegner"; break;
		}
		
		this.level = level;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public GameTurn getNextTurn(GameBoard board) {
		// TODO Add AI algorithms here.
		
		if(level.equals(AIDifficultyLevel.EASY)) {
			// Easy AI here
		}
		else if(level.equals(AIDifficultyLevel.NORMAL)) {
			// Normal AI here
		}
		else if(level.equals(AIDifficultyLevel.HARD)) {
			// Hard AI here
		}
		
		return null;
	}

}
