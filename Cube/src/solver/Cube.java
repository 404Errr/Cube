//package solver;
//
//import data.MainData;
//import generator.Pointer;
//
//public class Cube implements MainData {
//	private int[][][] cubies;
//
//	public Cube() {
//		reset();
//	}
//
//	public void reset() {
//		cubies = new int[SIZE][SIZE][SIZE];
//	}
//
//	public boolean overLay(Space space) {
//		for (int z = 0;z<d();z++) {
//			for (int y = 0;y<h();y++) {
//				for (int x = 0;x<w();x++) {
//					int cubie = space.getLayout().get(x, y, z);
//					if (cubie!=0) {
//						if (cubies[z][y][x]==0) {
//							cubies[z][y][x] = cubie;
//						}
//						else {//collision
//							return false;
//						}
//					}
//				}
//			}
//		}
//
//		return true;
//	}
//
//	public int get(Pointer pointer) {
//		return cubies[pointer.getZ()][pointer.getY()][pointer.getX()];
//	}
//
//	public int get(int x, int y, int z) {
//		return cubies[z][y][x];
//	}
//
//	public boolean isOccupied(int x, int y, int z) {
//		return cubies[z][y][x]!=0;
//	}
//
//	public boolean isFull() {
//		for (int z = 0;z<d();z++) {
//			for (int y = 0;y<h();y++) {
//				for (int x = 0;x<w();x++) {
//					if (!isOccupied(x, y, z)) return false;
//				}
//			}
//		}
//		return true;
//	}
//
//	public int d() {
//		return cubies.length;
//	}
//
//	public int h() {
//		return cubies[0].length;
//	}
//
//	public int w() {
//		return cubies[0][0].length;
//	}
//
//	@Override
//	public String toString() {
//		StringBuilder str = new StringBuilder();
//		for (int z = 0;z<d();z++) {
//			for (int y = 0;y<h();y++) {
//				for (int x = 0;x<w();x++) {
//					str.append(get(x, y, z));
//				}
//				str.append("\n");
//			}
//			if (z<d()-1) {
//				for (int i = 0;i<w();i++) str.append("-");
//				str.append("\n");
//			}
//		}
//		return str.toString();
//	}
//
//	public boolean inBounds(int x, int y, int z) {
//		return x>=0&&y>=0&&z>=0&&x<w()&&y<h()&&z<d();
//	}
//}
