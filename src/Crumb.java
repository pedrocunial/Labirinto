public class Crumb {
	private Node node;
	private int passes;

	public Crumb(Node node) {
		this.node = node;
		this.passes = 0;
	}

	public Node getNode() {
		return node;
	}

	public int getPasses() {
		return passes;
	}

	public void incrementPasses() {
		passes++;
	}
}
