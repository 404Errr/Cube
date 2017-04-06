package solver;

import java.util.ArrayList;
import java.util.List;

import cube.Piece;
import data.ToSolve;

public class Solver {
	public static void solve(int[][][][] pieceLayouts) {
		List<Piece> pieces = new ArrayList<>();
		for (int p = 0;p<pieceLayouts.length;p++) {
			pieces.add(new Piece(pieceLayouts[p]));
		}
		new Piece(ToSolve.SIDES);
	}
}
