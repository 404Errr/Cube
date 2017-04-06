package cube;

import java.util.ArrayList;
import java.util.List;

import data.MainData;

public class Piece implements MainData {
	private final Layout layout;
	private List<Layout> layouts;

	public Piece(int[][][] rawLayout) {
		layout = new Layout(rawLayout);
		layouts = new ArrayList<>();
		generateLayouts();

		System.out.println(layouts.size()+"\t\t-----");
		for (int i = 0;i<layouts.size();i++) {
			System.out.println(layouts.get(i));
		}
	}

	public void generateLayouts() {
		for (int o = 0;o<6;o++) {
			if (o>0&&o<5) layout.rotate(0, 1, 0);//1-4
			if (o>=4) layout.rotate(1, 0, 0);//4-5
			if (o==5) layout.rotate(1, 0, 0);//5
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
	}

	public List<Layout> getLayouts() {
		return layouts;
	}

	public Layout getLayout() {
		return layout;
	}


}
