package generator;

import data.GeneratorData;
import main.Layout;
import main.Pointer;

public class NavigateableLayout extends Layout implements GeneratorData {
	public NavigateableLayout(int size) {
		super(size);
	}

	public NavigateableLayout(int[][][] layout) {
		super(layout);
	}
	
	public void set(Pointer pointer, int val) {
		cubies[pointer.getZ()][pointer.getY()][pointer.getX()] = val;
	}

	public int get(Pointer pointer) {
		return cubies[pointer.getZ()][pointer.getY()][pointer.getX()];
	}

	public boolean isOccupied(Pointer pointer) {
		return cubies[pointer.getZ()][pointer.getY()][pointer.getX()]!=0;
	}

	public boolean inBounds(Pointer pointer) {
		return pointer.getX()>=0&&pointer.getY()>=0&&pointer.getZ()>=0&&pointer.getX()<w()&&pointer.getY()<h()&&pointer.getZ()<d();
	}

}

