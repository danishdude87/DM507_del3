package del3;

public class Element {
	int key;
	private Object data;

	public Element(int i, Object o) {
		this.key = i;
		this.data = o;
	}

	public int getKey() {
		return key;
	}

	public Object getObject() {
		return data;
	}
}
