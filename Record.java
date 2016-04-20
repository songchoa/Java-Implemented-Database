import java.util.ArrayList;

public class Record {

	private ArrayList<String> recordParts;
	private String record;
	private boolean srcFromString;
	private int rid;

	public Record(){
		recordParts = new ArrayList<String>();
		srcFromString = false;
	}

	public Record(String record) {
		this.record = record;
		srcFromString = true;
	}

	protected void addValue(String part){
		recordParts.add(part);
	}

	protected String formRecord(){
		String result;

		if(srcFromString) {
			result = record;
		} 
		else {
			result = "{";

			for (int i = 0; i < recordParts.size() - 1; i++) {
				result += recordParts.get(i) + "|";
			}

			result += recordParts.get(recordParts.size() - 1) + "}";
		}
		
		return result;
	}

	protected String[] parseRecord(){

		String[] strRecordParts = null;

		if(srcFromString) {
			strRecordParts 
				= record.trim().replace(" ","*").replace("{", "").replace("}", "").replace("|", " ").split(" ");
			for(int i = 0; i < strRecordParts.length; i++) {
				strRecordParts[i] = strRecordParts[i].replace("*", " ");
			}
		}
		else {
			if(recordParts.size() == 0) {
				System.out.println("Record is empty.");
				System.exit(0);
			}

			strRecordParts = new String[recordParts.size()];

			for(int i = 0; i < recordParts.size(); i++) {
				strRecordParts[i] = recordParts.get(i);
			}
		}
				 
		return strRecordParts;
	}

}