package generator;

public interface GeneratorData {
	int X = 2, Y = 1, Z = 0;

	int SIZE = 3;

	int PIECE_COUNT = 5;
	int PIECE_SIZE_MIN_OFFSET = 1, PIECE_SIZE_MAX_OFFSET = 0;

	int BORING_PLANE_COUNT = 5;
	int BORING_3D_CLUSTER_COUNT = 6;
	int BORING_2D_CLUSTER_COUNT = 4;

	int PIECE_SIZE_MIN = (int) Math.floor((SIZE*SIZE*SIZE)/(double) PIECE_COUNT)-PIECE_SIZE_MIN_OFFSET;
	int PIECE_SIZE_MAX = (int) Math.ceil((SIZE*SIZE*SIZE)/(double) PIECE_COUNT)+PIECE_SIZE_MAX_OFFSET;
}