package BCD;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CheckOut {
	private static final DecimalFormat df = new DecimalFormat("0.00");
	public CheckOut(List<Medicine> orderItem) {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("==================================================");
		System.out.println();
		System.out.println("-- Check Out Page --");
		System.out.println();
		System.out.println("Item selected: ");
		System.out.println();
		orderItem
			.stream()
			.forEach(m -> System.out.println(
					m.getID() + "\t||\t" + m.getName() + "\t||\tRM" + df.format(m.getPrice()) + "\n"));
		System.out.println();
		System.out.println("Total Amount\t: RM " + df.format(orderItem.stream().collect(Collectors.summarizingDouble(m -> m.getPrice()))));
		System.out.println();
		System.out.println("Press anykey to confirm checkout and 'x' for modifying the check out item:");
		while(sc.next().equals("x")) {
			ProductPage.product();
		}
	}
}
