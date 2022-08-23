package Controller;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class WelcomeController {
	
	public static Optional<Integer> roleSelection() {
		Scanner sc = new Scanner(System.in);  
		System.out.println("Welcome to the System!");
		System.out.println("Kindly select the role to access the system:"
				+ "\n1. Customer"
				+ "\n2. Admin"
				+ "\nPlease enter the number: ");
		
		return Optional.ofNullable(sc.nextInt());
	}
	
	public static int getInput(Optional<Integer> v) {
		try {
			while(!v.equals(Optional.ofNullable(1)) && !v.equals(Optional.ofNullable(2))){
				System.out.println("Wrong Input!");
				v = roleSelection();
			}
		}
		catch (InputMismatchException exception) {
			System.out.println("Wrong Input!");
			v = roleSelection();
			while(!v.equals(Optional.ofNullable(1)) && !v.equals(Optional.ofNullable(2))){
				System.out.println("Wrong Input!");
				v = roleSelection();
			}
		}
		return v.orElse(0);
	}
}
