package de.hsMannheim.informatik.oot.ss17.ttt.model;


public class Menu {
	String[] options;
	MenuPrinter printer;
	public Menu(String[] options) {
		this.options = options;
		printer = new MenuPrinter();
	}
	
	public int getCommand(){
		return printer.print(options);
	}
}
