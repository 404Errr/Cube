package data;

public interface GeneratorData {
	boolean MAKE_PIECES = true, COMPACT = true;
	boolean HIDE_SOLUTION = false;

	boolean SAVE = true;
	String PATH = "src/generated";

	float PIECE_COUNT_RATIO = (Size.SIZE==3)?0.2f:0.15f;
	int PIECE_SIZE_MIN_OFFSET = (Size.SIZE==3)?0:1, PIECE_SIZE_MAX_OFFSET = (Size.SIZE==3)?0:1;

	int PIECE_COUNT = (int) (MainData.SIZE*MainData.SIZE*MainData.SIZE*PIECE_COUNT_RATIO);
	int PIECE_SIZE_MIN = (int) (Math.floor((MainData.SIZE*MainData.SIZE*MainData.SIZE)/(float) PIECE_COUNT)-PIECE_SIZE_MIN_OFFSET);
	int PIECE_SIZE_MAX = (int) (Math.ceil((MainData.SIZE*MainData.SIZE*MainData.SIZE)/(float) PIECE_COUNT)+PIECE_SIZE_MAX_OFFSET);


	int BORING_PLANE_COUNT = Math.max((int) (PIECE_SIZE_MAX*0.8f), PIECE_SIZE_MAX-1);
	int BORING_CLUSTER_3D_COUNT = 6;//6
	int BORING_CLUSTER_2D_COUNT = 4;//4

	boolean CHECK_PRINT = false;

//	boolean CHECK_FLAT = true;
//	boolean CHECK_TOO_MANY_ON_PLANE = true;
//	boolean CHECK_COLLISION = true;
//	boolean CHECK_2D_CLUSTERS = false;
//	boolean CHECK_3D_CLUSTERS = true;
//	boolean CHECK_IDENTICAL = true;

	boolean CHECK_FLAT = true;
	boolean CHECK_TOO_MANY_ON_PLANE = true;
	boolean CHECK_COLLISION = true;
	boolean CHECK_2D_CLUSTERS = true;
	boolean CHECK_3D_CLUSTERS = true;
	boolean CHECK_IDENTICAL = true;

}

class Size {
	static int SIZE = MainData.SIZE;
}