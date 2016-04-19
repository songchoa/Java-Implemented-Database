public class Attribute {
	String name;
	int type;

	public Attribute(String name, int type) {
		this.name = name;
		this.type = type;
	}

	protected String getName() {
		return name;
	}

	protected int getType() {
		return type;
	}
}