package BCD;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Controller.ReadFile;
import Controller.SignatureController;
import blockchain.Blockchain;
import blockchain.KeyAccess;

public class AdminPage {
	public AdminPage() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println();
		System.out.println("==================================================");
		System.out.println("-- Login Page --");
		System.out.println();
		System.out.println("Enter username: ");
		String username = sc.next();
		App.user = username;
		System.out.println("Enter password: ");
		String pwd = sc.next();
		while (!username.equalsIgnoreCase("admin") && !pwd.equalsIgnoreCase("12345")) {
			System.out.println("Wrong Credential!");
			System.out.println("==================================================");
			System.out.println("Enter username: ");
			username = sc.next();
			System.out.println("Enter password: ");
			pwd = sc.next();
		}
		show();
	}
	
	public void show() throws Exception {
		System.out.println();
		System.out.println();
		System.out.println("==================================================");
		System.out.println("-- Login Page --");
		System.out.println();
		List<String> m = ReadFile.read("trnxpool.txt");
		List<Order> x = new ArrayList<Order>();
		for (String d : m) {
			String[] f = d.split("\\|");
			x.add(new Order(
					f[0],
					f[1],
					f[2],
					splitString(f[3]),
					Double.parseDouble(f[4]),
					LocalDateTime.parse(f[5]),
					f[6],
					f[7]));
		}
		x.stream().forEach(r -> System.out.println(
				(x.indexOf(r)+1) + "." +
				"\nOrder ID\t: " + r.getOrderID() +
				"\nAmount\t\t: " + Double.toString(r.getPaymentAmount())));
		Scanner sc = new Scanner(System.in);
		SignatureController sig = new SignatureController();
		System.out.print("Enter the no. of order to verify: ");
		String opt = sc.next();
		Order o = x.stream().filter(s -> x.get(Integer.parseInt(opt)-1).getOrderID() == s.getOrderID()).findAny().orElse(null);
		boolean v = sig.verify(Double.toString(o.getPaymentAmount()), o.getSignature(), KeyAccess.getPublicKey("KeyPair/PublicKey"+o.getUsername()));
		if(v) {
			System.out.print("Valid!");
			Blockchain b = new Blockchain();
		} else {
			System.out.print("Invalid!");
		}
	}
	
	public List<Medicine> splitString(String a) {
		List<Medicine> mlist = new ArrayList<Medicine>();
		String[] b = removeFirstandLast(a).split(",");
		for (String c : b) {
			String[] d = c.split("\\$",-1);
			mlist.add(new Medicine(Integer.parseInt(d[0]), d[1], Double.parseDouble(d[2])));
		}
		return mlist;
	}
	
    public static String removeFirstandLast(String str)
    {
    	
        str = str.replace("[", "");
        str = str.replace("]", "");
        return str;
    }
    
    
}
