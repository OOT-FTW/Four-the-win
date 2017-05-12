package de.hsMannheim.informatik.oot.ss17.ttt.model;

public class AIPlayer implements Player {

	private String name;
	private AIDifficultyLevel level;
	
	public AIPlayer(String name, AIDifficultyLevel level) {
		this.name = name;
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
