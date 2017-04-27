//package builder;
//
//import data.BuilderData;
//import main.Layout;
//import main.Pointer;
//
//public class BuildOrder implements BuilderData {
//	Cubie[][][] cubies;
//	Layout layout;
//
//	public BuildOrder(Layout layout) {
//		this.layout = layout;
//		reset();
//	}
//
//	public boolean isComplete() {
//		for (int z = 0;z<d();z++) {
//			for (int y = 0;y<h();y++) {
//				for (int x = 0;x<w();x++) {
//					Cubie cubie = get(x, y, z);
//					if (cubie.getColor()!=0&&cubie.getDirection()=='-') {
//						return false;
//					}
//				}
//			}
//		}
//		return true;
//	}
//
//	public Pointer getMinNeighbors() {
//		Pointer min = new Pointer(0, 0, 0);
//		for (int z = 0;z<d();z++) {
//			for (int y = 0;y<h();y++) {
//				for (int x = 0;x<w();x++) {
//					if (get(x, y, z).getColor()!=0) {
////						int neighbors = 0;//TODO
//						min = new Pointer(x, y, z);
//					}
//				}
//			}
//		}
//		return min;
//	}
//
//	public void reset() {
//		cubies = new Cubie[layout.d()][layout.h()][layout.w()];
//		for (int z = 0;z<layout.d();z++) {
//			for (int y = 0;y<layout.h();y++) {
//				for (int x = 0;x<layout.w();x++) {
//					cubies[z][y][x] = new Cubie(x, y, z, layout.get(x, y, z));
//				}
//			}
//		}
//	}
//
//	public Cubie get(int x, int y, int z) {
//		return cubies[z][y][x];
//	}
//
//	public void set(int x, int y, int z, Cubie val) {
//		cubies[z][y][x] = val;
//	}
//
//	public Cubie[][][] getLayout() {
//		return cubies;
//	}
//
//	public int d() {//depth
//		return cubies.length;
//	}
//
//	public int h() {//height
//		return cubies[0].length;
//	}
//
//	public int w() {//width
//		return cubies[0][0].length;
//	}
//
//	public void set(Pointer pointer, Cubie val) {
//		cubies[pointer.getZ()][pointer.getY()][pointer.getX()] = val;
//	}
//
//	public Cubie get(Pointer pointer) {
//		return cubies[pointer.getZ()][pointer.getY()][pointer.getX()];
//	}
//
//	public boolean inBounds(Pointer pointer) {
//		return pointer.getX()>=0&&pointer.getY()>=0&&pointer.getZ()>=0&&pointer.getX()<w()&&pointer.getY()<h()&&pointer.getZ()<d();
//	}
//
//	public String layoutToString() {
//		StringBuilder str = new StringBuilder();
//		for (int z = 0;z<d();z++) {
//			for (int y = 0;y<h();y++) {
//				for (int x = 0;x<w();x++) {
//					str.append(get(x, y, z).getColor());
//				}
//				str.append("\n");
//			}
//			if (z<d()-1) {
//				for (int i = 0;i<w();i++) str.append("=");
//				str.append("\n");
//			}
//		}
//		return str.toString();
//	}
//
//	public String directionToString() {
//		StringBuilder str = new StringBuilder();
//		for (int z = 0;z<d();z++) {
//			for (int y = 0;y<h();y++) {
//				for (int x = 0;x<w();x++) {
//					str.append((char)get(x, y, z).getDirection());
//				}
//				str.append("\n");
//			}
//			if (z<d()-1) {
//				for (int i = 0;i<w();i++) str.append("=");
//				str.append("\n");
//			}
//		}
//		return str.toString();
//	}
//}
