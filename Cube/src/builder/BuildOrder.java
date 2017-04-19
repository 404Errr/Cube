package builder;

import main.Layout;
import main.Pointer;

public class BuildOrder {
	Cubie[][][] cubies;
	
	public BuildOrder(Layout layout) {
		cubies = new Cubie[layout.d()][layout.h()][layout.w()];
		for (int z = 0;z<layout.d();z++) {
			for (int y = 0;y<layout.h();y++) {
				for (int x = 0;x<layout.w();x++) {
					cubies[z][y][x] = new Cubie(x, y, z, layout.get(x, y, z));
				}
			}
		}
	}
	
	public boolean isComplete() {
		for (int z = 0;z<d();z++) {
			for (int y = 0;y<h();y++) {
				for (int x = 0;x<w();x++) {
					Cubie cubie = get(x, y, z);
					if (cubie.getColor()!=0&&cubie.getDirection()=='-') {
						return false;
					}
				}
			}
		}
		return true;
	}

	public Pointer getMinNeighbors() {
		Pointer min = new Pointer(0, 0, 0);
		for (int z = 0;z<d();z++) {
			for (int y = 0;y<h();y++) {
				for (int x = 0;x<w();x++) {
					if (get(x, y, z).getColor()!=0) {
						int neighbors = 0;
						min = new Pointer(x, y, z);
					}
				}
			}
		}
		return min;
	}
	
	public Cubie get(int x, int y, int z) {
		return cubies[z][y][x];
	}

	public void set(int x, int y, int z, Cubie val) {
		cubies[z][y][x] = val;
	}

	public Cubie[][][] getLayout() {
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
	
	public void set(Pointer pointer, Cubie val) {
		cubies[pointer.getZ()][pointer.getY()][pointer.getX()] = val;
	}

	public Cubie get(Pointer pointer) {
		return cubies[pointer.getZ()][pointer.getY()][pointer.getX()];
	}

	public boolean inBounds(Pointer pointer) {
		return pointer.getX()>=0&&pointer.getY()>=0&&pointer.getZ()>=0&&pointer.getX()<w()&&pointer.getY()<h()&&pointer.getZ()<d();
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
