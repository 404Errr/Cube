package data;

public interface GeneratorData {
	boolean MAKE_PIECES = true, COMPACT = true;
	boolean HIDE_SOLUTION = false;

	boolean SAVE = true;
	String PATH = "src/cubes";

//	float PIECE_COUNT_RATIO = 0.2f;
//	int PIECE_SIZE_MIN_OFFSET = 0, PIECE_SIZE_MAX_OFFSET = 0;
	float PIECE_COUNT_RATIO = 0.15f;
	int PIECE_SIZE_MIN_OFFSET = 1, PIECE_SIZE_MAX_OFFSET = 1;

	int PIECE_COUNT = (int) (MainData.SIZE*MainData.SIZE*MainData.SIZE*PIECE_COUNT_RATIO);
	int PIECE_SIZE_MIN = (int) (Math.floor((MainData.SIZE*MainData.SIZE*MainData.SIZE)/(float) PIECE_COUNT)-PIECE_SIZE_MIN_OFFSET);
	int PIECE_SIZE_MAX = (int) (Math.ceil((MainData.SIZE*MainData.SIZE*MainData.SIZE)/(float) PIECE_COUNT)+PIECE_SIZE_MAX_OFFSET);

	int BORING_PLANE_COUNT = Math.max((int) (PIECE_SIZE_MAX*0.8f), PIECE_SIZE_MAX-1);
	int BORING_CLUSTER_3D_COUNT = 6;//6

	boolean CHECK_PRINT = false;

	boolean CHECK_FLAT = true;
	boolean CHECK_TOO_MANY_ON_PLANE = true;
	boolean CHECK_COLLISION = true;
	int CHECK_2D_CLUSTERS = 7;
	boolean CHECK_3D_CLUSTERS = true;
	boolean CHECK_IDENTICAL = true;

}

class Size {
	static int SIZE = MainData.SIZE;
}