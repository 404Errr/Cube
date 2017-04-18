package data;

public interface SolverData extends ToSolve, MainData {
	int[][][][] TO_SOLVE = PIECES1;
//	int[][][][] TO_SOLVE = SINGLES;
//	int[][][][] TO_SOLVE = PIECES3;
//	int[][][][] TO_SOLVE = TEST;
	
//	boolean FIND_ALL = true;
	boolean FIND_ALL = false;
	
	float MIDDLE = 1f;
	float CENTER = 0.25f;
	float EDGE = 0.1f;
	float CORNER = 0.05f;
}
