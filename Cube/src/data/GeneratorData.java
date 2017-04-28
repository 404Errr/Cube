package data;

public interface GeneratorData {
	boolean MAKE_PIECES = true, COMPACT = true;
	boolean HIDE_SOLUTION = false;

	boolean SAVE = true;
	String PATH = "src/cubes";

	float PIECE_COUNT_RATIO = 5;//0.2f;
	int PIECE_SIZE_MIN_OFFSET = MainData.SIZE-3, PIECE_SIZE_MAX_OFFSET = MainData.SIZE-3;

	int PIECE_COUNT = (int) (MainData.SIZE*MainData.SIZE*MainData.SIZE/PIECE_COUNT_RATIO);
	int PIECE_SIZE_MIN = (int) (Math.floor((MainData.SIZE*MainData.SIZE*MainData.SIZE)/(float) PIECE_COUNT)-PIECE_SIZE_MIN_OFFSET);
	int PIECE_SIZE_MAX = (int) (Math.ceil((MainData.SIZE*MainData.SIZE*MainData.SIZE)/(float) PIECE_COUNT)+PIECE_SIZE_MAX_OFFSET);

	int BORING_PLANE_COUNT = (int) (PIECE_SIZE_MAX-(Math.ceil((PIECE_SIZE_MAX-PIECE_SIZE_MIN)/2f)));
	int BORING_CLUSTER_3D_COUNT = 6;//6

	boolean CHECK_PRINT = false;

	boolean RUN_CHECKS = true;

	boolean CHECK_FLAT = true;
	boolean CHECK_TOO_MANY_ON_PLANE = true;
	boolean CHECK_COLLISION = true;
	int MAX_2D_CLUSTERS = (MainData.SIZE-3)*3;
	boolean CHECK_3D_CLUSTERS = false;
	boolean CHECK_IDENTICAL = true;

}