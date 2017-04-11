package main;

import data.MainData;

public class Layout implements MainData {
	private int[][][] cubies;

	public Layout(int size) {
		this.cubies = new int[size][size][size];
	}

	public Layout(int[][][] layout) {
		this.cubies = layout;
	}

	public void trim() {
		int[] tFR = {d(), h(), w()}, dBL = {0, 0, 0};//z, y, x
		for (int z = 0;z<d();z++) {
			for (int y = 0;y<h();y++) {
				for (int x = 0;x<w();x++) {
					if (cubies[z][y][x]!=0) {
						if (z>dBL[Z]) dBL[Z] = z;
						if (y>dBL[Y]) dBL[Y] = y;
						if (x>dBL[X]) dBL[X] = x;
						if (z<tFR[Z]) tFR[Z] = z;
						if (y<tFR[Y]) tFR[Y] = y;
						if (x<tFR[X]) tFR[X] = x;
					}
				}
			}
		}
		int[][][] trimedCubies = new int[dBL[Z]-tFR[Z]+1][dBL[Y]-tFR[Y]+1][dBL[X]-tFR[X]+1];
		for (int z = 0;z<trimedCubies.length;z++) {
			for (int y = 0;y<trimedCubies[0].length;y++) {
				for (int x = 0;x<trimedCubies[0][0].length;x++) {
					trimedCubies[z][y][x] = cubies[z+tFR[Z]][y+tFR[Y]][x+tFR[X]];
				}
			}
		}
		cubies = trimedCubies;
	}

	public void rotate(int xRotations, int yRotations, int zRotations) {//FIXME
		while (xRotations<0) xRotations+=4;
		while (yRotations<0) yRotations+=4;
		while (zRotations<0) zRotations+=4;
		for (int zR = 0;zR<zRotations;zR++) {
			int[][][] rotated = new int[d()][w()][h()];
			for (int z = 0;z<d();z++) {
				for (int y = 0;y<w();y++) {
					for (int x = 0;x<h();x++) {
						rotated[z][y][x] = cubies[z][h()-x-1][y];
					}
				}
			}
			cubies = rotated;
		}
		for (int yR = 0;yR<yRotations;yR++) {
			int[][][] rotated = new int[w()][h()][d()];
			for (int z = 0;z<w();z++) {
				for (int y = 0;y<h();y++) {
					for (int x = 0;x<d();x++) {
						rotated[z][y][x] = cubies[d()-x-1][y][z];
					}
				}
			}
			cubies = rotated;
		}
		for (int xR = 0;xR<xRotations;xR++) {
			int[][][] rotated = new int[h()][d()][w()];
			for (int z = 0;z<h();z++) {
				for (int y = 0;y<d();y++) {
					for (int x = 0;x<w();x++) {
						rotated[z][y][x] = cubies[y][z][w()-1-x];
					}
				}
			}
			cubies = rotated;
		}
	}

	@Override
	public Layout clone() {
		int[][][] cloneLayout = new int[d()][h()][w()];
		for (int z = 0;z<d();z++) {
			for (int y = 0;y<h();y++) {
				for (int x = 0;x<w();x++) {
					cloneLayout[z][y][x] = cubies[z][y][x];
				}
			}
		}
		return new Layout(cloneLayout);
	}

	public boolean append(Layout toAppend) {
		for (int z = 0;z<d();z++) {
			for (int y = 0;y<h();y++) {
				for (int x = 0;x<w();x++) {
					int v = get(x, y, z), vA = toAppend.get(x, y, z);
					if (vA!=0) {
						if (v==0) set(x, y, z, vA);
						else return false;
					}
				}
			}
		}
		return true;
	}

	public boolean append(Layout toAppend, int xO, int yO, int zO) {
		for (int z = 0;z<d();z++) {
			for (int y = 0;y<h();y++) {
				for (int x = 0;x<w();x++) {
					int v = get(x+xO, y+yO, z+zO), vA = toAppend.get(x, y, z);
					if (vA!=0) {
						if (v==0) set(x+xO, y+yO, z+zO, vA);
						else return false;
					}
				}
			}
		}
		return true;
	}

	public int get(int x, int y, int z) {
		return cubies[z][y][x];
	}

	public void set(int x, int y, int z, int val) {
		cubies[z][y][x] = val;
	}

	public int[][][] getLayout() {
		return cubies;
	}

	public int d() {//depth
		return cubies.length;
	}

	public int h() {//height
		return cubies[0].length;
	}

	public int w() {//width
		return cubies[0][0].length;
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
