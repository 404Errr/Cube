package stl;

public class Vertex {
	private float x, y, z;

	public Vertex(int x) {}//FIXME

	public Vertex(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public boolean equals(Vertex other) {
		return getX()==other.getX()&&getY()==other.getY()&&getZ()==other.getZ();
	}
}