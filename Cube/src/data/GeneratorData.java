package data;

public interface GeneratorData {
	boolean MAKE_PIECES = false, COMPACT = true;

	boolean SAVE = true;
	String PATH = "src/cubes";

//	blah
//	int[][][] START_WITH = Data.TEST_START;//TODO

	float PIECE_COUNT_RATIO = MainData.SIZE*2-1;
	int PIECE_SIZE_MIN_OFFSET = MainData.SIZE%3, PIECE_SIZE_MAX_OFFSET = MainData.SIZE%3;

	int PIECE_COUNT = (int) (MainData.SIZE*MainData.SIZE*MainData.SIZE/PIECE_COUNT_RATIO);
	int PIECE_SIZE_MIN = (int) (Math.floor((MainData.SIZE*MainData.SIZE*MainData.SIZE)/(float) PIECE_COUNT)-PIECE_SIZE_MIN_OFFSET);
	int PIECE_SIZE_MAX = (int) (Math.ceil((MainData.SIZE*MainData.SIZE*MainData.SIZE)/(float) PIECE_COUNT)+PIECE_SIZE_MAX_OFFSET);

	boolean CHECK_PRINT = false;
	boolean RUN_CHECKS = true;

	boolean CHECK_FLAT = true;
	boolean CHECK_TOO_MANY_ON_PLANE = true;
	int BORING_PLANE_COUNT = (int) (PIECE_SIZE_MAX-(Math.ceil((PIECE_SIZE_MAX-PIECE_SIZE_MIN)/2f)));
	boolean CHECK_DIAGONAL_COLLISION = true;
	boolean CHECK_SURROUNDED_COLLISION = false;
	boolean CHECK_2D_CLUSTERS = true;
	int MAX_2D_CLUSTERS = 1+(MainData.SIZE-3)*3;
	boolean CHECK_3D_CLUSTERS = false;
	int MAX_3D_CLUSTER_SIZE = 5;
	boolean CHECK_IDENTICAL = false;

}