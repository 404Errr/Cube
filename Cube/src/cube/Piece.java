package cube;

import data.MainData;

public class Piece implements MainData {
	private final Cubie[][][] layout;
	private int[] location;
	
	public Piece(final int[][][] layout) {
		this.layout = new Cubie[layout.length][layout[0].length][layout[0][0].length];
		for (int z = 0;z<layout.length;z++) {
			for (int y = 0;y<layout[0].length;y++) {
				for (int x = 0;x<layout[0][0].length;x++) {
					this.layout[z][y][x] = new Cubie(layout[z][y][x]);
				}
			}
		}
	}

	public void setLocation(int z, int y, int x) {
		location = new int[] {z, y, x};
	}
	
	public int[] getLocation() {
		return location;
	}
	
	public Cubie[][][] getLayout() {
		return layout;
	}
}
