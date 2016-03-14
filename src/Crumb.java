public class Crumb {
	int x, y;
	private int passes;

	public Crumb(int x, int y) {
		this.x = x;
		this.y = y;
		this.passes = 0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}	
	
	public int getPasses() {
		return passes;
	}

	public void incrementPasses() {
		passes++;
	}
}
