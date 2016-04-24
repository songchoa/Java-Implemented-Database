import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;
import java.lang.Math;
import java.util.regex.Pattern;
import java.lang.String;


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

	protected void toFile(){
		
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.print("[" + attributes.size() + "]");
			for (Attribute a : attributes) {
				writer.print("[" + a.getName().trim() + ":" + a.getType() +"]");
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

	protected boolean toTable(){
				
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
						return false;
					}

					while(fileScanner.hasNextLine()) {
						records.add(new Record(fileScanner.nextLine()));
					}

					fileScanner.close();

				} catch (Exception e) {
					System.err.println("The queried file does not exist.");
					return false;
				}

				return true;
			}	

	protected void insertRecord(Record r){
		numberOfRecords += 1; 
		records.add(r);
	}

	protected void showRecordById(int id) {
		String[] temp = records.get(id).parseRecord();
		for (int i = 0; i < temp.length; i++) {
			System.out.println("-> " + attributes.get(i).getName().trim() + ": " + temp[i]);
		}
	}

	protected void deleteRecordById(int id) {
		records.remove(id);
	}

	protected Attribute getAttributeByName(String attri){

		for (int i = 0; i < attributes.size(); i++) {
			if(attributes.get(i).getName().trim().toUpperCase().equals(attri.toUpperCase())) {
				return attributes.get(i);
			}
		}
		return null;
	}

	protected int getAttributeIndex(String attri){
		int index = 0;
		for (int i = 0; i < attributes.size(); i++) {
			if(attributes.get(i).getName().trim().toUpperCase().equals(attri.toUpperCase())) {
				index = i;
				break;
			}
		}

		return index;
	}

	protected void showRecord(String attri, String value){
		Attribute temp = getAttributeByName(attri);
		int numericValue = 0;

		int attriIndex = getAttributeIndex(attri);
		boolean intValue = false;

		if(temp.getType() == 1 || temp.getType() == 2) {
			
			if(Pattern.matches("-*[0-9]+", value.trim())) {
				numericValue = Integer.valueOf(value);
				intValue = true;
			} else {
				double num = Double.valueOf(value);
				numericValue = (int)num;
				intValue = false;
			}

			for (int i = 0; i < records.size(); i++){
				String[] rStrArr = records.get(i).parseRecord();
			
				if(temp.getType() == 1) {
					if(Integer.valueOf(rStrArr[attriIndex]) == numericValue) {
						System.out.println("Record " + i);
						showRecordById(i);
					}

				} else {
						if(Math.floor(Double.valueOf(rStrArr[attriIndex])) == numericValue) {
							System.out.println("Record " + i);
							showRecordById(i);
						}
					}
				
				}
			} else {

				for (int i = 0; i < records.size(); i++){
					String[] rStrArr = records.get(i).parseRecord();
					if(rStrArr[attriIndex].equals(value)){
						System.out.println("Record " + i);
						showRecordById(i);
					}
				
				}
			}
		

		System.out.println("complete.");
	}

}