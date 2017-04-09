package solver;

import java.util.ArrayList;
import java.util.List;

import data.MainData;
import main.Layout;

public class Piece implements MainData {

	public static List<Layout> getAllPermutations(Layout layout) {
		List<Layout> layouts = new ArrayList<>();
		for (int o = 0;o<6;o++) {
			layout.rotate((o==4)?1:(o==5)?2:0, (o>=1&&o<=4)?1:0, 0);
			for (int r = 0;r<4;r++) {
				layout.rotate(0, 0, 1);
				layouts.add(layout.clone());
			}
		}
		for (int i = layouts.size()-1;i>=0;i--) {
			for (int j = 0;j<i;j++) {
				if (layouts.get(i).equals(layouts.get(j))) {
					layouts.remove(j);
					break;
				}
			}
		}
		return layouts;
	}
}