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
	}

	public void generateLayouts() {

	}

	public List<Layout> getLayouts() {
		return layouts;
	}

	public Layout getLayout() {
		return layout;
	}


}
