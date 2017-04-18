package solver;

import data.MainData;
import main.Layout;

public class OriginalPiece extends Layout implements MainData, Comparable<OriginalPiece> {
	public OriginalPiece(int size) {
		super(size);
	}

	public OriginalPiece(int[][][] layout) {
		super(layout);
	}

	public int getSize() {
		int size = 0;
		for (int z = 0;z<d();z++) {
			for (int y = 0;y<h();y++) {
				for (int x = 0;x<w();x++) {
					if (get(x, y, z)!=0) size++;
				}
			}
		}
		return size;
	}

	public int compareTo(OriginalPiece that) {
		int thisS = this.getSize(), thatS = that.getSize();
		if (thisS>thatS) return -1;
		if (thisS<thatS) return 1;
		return 0;
	}
}
