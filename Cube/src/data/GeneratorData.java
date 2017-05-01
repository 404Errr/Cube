package data;

public interface GeneratorData {
	boolean MAKE_PIECES = false, COMPACT = true;
	boolean PRINT_PATH = false;

	boolean SAVE = true;
	String PATH = "src/cubes";

	float PIECE_COUNT_RATIO = ((MainData.WIDTH+MainData.HEIGHT+MainData.DEPTH)/3f)*2-1;
	int PIECE_SIZE_MIN_OFFSET = (int) ((MainData.WIDTH+MainData.HEIGHT+MainData.DEPTH)/3f%3), PIECE_SIZE_MAX_OFFSET = (int) ((MainData.WIDTH+MainData.HEIGHT+MainData.DEPTH)/3f%3);

	int PIECE_COUNT = (int) (MainData.WIDTH*MainData.HEIGHT*MainData.DEPTH/PIECE_COUNT_RATIO);
	int PIECE_SIZE_MIN = (int) (Math.floor((MainData.WIDTH*MainData.HEIGHT*MainData.DEPTH)/(float) PIECE_COUNT)-PIECE_SIZE_MIN_OFFSET);
	int PIECE_SIZE_MAX = (int) (Math.ceil((MainData.WIDTH*MainData.HEIGHT*MainData.DEPTH)/(float) PIECE_COUNT)+PIECE_SIZE_MAX_OFFSET);

	boolean CHECK_PRINT = false;
	boolean RUN_CHECKS = true;

	boolean CHECK_FLAT = true;
	boolean CHECK_TOO_MANY_ON_PLANE = true;
	int BORING_PLANE_COUNT = (int) (PIECE_SIZE_MAX-(Math.ceil((PIECE_SIZE_MAX-PIECE_SIZE_MIN)/2f)));
	boolean CHECK_DIAGONAL_COLLISION = true;
	boolean CHECK_SURROUNDED_COLLISION = true;
	boolean CHECK_2D_CLUSTERS = true;
	int MAX_2D_CLUSTERS = 1;//(int) ((((MainData.WIDTH+MainData.HEIGHT+MainData.DEPTH)/3f)-3)*3)+1;
	boolean CHECK_3D_CLUSTERS = true;
	int MAX_3D_CLUSTER_SIZE = 5;
	boolean CHECK_IDENTICAL = false;

}