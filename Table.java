import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;


public class Table {
	private int numberOfAttributes;
	private ArrayList<Attribute> attributes;
	private ArrayList<Record> records;
	private int numberOfRecords;	
	private String filename;

	public Table(String filename) {
		attributes = new ArrayList<Attribute>();
		records = new ArrayList<Record>();
		this.filename = filename;
	}

	protected void addAttribute(Attribute a) {
		attributes.add(a);
	}

	protected ArrayList<Attribute> getAttributes(){
		return attributes;
	}

	protected int getNumOfAttributes(){
		return numberOfAttributes;
	}

	protected int getNumOfRecords(){
		return numberOfRecords;
	}

	protected ArrayList<Record> getRecords(){
		return records;
	}

	protected void toFile(String filename){
		
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.print("[" + attributes.size() + "]");
			for (Attribute a : attributes) {
				writer.print("[" + a.name + ":" + a.type +"]");
			}

			writer.print("[" + records.size() + "]\n");

			for(Record r : records) {
				writer.println(r.formRecord());
			}

			writer.close();

		} catch (Exception e) {
			System.err.println("File not found.");
		}
		
	}

	protected void toTable(){
				
				try{
					File f = new File(filename);
					Scanner fileScanner = new Scanner(f);
					if(fileScanner.hasNextLine()) {
					
						String[] parts = fileScanner.nextLine().replace("][", " ").replace("[", "").replace("]", "").split(" ");
						numberOfAttributes = Integer.valueOf(parts[0]);

						for (int i = 1; i < parts.length - 1; i++) {
							String[] aParts = parts[i].split(":");
							attributes.add(new Attribute(aParts[0],Integer.valueOf(aParts[1])));
						}
						
						numberOfRecords = Integer.valueOf(parts[parts.length - 1]);		

					} else {
						System.err.println("The queried file is empty.");
					}

					fileScanner.close();

				} catch (Exception e) {
					System.err.println("The queried file does not exist.");
				}
			}	

	protected void insertRecord(Record r){
		numberOfRecords += 1; 
		records.add(r);
	}


}