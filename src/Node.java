public class Node {
	private int valueX;
	private int valueY;
	private Node left;
	private Node right;
	private Node up;
	private Node down;

	public Node(int valueX, int valueY) {
		this.valueX = valueX;
		this.valueY = valueY;
		this.left = null;
		this.right = null;
		this.up = null;
		this.down = null;
	}

	public Node getUp() {
		return up;
	}

	public void setUp(Node up) {
		this.up = up;
	}

	public Node getDown() {
		return down;
	}

	public void setDown(Node down) {
		this.down = down;
	}

	public int getValueX() {
		return valueX;
	}
	
	public int getValueY() {
		return valueY;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
}