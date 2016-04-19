import java.util.ArrayList;

public class Record {

	ArrayList<String> recordParts;

	public Record(){
		recordParts = new ArrayList<String>();
	}
	protected void addValue(String part){
		recordParts.add(part);
	}

	protected String formRecord(){
		String result;
		result = "{";

		for (int i = 0; i < recordParts.size() - 1; i++) {
			result += recordParts.get(i) + "|";
		}

		result += recordParts.get(recordParts.size() - 1) + "}";

		return result;
	}
}