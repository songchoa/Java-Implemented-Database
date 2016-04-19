import java.util.Scanner;
import java.util.ArrayList;

public class Toy {
	public static void main(String [] args) {
		
		if(args.length > 0) {
			
			if(args[0].equals("create")) {
				
				Scanner scan = new Scanner(System.in);
				String filename = args[1];
				Table tempTable = new Table(filename);

				boolean stop = false;

				while(!stop) {
					System.out.print("Attribute name: ");
					String attributeName = scan.next();
					System.out.print("Select a type (1. integer; 2. double; 3. boolean; 4. string): ");
					int type = scan.nextInt();
					tempTable.addAttribute(new Attribute(attributeName, type));
					System.out.println("More Attribute? (y/n): ");
					if(scan.next().charAt(0) == 'n') {
						tempTable.toFile(filename);
						stop = true;
					}
				}

				scan.close();
			}

			if(args[0].equals("header")) {
				
				String filename = args[1];
				
				Table tempTable = new Table(filename);

				tempTable.toTable();

				ArrayList<Attribute> aArray = tempTable.getAttributes();

				String result = tempTable.getNumOfAttributes() + " attributes\n";

				int index = 0;

				for(Attribute a : aArray) {
					if(a.getType() == 1){
						result += "Attribute " + (++index) + ": " + a.getName() + ", integer\n";
					}
					if(a.getType() == 2){
						result += "Attribute " + (++index) + ": " + a.getName() + ", double\n";
					}
					if(a.getType() == 3){
						result += "Attribute " + (++index) + ": " + a.getName() + ", boolean\n";
					}
					if(a.getType() == 4){
						result += "Attribute " + (++index) + ": " + a.getName() + ", string\n";
					}

				}
				result += tempTable.getNumOfRecords() + " records";
				System.out.println(result);

			}

			if(args[0].equals("insert")) {
				String filename = args[1];
				Table tempTable = new Table(filename);
				tempTable.toTable();
				ArrayList<Attribute> aArray = tempTable.getAttributes();
				Scanner in = new Scanner(System.in);
				Record r = new Record();
				for(Attribute a : aArray) {
					System.out.print(a.getName() + ": ");

					if(a.type == 1) {
						int temp = in.nextInt();
						r.addValue(String.valueOf(temp));
					}

					if(a.type == 2) {
						double temp = in.nextDouble();
						r.addValue(String.valueOf(temp));
					}

					if(a.type == 3) {
						char c = in.next().charAt(0);
						if(c != 'T' && c != 'F') {
							System.err.println("Only 'T' or 'F' allowed.");
							return;
						} else {
							r.addValue(String.valueOf(c));
						}
					}

					if(a.type == 4) {
						String temp = in.nextLine();
						r.addValue(temp);
					}
				}

				in.close();

				tempTable.insertRecord(r);
				tempTable.toFile(filename);

			}
		}
		
		
	}
	
}