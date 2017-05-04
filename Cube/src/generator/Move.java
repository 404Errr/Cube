package generator;

public class Move implements Comparable<Move> {
	protected int x, y, z, diff;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public void add(Move other) {
		set(x+=other.getX(), y+=other.getY(), z+=other.getZ());
	}

	public Move(int x, int y, int z) {
		set(x, y, z);
	}

	@Override
	public String toString() {
		return x+", "+y+", "+z;
	}

	public void setDiff() {
		diff = Math.abs(x)+Math.abs(y)+Math.abs(z);
	}

	public void set(int xO, int yO, int zO) {
		this.x = xO;
		this.y = yO;
		this.z = zO;
		setDiff();
	}

	public int getDiff() {
		return diff;
	}

	@Override
	public int compareTo(Move other) {
		int netDiff = Generator.getNet().getDiff();
		if (this.getDiff()-netDiff<other.getDiff()-netDiff) return -1;
		if (this.getDiff()-netDiff>other.getDiff()-netDiff) return 1;
		return 0;
	}
}
