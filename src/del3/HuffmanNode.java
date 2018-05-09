package del3;

public class HuffmanNode  {
	private int key;
	private Object left;
	private Object right;

	public HuffmanNode(int x, Object rightNode, Object leftNode) {
		key = x;
		right = rightNode;
		left = leftNode;

	}


	

	public Object returnNode() {
		if (right != null) {
			return right;
		}
		return left;
	}

	public Object returnRight() {
		return right;
	}

	public Object returnLeft() {
		return left;
	}
	public int getKey(){
		return key;
	}

	

}
