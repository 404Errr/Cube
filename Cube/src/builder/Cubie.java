package builder;

import java.util.List;

public class Cubie {
	private final int color;
	private List<Cubie> nexts;
	
	public Cubie(int color, List<Cubie> nexts) {
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	
	public void addNext(Cubie cubie) {
		nexts.add(cubie);
	}
	
	public List<Cubie> getNexts() {
		if (nexts.isEmpty()) return null;
		return nexts;
	}
	
	
}
