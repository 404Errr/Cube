package generator;

public class Move implements Comparable<Move> {
	private int x, y, z;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public void add(Move merge) {
		x+=merge.getX();
		y+=merge.getY();
		z+=merge.getZ();
	}
	
//	public Move(int[] move) {
//		x = move[0];
//		y = move[1];
//		z = move[2];
//	}

	public Move(int x, int y, int z) {
		move(x, y, z);
	}

	@Override
	public String toString() {
		return x+", "+y+", "+z;
	}
	
	public int diff() {
		return Math.abs(x)+Math.abs(y)+Math.abs(z);
	}
	
	public void move(int xO, int yO, int zO) {
		this.x = xO;
		this.y = yO;
		this.z = zO;
	}

	@Override
	public int compareTo(Move other) {
		int netTotal = Generator.getNet().diff();
		if (this.diff()-netTotal>other.diff()-netTotal) return 1;
		if (this.diff()-netTotal<other.diff()-netTotal) return -1;
		return 0;
	}
}
