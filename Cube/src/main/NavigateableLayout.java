package main;

import data.GeneratorData;

public class NavigateableLayout extends Layout implements GeneratorData {
	public NavigateableLayout(int w, int h, int d) {
		super(w, h, d);
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

