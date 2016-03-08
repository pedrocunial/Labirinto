public class Cell {
	private int i;
	private int j;
	private int rank;
	private Cell parent;

	public Cell(int i, int j) {
		this.i = i;
		this.j = j;
		this.rank = 0;
		this.parent = this;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	private Cell find() {
		if(parent != this) {
			parent = parent.find();
		}
		return parent;
	}

	public boolean union(Cell v) {
		Cell rootU = find();
		Cell rootV = v.find();

		if(rootU == rootV) {
			return false;
		}

		if(rootU.rank < rootV.rank) {
			rootU.parent = rootV;
		}
		else {
			rootV.parent = rootU;

			if(rootU.rank == rootV.rank) {
				rootU.rank++;
			}
		}

		return true;
	}
}
