
package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while(choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}
	
	public int getIndexFromOptions(Object[] options) {
		int choice = -1;
		while(choice == -1) {
			displayMenuOptions(options);
			choice = getIndexFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if(selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		
		}catch(NumberFormatException e) {
			// error message will be displayed below since choice will be null
		
		if(choice == null) {
			System.out.println("\n*** "+userInput+" is not a valid option ***\n");
			
		}
		if (Integer.parseInt(choice.toString()) <= -1){
			System.out.println("\n*** "+userInput+" is not a valid option ***\n");
			
		}
		//return choice;
		
	}
		return choice;
	}
	
	private int getIndexFromUserInput(Object[] options) {
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if(selectedOption <= options.length) {
				return selectedOption - 1;
			}
		} catch(NumberFormatException e) {
			// error message will be displayed below since choice will be null
		}
		out.println("\n*** "+userInput+" is not a valid option ***\n");
		return -1;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.println(optionNum+") "+options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
}
