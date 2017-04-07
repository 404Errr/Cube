package generator;

public class Generator {
	private static final List<Integer> moveOrder = new ArrayList<>(0, 1, 2, 3, 4, 5);
	private static Cube cube = new Cube();
	private static Pointer currentPointer = new Pointer(0, 0, 0);
	
	public static void main(String[] args) {
		
	}

	public static void generate() {
		
		int color = 1;
		int cubieCount = 0;
		while (!cube.full()) {
			movePointer();
		}
	}

	public void movePointer() {
		Collections.shuffle(moveOrder);
		int i = 0;
		boolean failed;
						
		while (i<moveOrder.size()) {
			Pointer tempPointer = currentPointer.getOffset(moveOrder(i));
			//if () {}
			i++;
		}
	}

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
					
class Cube implements GeneratorData {
	private int[][][] cubies;
	private boolean[][][] added;
	int addedCount;
	
	public Cube() {
		reset();
	}

	public void reset() {
		cubies = new int[SIZE][SIZE][SIZE];
		added = new boolean[SIZE][SIZE][SIZE];
		addedCount = 0;
	}

	public void add(int x, int y, int z, int val) {
		cubies[z][y][x] = val;
		added[z][y][x] = true;
	}
	
	public int get(int x, int y, int z) {
		return cubies[z][y][x];
	}

	public boolean added(int x, int y, int z) {
		return added[z][y][x];
	}
	
	public boolean full() {
		return addedCount==d()*h()*w();
	}
	
	public int d() {
		return cubies.length;
	}
	
	public int h() {
		return cubies[0].length;
	}
	
	public int w() {
		return cubies[0][0].length;
	}
}





interface GeneratorData {
	int SIZE = 3;
	
	int PX = 0, PY = 1, PZ = 2, NX = 3, NY = 4, NZ = 5;
}














