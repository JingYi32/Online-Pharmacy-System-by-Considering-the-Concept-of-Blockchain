package BCD;

import java.util.List;
import java.util.Scanner;

import Controller.*;
import blockchain.KeyGen;


public class Signin {
	public Signin() throws Exception{
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("==================================================");
		System.out.println();
		System.out.println("-- Signin Page --");
		System.out.println("Enter username: ");
		String username = sc.next();
		App.user = username;
		List<String> usrLst = ReadFile.read( "login.txt" );
		if (usrLst != null) {
			boolean i = usrLst.stream().anyMatch(elem -> elem.startsWith(username));
			if(i) {
				System.out.println("Username exist!");
			}
		}
		System.out.println("Enter password: ");
		String pwd = sc.next();
		new SigninController();
		SigninController.create(username, pwd);
		KeyGen.create(username);
		ProductPage.product();
	}
}
