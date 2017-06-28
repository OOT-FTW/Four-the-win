package de.hsMannheim.informatik.oot.ss17.ttt.view;

import de.hsMannheim.informatik.oot.ss17.ttt.model.*;

public class GameBoardPrinter {
	
	private static final char symbolFirstPlayer = 'O', symbolSecondPlayer = 'X';
    private GameBoard board;
    private int rows, columns;
    private int numberOfInserts;
    private String[][] skeleton;
    
    /**
     * Creates a new GameBoardPrinter for a specific board.
     * @param board
     */
    public GameBoardPrinter(GameBoard board) {
        this.board = board;
        this.rows = board.getRows();
        this.columns = board.getColumns();
        this.numberOfInserts = 2 * this.rows + 2 * this.columns;
        this.skeleton = new String[2 * columns + 1][2 * rows + 1];
    }

    private String[][] initSkeleton(int offset) {
        if (offset == 0) {
            for (int i = 0; i < skeleton.length; i++) {
                for (int j = 0; j < skeleton[0].length; j++) {
                    if (j % 2 == 0) {
                        skeleton[i][j] = "|";
                    }
                    if (i % 2 == 0 && skeleton[i][j] != "|") {
                        skeleton[i][j] = "––";
                    }
                    if (i % 2 != 0 && j % 2 != 0) {
                        skeleton[i][j] = "  ";
                    }
                }
            }
        } else {
            for (int i = 0; i < skeleton.length; i++) {
                for (int j = 0; j < skeleton[0].length; j++) {
                    if (j % 2 == 0) {
                        skeleton[i][j] = "|";
                    }
                    if (i % 2 == 0 && skeleton[i][j] != "|") {
                        skeleton[i][j] = "–––";
                    }
                    if (i % 2 != 0 && j % 2 != 0) {
                        skeleton[i][j] = "   ";
                    }
                }
            }
        }
        skeleton[0][0] = "+";
        skeleton[0][skeleton[0].length - 1] = "+";
        skeleton[skeleton.length - 1][0] = "+";
        skeleton[skeleton.length - 1][skeleton[0].length - 1] = "+";
        return skeleton;
    }
    
    /**
     * Prints the game board to the console.
     */
    public void print() {
        if (board == null) {
            System.out.println("Field not initialized.");
            return;
        }
        if (Math.min(rows, columns) < 6 || Math.max(rows, columns) < 7 ||
        	Math.max(rows, columns) > 13 || Math.min(rows, columns) > 11 ) {
            System.out.println("Field not properly initialized.");
        }
        
            initSkeleton(1);
        
        System.out.println();
        System.out.println("Spielfeld:");
        System.out.println();
        
        // fill board:
        for(int i=0; i<rows; i++){
        	for(int j=0; j<columns;j++){
        		if(board.getTokenAt(i, j).equals(GameToken.FIRST_PLAYER)){
        			skeleton[2*j+1][2*i+1] = " "+symbolFirstPlayer+" ";
        		}else if(board.getTokenAt(i, j).equals(GameToken.SECOND_PLAYER)){
        			skeleton[2*j+1][2*i+1] = " "+symbolSecondPlayer+" ";
        		}else{
        			skeleton[2*j+1][2*i+1] = "   ";
        		}
        	}
        }
        
        // create left and right numeration
        String[] left = new String[columns];
        String[] right = new String[columns];
        for (int i = 0; i < columns; i++) {
            left[i] = (numberOfInserts - i) + "";
            if((columns + (i+1))<10){
            right[i] = "0"+(columns + (i + 1)) + "";
            }else{
                right[i] =(columns + (i + 1)) + "";
            }
            
        }
        
        // print top level numeration:
        System.out.print("   ");
        for (int i = 0; i < rows; i++) {
            if (i + 1 < 10 && numberOfInserts < 100) {
                System.out.print(" 0" + (i + 1) + " ");
            } else if (i + 1 < 10 && numberOfInserts >= 100) {
                System.out.print(" 00" + (i + 1) + " ");
            } else if( i+1 >= 10 && numberOfInserts <100){
                System.out.print(" "+(i + 1)+" ");
            } else{
                System.out.print(" 0"+(i+1)+" ");
            }
        }
        
        // print board and left and right numeration
        System.out.println();
        if (numberOfInserts < 100) {
            int h = 0;
            for (int i = 0; i < skeleton.length; i++) {
                if (i % 2 != 0) {
                    System.out.print(left[h]);
                    for (int j = 0; j < skeleton[0].length; j++) {
                        System.out.print(skeleton[i][j]);
                    }
                    System.out.print(right[h]);
                    System.out.println();
                    h++;
                } else {
                    System.out.print("  ");
                    for (int j = 0; j < skeleton[0].length; j++) {
                        System.out.print(skeleton[i][j]);
                    }
                    System.out.print("  ");
                    System.out.println();
                }
            }
        } else {
            int h = 0;
            for (int i = 0; i < skeleton.length; i++) {
                if (i % 2 != 0) {
                    System.out.print(left[h]);
                    for (int j = 0; j < skeleton[0].length; j++) {
                        System.out.print(skeleton[i][j]);
                    }
                    System.out.print(right[h]);
                    System.out.println();
                    h++;
                } else {
                    System.out.print("   ");
                    for (int j = 0; j < skeleton[0].length; j++) {
                        System.out.print(skeleton[i][j]);
                    }
                    System.out.print("   ");
                    System.out.println();
                }
            }
        }
        
        // print bottom level numeration
        System.out.print("   ");
        for (int i = 0; i < rows; i++) {
            System.out.print(" "+(numberOfInserts - rows - i)+" ");
        }
        System.out.println("\n");
        
        
        
    }
    
    public void printWinner(GameResult result, Player winner) {
        System.out.println("Das Spiel ist beendet.");
        if (result == null) {
            System.out.println("Es konnte kein Gewinner festgestellt werden.");
        } else if (result.equals(GameResult.DRAW)) {
            System.out.println("Das Spiel ist unentschieden ausgegangen.");
        } else if (result.equals(GameResult.NONE)) {
            System.out.println("Kein Spieler hat gewonnen.");
        } else if (result.equals(GameResult.FIRST_PLAYER_WON) || result.equals(GameResult.SECOND_PLAYER_WON)) {
            System.out.println("Spieler \"" + winner.getName() + "\" hat gewonnen.");
        }
    }
}

