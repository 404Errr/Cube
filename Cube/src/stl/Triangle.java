package stl;

public class Triangle {
	private Vertex[] vertexs;

	public Triangle(Vertex... vertexs) {
		this.vertexs = vertexs;
	}

	public Vertex[] getVertexs() {
		return vertexs;
	}
}
