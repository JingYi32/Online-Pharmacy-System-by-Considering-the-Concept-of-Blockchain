package BCD;

import java.util.InputMismatchException;

import Controller.*;

public class App {
	
	public static void main(String[] args){
		try {
			switch(WelcomeController.getInput(WelcomeController.roleSelection())) {
			case 1:
				 new Login().Login();
				break;
			case 2:
				Admin.admin();
				break;
			}
		}catch(InputMismatchException e) {
			System.out.println("Wrong Input!");
			WelcomeController.getInput(WelcomeController.roleSelection());
		}

	}
}
