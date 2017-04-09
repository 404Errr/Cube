package main;

import java.util.Arrays;

import data.MainData;

public class Layout implements MainData {
	private int[][][] cubies;

	public Layout(int[][][] layout) {
		this.cubies = layout;
	}

	public void trim() {
		int[] tFR = {0, 0, 0}, dBL = {d(), h(), w()};//z, y, x
		for (int z = 0;z<d();z++) {
			for (int y = 0;y<h();y++) {
				for (int x = 0;x<w();x++) {
					if (cubies[z][y][x]!=0) {
						dBL[X] = x;
						dBL[Y] = y;
						dBL[Z] = z;
					}
				}
			}
		}
		for (int z = d()-1;z>=0;z--) {
			for (int y = h()-1;y>=0;y--) {
				for (int x = w()-1;x>=0;x--) {
					if (cubies[z][y][x]!=0) {
						tFR[X] = x;
						tFR[Y] = y;
						tFR[Z] = z;
					}
				}
			}
		}
		int[][][] trimedCubies = new int[dBL[Z]-tFR[Z]+1][dBL[Y]-tFR[Y]+1][dBL[X]-tFR[X]+1];
		for (int z = 0;z<trimedCubies.length;z++) {
			for (int y = 0;y<trimedCubies[0].length;y++) {
				for (int x = 0;x<trimedCubies[0][0].length;x++) {
					trimedCubies[z][y][x] = cubies[z+tFR[Z]][y+tFR[Y]][x+tFR[X]];//FIXME
				}hey look over here!!!
			}
		}
		cubies = trimedCubies;
		System.out.println("tfr: "+Arrays.toString(tFR));
		System.out.println("dbl: "+Arrays.toString(dBL));
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
						rotated[z][y][x] = cubies[h()-y-1][z][x];
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
