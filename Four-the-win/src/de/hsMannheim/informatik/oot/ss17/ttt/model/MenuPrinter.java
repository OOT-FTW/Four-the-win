package de.hsMannheim.informatik.oot.ss17.ttt.model;

import java.util.Scanner;

public class MenuPrinter {
	public int print(String[] options){
		for(int i=0; i<options.length; i++){
			System.out.print("| "+options[i]+" ");
		}
		System.out.print("|\n");
		System.out.print("Please choose an Option: \n");
		Scanner UI = new Scanner(System.in);
		String command = UI.nextLine();
		for(int i=0; i<options.length;i++){
			if (command.equals(options[i])){
				return i+1;
			}
		}return -1;
	}
}
