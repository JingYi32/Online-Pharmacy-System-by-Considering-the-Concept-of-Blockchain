package BCD;

import java.util.InputMismatchException;
import java.util.Scanner;
import Controller.*;

public class App {
	
	public static void main(String[] args){
		try {
			switch(WelcomeController.getInput(WelcomeController.roleSelection())) {
			case 1:
				try {
					switch(verity(LoginOrSignin())) {
					case 1:
						new Signin().Signin();
						break;
					case 2:
						new Login().Login();
						break;
					}
					break;
				}
				catch(InputMismatchException e) {
					System.out.println("Wrong Input!");
					verity(LoginOrSignin());
				}

			case 2:
				Admin.admin();
				break;
			}
		}catch(InputMismatchException e) {
			System.out.println("Wrong Input!");
			WelcomeController.getInput(WelcomeController.roleSelection());
		}
	}
	
	private static int LoginOrSignin() {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("==================================================");
		System.out.println();
		System.out.println("1. Create new account.");
		System.out.println("2. Login to existing account."
				+ "\nPlease enter the number: ");
		return sc.nextInt();
	}
	
	private static int verity(int v) {
		while ((v != 1) && (v != 2)) {
			System.out.println("\nWrong Input!");
			v = LoginOrSignin();
		}
		return v;
	}
}
