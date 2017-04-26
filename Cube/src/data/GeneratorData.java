package data;

public interface GeneratorData extends MainData {
	int PIECE_COUNT = 5;

	boolean MAKE_PIECES = true;
	boolean HIDE_SOLUTION = false;

	int BORING_PLANE_COUNT = 5;//5
	int BORING_3D_CLUSTER_COUNT = 6;//6
	int BORING_2D_CLUSTER_COUNT = 4;//4

	int PIECE_SIZE_MIN_OFFSET = 1, PIECE_SIZE_MAX_OFFSET = 0;
	int PIECE_SIZE_MIN = (int) Math.floor((SIZE*SIZE*SIZE)/(double) PIECE_COUNT)-PIECE_SIZE_MIN_OFFSET;
	int PIECE_SIZE_MAX = (int) Math.ceil((SIZE*SIZE*SIZE)/(double) PIECE_COUNT)+PIECE_SIZE_MAX_OFFSET;

	boolean DIRECTIONS = false;
	int U = -1, D = -2, L = -3, R = -4, F = -5, B = -6;
}