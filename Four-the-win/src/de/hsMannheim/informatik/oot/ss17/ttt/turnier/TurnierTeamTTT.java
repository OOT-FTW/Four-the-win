package de.hsMannheim.informatik.oot.ss17.ttt.turnier;

import java.util.Scanner;

/**
 * Die Turnierklasse implementiert die beiden gegeneinander antretenden KIs und zählt die Gewinne.
 * Die KI die in 100 Runden die meisten Siege erreicht gewinnt, dabei darf ein Zug nicht länger als 10 Sekunden dauern.
 * Am Montag den 05.06.2017 findet das Turnier statt, dafür wird hier die KI vom Team Tornado Tag implementiert.
 * Diese Klasse findet mit dem Ende des Turniers ebenfalls ihr Ende und kann dann gelöscht werden.
 * @author Joshua Joost @10.06.2017
 *
 */
public class TurnierTeamTTT {
	private static final String TEAM_1 = "MindMaze";
	private static final String TEAM_2 = "Tornado Tag";

	private static final int MAX_TIME_FOR_TURN = 10000; //ms

	private static final int MAX_ROUNDS = 100;
	private static int actualRound = 1;
	private static int actualTurnNumber = 1;

	private static int winsTeam1 = 0;
	private static int winsTeam2 = 0;
	private static int draws = 0;
	private static int timeKOTeam1 = 0;
	private static int timeKOTeam2 = 0;
	private static int numberOfWrongTurnsTeam1 = 0;
	private static int numberOfWrongTurnsTeam2 = 0;

	private static String recentBeginner = TEAM_1; //Das gegnerische Team beginnt das Turnier
	private static String actualTurn = TEAM_1;

	private static Scanner inputScanner = new Scanner(System.in);

	private static final String[] errorMenu = new String[]{
			TEAM_1 + " gewinnt",
			TEAM_1 + " verliert",
			"Unentschieden"
	};

	//Implementiere KIs
	private static Engine team1Ki = new TTTEngine(); //TODO change to enemy engine
	private static Engine team2Ki = new TTTEngine();
	//Diese KI gibt das Board aus, dass es einheitlich bleibt
	private static Engine printOnlyThisBoardWhilePlaying = team1Ki;

	public static void printStatistic(){
		System.out.println("Aktueller Stand Runde " + (actualRound - 1) + "/" + MAX_ROUNDS);
		System.out.println(TEAM_1 + ": \tGewonnen: " + winsTeam1 + "\tKOs durch TimeOut: " + timeKOTeam1 + "\tAnzahl ungültiger Züge: " + numberOfWrongTurnsTeam1);
		System.out.println(TEAM_2 + ": \tGewonnen: " + winsTeam2 + "\tKOs durch TimeOut: " + timeKOTeam2 + "\tAnzahl ungültiger Züge: " + numberOfWrongTurnsTeam2);
		System.out.println("Unentschieden: " + draws);
	}

	private static void switchBeginner(){
		if(recentBeginner.equals(TEAM_1)){
			recentBeginner = TEAM_2;
			actualTurn = TEAM_2;
		}
		else{
			recentBeginner = TEAM_1;
			actualTurn = TEAM_1;
		}
	}

	public static void kOCauseTimeOut(long timeNeeded, String teamWhoTimedOut){
		System.out.println(teamWhoTimedOut + " verliert durch TimeOut! (" + timeNeeded + " ms)");
		if(teamWhoTimedOut.equals(TEAM_1)){
			timeKOTeam1++;
			winsTeam2++;
		}
		else{
			timeKOTeam2++;
			winsTeam1++;
		}
	}

	public static void wrongTurn(int move, String teamname, Engine engineWhoMadeTheWrongTurn, Engine othersEngine){
		boolean stillWrongTurn = true;
		while(stillWrongTurn){
			System.out.println(teamname + " hat mit " + move + " einen falschen Zug ausführen wollen!");
			if(teamname.equals(TEAM_1)){
				numberOfWrongTurnsTeam1++;
			}
			else{
				numberOfWrongTurnsTeam2++;
			}
			//Vergleichen beider Boards
			team2Ki.printBoard();
			team1Ki.printBoard();

			long time = System.currentTimeMillis();
			int newMove = engineWhoMadeTheWrongTurn.getMove();
			time = System.currentTimeMillis() - time;
			if(time > MAX_TIME_FOR_TURN){
				kOCauseTimeOut(time, teamname);
			}
			if(othersEngine.sendMove(newMove)){
				stillWrongTurn = false;
			}
		}
	}

	public static void printResult(){
		System.out.print("Das Spiel ist vorbei ");
		int resultTeam1 = team1Ki.getMatchResult();
		int resultTeam2 = team2Ki.getMatchResult();
		String whatTeam1Say = "Unentschieden";
		String whatTeam2Say = "Unentschieden";

		switch(resultTeam1){
		case -1: whatTeam1Say = TEAM_2; break;
		case 1: whatTeam1Say = TEAM_1; break;
		default: break;
		}
		switch(resultTeam2){
		case -1: whatTeam2Say = TEAM_1; break; 
		case 1: whatTeam2Say = TEAM_2; break;
		default: break;
		}

		if(whatTeam1Say.equals(whatTeam2Say)){
			if(whatTeam1Say.equals(TEAM_1)){
				winsTeam1++; System.out.println(TEAM_1 + " hat das Spiel gewonnen");
			}
			else if(whatTeam1Say.equals(TEAM_2)){
				winsTeam2++; System.out.println(TEAM_2 + " hat das Spiel gewonnen");
			}
			else{
				draws++; System.out.println("Unentschieden");
			}
		}
		else{
			System.out.println("Die KIs sind sich über das Ergebnis uneins!");
			System.out.println(TEAM_1 + " behauptet: " + whatTeam1Say);
			System.out.println(TEAM_2 + " behauptet: " + whatTeam2Say);
			System.out.println("Wie soll weiter verfahren werden?");
			System.out.println("Aktuelle Boards:");
			System.out.println("Board von " + TEAM_1);
			team1Ki.printBoard();
			System.out.println("Board von " + TEAM_2);
			team2Ki.printBoard();
			for(int i = 0; i < errorMenu.length; i++){
				System.out.println(i + ": " + errorMenu[i]);
			}

			int input = -1;
			while(true){
				try{
					input = Integer.parseInt(inputScanner.nextLine());
					if(input < 0){
						System.out.println("Eingabe ist zu klein!");
					}
					else if(input > (errorMenu.length - 1)){
						System.out.println("Eingabe ist zu groß!");
					}
					else{
						switch(input){
						case 0: winsTeam1++; break;
						case 1: winsTeam2++; break;
						default: draws++;
						}
						break;
					}
				}
				catch(Exception e){
					System.out.println("Ungültige Eingabe versuchen wir nochmal eine natürliche Zahl zwischen 0 und " + (errorMenu.length - 1) + " einzugeben.");
				}
			}
		}

		System.out.print("\n");
		System.out.println("Board von Team: " + TEAM_2);
		team2Ki.printBoard();
		System.out.println("Board von Team: " + TEAM_1);
		team1Ki.printBoard();
	}

	public static void main(String[] args){	
		//Messe die Zeit des gesamten Turniers
		long wholeTimeForBattle = System.currentTimeMillis();
		//KI versus KI
		while(actualRound <= MAX_ROUNDS){
			//Bereite Spiel vor
			long wholeTimeForRound = System.currentTimeMillis();
			boolean isRunning = true;
			actualTurnNumber = 1;
			if(recentBeginner.equals(TEAM_1)){
				System.out.println(TEAM_1 + " beginnt das Spiel");
				team2Ki.setFirst(true);
				team1Ki.setFirst(false);
				printOnlyThisBoardWhilePlaying.printBoard();
			}
			else{
				System.out.println(TEAM_2 + " beginnt das Spiel");
				team2Ki.setFirst(false);
				team1Ki.setFirst(true);
				printOnlyThisBoardWhilePlaying.printBoard();
			}

			//Spiele KI versus KI
			while(isRunning){
				if(actualTurn.equals(TEAM_1)){
					if(team1Ki.isRunning()){
						long time = System.currentTimeMillis();
						//TODO
						int move = team1Ki.getMove();
						time = System.currentTimeMillis() - time;
						if(time > MAX_TIME_FOR_TURN){
							kOCauseTimeOut(time, TEAM_1);
							break;
						}
						if(!team2Ki.sendMove(move)){
							wrongTurn(move, TEAM_1, team1Ki, team2Ki);
						}
						System.out.println("Zug " + actualTurnNumber + " " + TEAM_1 + " zieht " + move);
						printOnlyThisBoardWhilePlaying.printBoard();
						actualTurnNumber++;
						actualTurn = TEAM_2;
					}
					else{
						printResult();
						isRunning = false;
					}			
				}
				else{
					if(team2Ki.isRunning()){
						long time = System.currentTimeMillis();
						int move = team2Ki.getMove();
						time = System.currentTimeMillis() - time;
						if(time > MAX_TIME_FOR_TURN){
							kOCauseTimeOut(time, TEAM_2);
							break;
						}
						if(!team1Ki.sendMove(move)){
							wrongTurn(move, TEAM_2, team2Ki, team1Ki);
						}
						System.out.println("Zug " + actualTurnNumber + " " + TEAM_2 + " zieht " + move);
						printOnlyThisBoardWhilePlaying.printBoard();
						actualTurnNumber++;
						actualTurn = TEAM_1;
					}
					else{
						printResult();
						isRunning = false;
					}
				}
			}
			actualRound++;
			switchBeginner();
			wholeTimeForRound = System.currentTimeMillis() - wholeTimeForRound;
			//Gib aktuellen Stand aus
			System.out.println("Diese Runde hat " + (wholeTimeForRound/1000) + " Sekunden gedauert");
			printStatistic();
		}
		//Spiel vorbei
		wholeTimeForBattle = System.currentTimeMillis() - wholeTimeForBattle;
		System.out.println("\n\nErgebnis der Turnierrunde zwischen " + TEAM_2 + " und " + TEAM_1 + " Dauer des gesamten Turniers " + (wholeTimeForBattle/1000) + " Sekunden");
		printStatistic();
	}
}