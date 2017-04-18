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

//	public static List<Layout> getAllPermutations(Layout layout) {
//		List<Layout> layouts = new ArrayList<>();
//		for (int o = 0;o<6;o++) {
//			layout.rotate((o==4)?1:(o==5)?2:0, (o>=1&&o<=4)?1:0, 0);
//			for (int r = 0;r<4;r++) {
//				layout.rotate(0, 0, 1);
//				layouts.add(layout.clone());
//			}
//		}
//		for (int i = layouts.size()-1;i>=0;i--) {
//			for (int j = 0;j<i;j++) {
//				if (layouts.get(i).equals(layouts.get(j))) {
//					layouts.remove(j);
//					break;
//				}
//			}
//		}
//		return layouts;
//	}
}
