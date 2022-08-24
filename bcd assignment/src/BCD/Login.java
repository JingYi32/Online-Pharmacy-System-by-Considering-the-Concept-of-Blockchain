package BCD;

import java.util.Scanner;

import Controller.LoginController;

public class Login {
	public void Login() {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println();
		System.out.println("==================================================");
		System.out.println("-- Login Page --");
		System.out.println();
		System.out.println("Enter username: ");
		String username = sc.next();
		System.out.println("Enter password: ");
		String pwd = sc.next();
		boolean v = new LoginController().verify(username, pwd);
		while(!v) {
			System.out.println("Wrong Credential!");
			System.out.println("==================================================");
			System.out.println("Enter username: ");
			System.out.println("Enter password: ");
			v = new LoginController().verify(username, pwd);
		}
		System.out.println("Login Successfully");
		sc.close();
		Product.product();
	}
}