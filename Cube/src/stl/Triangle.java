package stl;

public class Triangle {
	private Vertex[] vertexs;

	public Triangle(Vertex v0, Vertex v1, Vertex v2) {
		vertexs = new Vertex[3];
		vertexs[0] = v0;
		vertexs[1] = v1;
		vertexs[2] = v2;
	}

	public Vertex[] getVertexs() {
		return vertexs;
	}

	public boolean equals(Triangle other) {
		return vertexs[0].equals(other.getVertexs()[0])&&vertexs[1].equals(other.getVertexs()[1])&&vertexs[2].equals(other.getVertexs()[2]);
	}
}
