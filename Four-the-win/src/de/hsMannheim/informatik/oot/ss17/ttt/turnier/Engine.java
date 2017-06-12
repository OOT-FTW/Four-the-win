package de.hsMannheim.informatik.oot.ss17.ttt.turnier;

/**
 * Klassen Namen für die Engines der einzelnen Teams damit es keine Konflikte gibt:
 * Tornado Tag Team: TornadoTagTeamEngine
 * Bravo Team: BravoTeamEngine
 * MindMaze: MindMazeEngine
 * 
 * In den Unterlagen steht das es Gruppennamen + Gruppennummer sein soll aber ich konnte keine Gruppennummer finden
 */
/**
 * Eine Engine repräsentiert eine KI und Spielfeld von einem Team.
 * Das Spielfeld ist immer als 10x10 definiert.
 * Als erstes wird setFirst(boolean) aufgerufen. 
 * Bei der ersten Engine wird danach immer isRunnging() aufgerufen . Wenn false dann wird getMatchResult aufgerufen.
 * Ansonsten, bei true, wird getMove aufgerufen.
 * Danach wird sendMove bei der anderen Engine aufgerufen.
 * Das ist der grobe Ablauf. Alles weitere ist dann in der Duell Klasse beschrieben.
 * @author David Nadoba (dnadoba@gmail.com)
 *
 */
public interface Engine {
	/**
	 * Fragt das jeweilige Programm, ob das Spiel noch läuft (Rückgabe true) oder 
	 * ob einer der beiden Spieler gewonnen bzw. kein weiterer Zug mehr möglich ist (Rückgabe false)
	 * 
	 * @return true wenn das Spiel noch läuft, ansonsten false
	 */
	public boolean isRunning();
	
	/**
	 * Unter der Voraussetzung, dass das Spiel beendet ist (also isRunning() false zurück gibt), 
	 * wird diese Methode ausgeführt und fragt, ob diese Engine (also `this`) gewonnen (Rückgabewert: 1) oder 
	 * verloren (Rückgabewert: -1) hat oder es ein Unentschieden gab (Rückgabewert: 0)
	 * 
	 * @return 1 wenn man selbst gewonnen hat, -1 wenn man verloren hat, 0 bei unentschieden
	 */
	public int getMatchResult();
	/**
	 * Signalisiert dem jeweiligen Programm, dass es beginnt (Parameter: true), 
	 * ansonsten ist es als zweiter am Zug (Parameter: false)
	 * 
	 * @param isFirst true wenn die engine den ersten Zug machen wird, ansonsten false
	 */
	public void setFirst(boolean isFirst);
	
	/**
	 * Die Engine berechnet einen Zug und führ ihn bei sich intern aus. 
	 * Damit die andere bzw. gegnerische Engine den Zug auch ausführen kann gibt die Funktionen auch den Zug zurück.
	 * 
	 * ACHTUNG! Diese Methode darf maximal 10 Sekunden benötigen! Ansonsten hat die Engine automatisch verloren!
	 * 
	 * @return Feld von dem der Schiebestein eingeworfen wurde
	 */
	public int getMove();
	/**
	 * Übergibt den Zug die die andere bzw. gegnerische Engine berechnet hat und führt ihn bei sich selbst aus.
	 * Die Funktion wird ausgeführt um die Spielfelder beider engines synchron zu halten und 
	 * zu überprüfen um der Zug überhaupt korrekt war!
	 * Wenn der Zug ungültig ist gibt die Funktion false zurück.
	 * 
	 * @param field Feld von dem der Schiebestein eingeworfen wurde
	 * @return true wenn der Zug gültig und erfolgreich ausgeführt wurde, ansonsten false
	 */
	public boolean sendMove(int field);
	/**
	 * Druckt das Spielbrett im aktuellen Zustand auf der Konsole aus.
	 */
	void printBoard();
	
}
