package generator;

public class Generator {
	





}

class Pointer implements GeneratorData {
	
	
	private int x, y, z;
	
	public Pointer(int x, int y, int z) {
		set(x, y, z);
	}
	
	public Pointer getOffset(int direction) {
		switch (direction) {
		case PX:
			return new Pointer(x+1, y, z);
		case PY:
			return new Pointer(x, y+1, z);
		case PZ:
			return new Pointer(x, y, z+1);
		case NX:
			return new Pointer(x-1, y, z);
		case NY:
			return new Pointer(x, y-1, z);
		case NZ:
			return new Pointer(x, y, z-1);
		}
	}
	
	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
					
class Cube {
	
	
	private int[][][] cubies;
	private boolean[][][] added;
	
	public Cube() {
		
	}





}





interface GeneratorData {
	int SIZE = 3;
	
	int PX = 0, PY = 1, PZ = 2, NX = 3, NY = 4, NZ = 5;
}














