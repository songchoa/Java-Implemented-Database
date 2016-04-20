import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
						tempTable.toFile();
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

					String input = in.nextLine();

					if(a.getType() == 1) {
						if(Pattern.matches("-*[0-9]+", input.trim())) {
							r.addValue(String.valueOf(input.trim()));
						} else {
							System.out.println("Invalid input.");
							return;
						}
					} else if(a.getType() == 2) {
						if(Pattern.matches("[0-9]+\\.*[0-9]+", input.trim())) {
							r.addValue(String.valueOf(input.trim()));
						}else {
							System.out.println("Invalid input.");
							return;
						}
					} else if(a.getType() == 3) {
						if(input.trim().equals("T") || input.trim().equals("F")) {
							r.addValue(String.valueOf(input.trim()));
						} else {
							System.out.println("Invalid input.");
							return;
						}
					} else {
						r.addValue(String.valueOf(input.trim()));
					}
				 }

				in.close();

				tempTable.insertRecord(r);
				tempTable.toFile();

			}

			if(args[0].equals("display")) {
				int rid = Integer.valueOf(args[1]);
				String filename = args[2];

				Table tempTable = new Table(filename);
				tempTable.toTable();

				if(tempTable.getNumOfRecords() <= rid) {
					System.out.println("queried rid out of range.");
					return;
				}

				tempTable.showRecordById(rid);

			}

			if(args[0].equals("delete")) {
				int rid = Integer.valueOf(args[1]);
				String filename = args[2];

				Table tempTable = new Table(filename);
				tempTable.toTable();

				tempTable.deleteRecordById(rid);
				tempTable.toFile();
			}

			if(args[0].equals("search")) {
				String condition = args[1];
				String filename = args[2];
				Table tempTable = new Table(filename);
				tempTable.toTable();
				String[] splitCondition = condition.trim().split("=");
				String attri = splitCondition[0].trim();
				String value = splitCondition[1].trim();
				tempTable.showRecord(attri, value);

			}
		}
		
		
	}
	
}