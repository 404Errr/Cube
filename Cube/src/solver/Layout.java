package solver;

public class Layout {
	private int[][][] layout;

	public Layout(int[][][] layout) {
		this.layout = layout;
	}

	public void rotate(int xRotations, int yRotations, int zRotations) {
		while (xRotations<0) xRotations+=4;
		while (yRotations<0) yRotations+=4;
		while (zRotations<0) zRotations+=4;
		for (int zR = 0;zR<zRotations;zR++) {
			int[][][] rotated = new int[d()][w()][h()];
			for (int z = 0;z<d();z++) {
				for (int y = 0;y<w();y++) {
					for (int x = 0;x<h();x++) {
						rotated[z][y][x] = layout[z][h()-x-1][y];
					}
				}
			}
			layout = rotated;
		}
		for (int yR = 0;yR<yRotations;yR++) {
			int[][][] rotated = new int[w()][h()][d()];
			for (int z = 0;z<w();z++) {
				for (int y = 0;y<h();y++) {
					for (int x = 0;x<d();x++) {
						rotated[z][y][x] = layout[d()-x-1][y][z];
					}
				}
			}
			layout = rotated;
		}
		for (int xR = 0;xR<xRotations;xR++) {
			int[][][] rotated = new int[h()][d()][w()];
			for (int z = 0;z<h();z++) {
				for (int y = 0;y<d();y++) {
					for (int x = 0;x<w();x++) {
						rotated[z][y][x] = layout[h()-y-1][z][x];
					}
				}
			}
			layout = rotated;
		}
	}

	@Override
	public Layout clone() {
		int[][][] cloneLayout = new int[d()][h()][w()];
		for (int z = 0;z<d();z++) {
			for (int y = 0;y<h();y++) {
				for (int x = 0;x<w();x++) {
					cloneLayout[z][y][x] = layout[z][y][x];
				}
			}
		}
		return new Layout(cloneLayout);
	}

	public int get(int x, int y, int z) {
		return layout[z][y][x];
	}

	public void set(int x, int y, int z, int val) {
		layout[z][y][x] = val;
	}

	public int[][][] getLayout() {
		return layout;
	}

	public int d() {//depth
		return layout.length;
	}

	public int h() {//height
		return layout[0].length;
	}

	public int w() {//width
		return layout[0][0].length;
	}

	public boolean equals(Layout that) {
		if (this.d()!=that.d()||this.h()!=that.h()||this.w()!=that.w()) return false;
		for (int z = 0;z<this.d();z++) {
			for (int y = 0;y<this.h();y++) {
				for (int x = 0;x<this.w();x++) {
					if (this.get(x, y, z)!=that.get(x, y, z)) return false;
				}
			}
		}
		return true;
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
}
