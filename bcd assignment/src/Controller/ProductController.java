package Controller;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import BCD.*;

public class ProductController {
	
	public void acceptOrder(List<Medicine> orderItem) {
		Scanner sc = new Scanner(System.in);
		String opt = "1";
		while (opt != "x") {
			System.out.println("Kindly enter the product ID that wish to purchase:");
			Medicine p = (Medicine) ReadFile.readProduct("Medicine.txt").stream().filter(e -> e.getID() == sc.nextInt());
			orderItem.add(p);
			System.out.println("\nItem added into cart");
			System.out.println("Do you wish to add more items? (Enter 'x' if you wish to check out item selected):");
			opt = sc.nextLine();
		}
		sc.close();
		CheckOut o = new CheckOut(orderItem);
	}
}
