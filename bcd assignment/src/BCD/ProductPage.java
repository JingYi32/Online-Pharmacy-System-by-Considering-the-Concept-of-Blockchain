package BCD;

import java.text.DecimalFormat;
import java.util.List;
import Controller.ReadFile;

public class ProductPage {
	private static final DecimalFormat df = new DecimalFormat("0.00");
	public static void product() {
		System.out.println();
		System.out.println();
		System.out.println("==================================================");
		System.out.println("-- Product Page --");
		System.out.println();
		List<Medicine> product = ReadFile.readProduct("Medicine.txt");
		product
			.stream()
			.forEach(m -> System.out.println(
					"Product ID\t: " + m.getID() + 
					"\nProduct Name\t: " + m.getName() +
					"\nProduct Price\t: RM " + df.format(m.getPrice()) + "\n"));
		
	}
}
