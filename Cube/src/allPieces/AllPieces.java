package allPieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import data.AllPiecesData;
import generator.Move;
import main.Layout;
import main.NavigateableLayout;
import main.Pointer;

public class AllPieces implements AllPiecesData {

	public static void generate() {
		List<Layout> pieces = new ArrayList<>();
		List<Move> allMoves = new ArrayList<>(Arrays.asList(new Move(1, 0, 0), new Move(0, 1, 0), new Move(0, 0, 1), new Move(-1, 0, 0), new Move(0, -1, 0), new Move(0, 0, -1)));
		int size = SIZE*2;
		NavigateableLayout piece;
		Pointer pointer;
		Stack<Move> moves;
		int[] is = new int[size], limits = new int[size];
		for (int i = 0;i<limits.length;i++) limits[i] = 6;
		do {
			moves = new Stack<>();
			for (int k = 0;k<size;k++) {
				if (k>=2&&is[k]==is[k-1]&&is[k]==is[k-2]) continue;
				moves.push(allMoves.get(is[k]));
			}
			piece = new NavigateableLayout(BOUND, BOUND, BOUND);
			pointer = new Pointer(0,0,0);
			int count = 0;
			while (count<SIZE&&!moves.isEmpty()) {
				Move move = moves.pop();
				if (piece.inBounds(pointer.getMoved(move.getX(), move.getY(), move.getZ()))) {
					pointer = pointer.getMoved(move.getX(), move.getY(), move.getZ());
					if (piece.get(pointer)==0) {
						piece.set(pointer, 1);
						count++;
					}
				}
				else break;
			}
			if (!moves.isEmpty()||count!=SIZE) continue;
			pieces.add(piece);
//			System.out.println(piece);
		} while (!incArray(is, limits));
		for (int i = pieces.size()-1;i>=0;i--) {
			for (int j = pieces.size()-1;j>i;j--) {
				if (pieces.get(i).equals(pieces.get(j))) {
					pieces.remove(j);
				}
			}
		}
		System.out.println(pieces.size());
		System.out.println(pieces);

	}

	public static boolean incArray(int[] array, int[] limits) {//true if at max, inclusive-exlusive
		array[0]++;
		for (int i = 1;i<=array.length;i++) {
			if (array[i-1]==limits[i-1]) {
				if (i<array.length) {
					array[i-1] = 0;
					array[i]++;
				}
				else return true;
			}
		}
		return false;
	}
}
