package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import BCD.*;

public class ProductController {
	
	public void acceptOrder(List<Medicine> orderItem) {
		if (orderItem == null) {
			orderItem = new ArrayList<Medicine>();
		}
		Scanner sc = new Scanner(System.in);
		String opt = "1";
		while (!opt.equals("x")) {
			System.out.println("Kindly enter the product ID that wish to purchase:");
			String x = sc.next();
			Medicine p = ReadFile.readProduct("Medicine.txt").stream().filter(e -> e.getID() == Integer.parseInt(x)).findAny().orElse(null);
			orderItem.add(p);
			System.out.println("\nItem added into cart");
			System.out.println("Do you wish to add more items? (Enter 'x' if you wish to check out item selected):");
			opt = sc.next();
		}
		CheckOut o = new CheckOut(orderItem);
	}
}
