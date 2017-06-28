package de.hsMannheim.informatik.oot.ss17.ttt.model;

import java.util.Scanner;
import java.util.StringTokenizer;

public class GameManager {
	private Menu menu;
	Scanner UI;
	public GameManager() {
		displayMenuAndExcecuteCommand();
	}
	
	public void displayMenuAndExcecuteCommand() {
		UI = new Scanner(System.in);
		String[] MainMenu = { "New Game", "load" , "exit"};
		menu = new Menu(MainMenu);
		int command = menu.getCommand();
		if (command == 1) {
			startGame();
		} else if (command == 2) {
			System.out.print("Please enter the Path, where to find the saved game: \n");
			String path = UI.nextLine();
			startGameSaved(path);
		}else if (command == 3){
			System.exit(0);
		}else{
			System.out.println("Entry dose not match. please try again.");
			displayMenuAndExcecuteCommand();
		}
	}

	

	private void startGameSaved(String path) {
		GameBoard board;
		try {
			board = new GameBoard(path);
			new Game(new LocalPlayer("Player 1",1), new LocalPlayer("Player 2",2), board);
		} catch (InvalidBoardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void startGame() {
		int i = gameMode();
		System.out.println("Please enter Your PlayerName");
		String name = UI.nextLine();
		System.out.println("Please enter the dimensions of the board: \nThe Board must be at least 6 x 7 Tiles big.\nUse the format \"AxB\": ");
		String dimensions = UI.nextLine();
		
		StringTokenizer st = new StringTokenizer(dimensions, "x");
		int first = Integer.parseInt(st.nextToken());
		int second = Integer.parseInt(st.nextToken());
		GameBoardSize size = new GameBoardSize(first, second);
		System.out.println("To save the Game type \"/save\" into the console");
		switch(i){
		case 1: new Game(new LocalPlayer(name,1),new AIPlayerEasy(), new GameBoard(size)).play();
			break;
		case 2:  new Game(new LocalPlayer(name,1),new AIPlayerNormal(2), new GameBoard(size)).play();
			break;
		case 3: new Game(new LocalPlayer(name,1),new AIPlayerHard(2), new GameBoard(size)).play();
			break;
		case 4: System.out.println("Please enter Player2:");
				String Player2 = UI.nextLine();
				new Game(new LocalPlayer(name,1),new LocalPlayer(Player2,2), new GameBoard(size)).play();
		}
	}
	public int gameMode(){
		String[] Mode = {"Easy", "Medium", "Hard", "PvP"};
		menu = new Menu(Mode);
		int i = menu.getCommand();
		switch(i){
		case 1 : return 1;
		case 2 : return 2;
		case 3 : return 3;
		case 4 : return 4;
		default : System.out.println("Entry dosn't match. Please try again");
			return gameMode();
		}
	}
}
