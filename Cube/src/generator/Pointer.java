package generator;

public class Pointer implements GeneratorData {
	private int x, y, z;

	public Pointer(int x, int y, int z) {
		set(x, y, z);
	}

	public Pointer getOffset(int xO, int yO, int zO) {
		return new Pointer(x+xO, y+yO, z+zO);
	}

	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	@Override
	public String toString() {
		return x+", "+y+", "+z;
	}


}


