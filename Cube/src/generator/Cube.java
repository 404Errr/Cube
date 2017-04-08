package generator;

public class Cube implements GeneratorData {
	private int[][][] cubies;

	public Cube() {
		reset();
	}

	public void reset() {
		cubies = new int[SIZE][SIZE][SIZE];
	}

	public void add(Pointer pointer, int val) {
		cubies[pointer.getZ()][pointer.getY()][pointer.getX()] = val;
	}

	public int get(Pointer pointer) {
		return cubies[pointer.getZ()][pointer.getY()][pointer.getX()];
	}

	public int get(int x, int y, int z) {
		return cubies[z][y][x];
	}

	public boolean occupied(Pointer pointer) {
		return cubies[pointer.getZ()][pointer.getY()][pointer.getX()]!=0;
	}

	public boolean occupied(int x, int y, int z) {
		return cubies[z][y][x]!=0;
	}

	public boolean full() {
		for (int z = 0;z<d();z++) {
			for (int y = 0;y<h();y++) {
				for (int x = 0;x<w();x++) {
					if (!occupied(x, y, z)) return false;
				}
			}
		}
		return true;
	}

	public int d() {
		return cubies.length;
	}

	public int h() {
		return cubies[0].length;
	}

	public int w() {
		return cubies[0][0].length;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int z = 0;z<d();z++) {
			for (int y = 0;y<h();y++) {
				for (int x = 0;x<w();x++) {
					str.append(get(x, y, z));
				}
				str.append("\n");
			}
			if (z<d()-1) {
				for (int i = 0;i<w();i++) str.append("-");
				str.append("\n");
			}
		}
		return str.toString();
	}

	public boolean inBounds(Pointer pointer) {
		return pointer.getX()>=0&&pointer.getY()>=0&&pointer.getZ()>=0&&pointer.getX()<w()&&pointer.getY()<h()&&pointer.getZ()<d();
	}

}

