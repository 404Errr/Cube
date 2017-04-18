package data;

public interface GeneratorData extends MainData {
	int PIECE_COUNT = 5;

	boolean MAKE_PIECES = true;
	boolean HIDE_SOLUTION = true;
	boolean PRINT_LOG_STUFF = false;

	
	int PIECE_SIZE_MIN_OFFSET = 1, PIECE_SIZE_MAX_OFFSET = 0;

	int BORING_PLANE_COUNT = 5;
	int BORING_3D_CLUSTER_COUNT = 5;
	int BORING_2D_CLUSTER_COUNT = 4;

	int PIECE_SIZE_MIN = (int) Math.floor((SIZE*SIZE*SIZE)/(double) PIECE_COUNT)-PIECE_SIZE_MIN_OFFSET;
	int PIECE_SIZE_MAX = (int) Math.ceil((SIZE*SIZE*SIZE)/(double) PIECE_COUNT)+PIECE_SIZE_MAX_OFFSET;
}