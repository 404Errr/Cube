package builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import data.BuilderData;
import main.Layout;
import main.Pointer;

public class Builder implements BuilderData {
	public static void build() {
		int[][][] cube = TO_BUILD;
		List<int[][][]> pieceLayouts = new ArrayList<>();
		for (int i = 0;i<PIECE_COUNT;i++) {
			pieceLayouts.add(new int[SIZE][SIZE][SIZE]);
			for (int z = 0;z<SIZE;z++) {
				for (int y = 0;y<SIZE;y++) {
					for (int x = 0;x<SIZE;x++) {
						int color = i+1;
						if (cube[z][y][x]==color) pieceLayouts.get(i)[z][y][x] = cube[z][y][x];
					}
				}
			}
		}
		List<BuildOrder> pieces = new ArrayList<>();
		for (int p = 0;p<pieceLayouts.size();p++) {
			pieces.add(new BuildOrder(new Layout(pieceLayouts.get(p))));
		}		
		
		for (int p = 0;p<pieces.size();p++) {
			BuildOrder piece = pieces.get(p);
			Pointer current = null;
			for (int z = 0;z<piece.d();z++) {
				for (int y = 0;y<piece.h();y++) {
					for (int x = 0;x<piece.w();x++) {
						if (current==null&&piece.get(x, y, z).getColor()!=0) {
							current = new Pointer(x, y, z);
						}
					}
				}
			}
			boolean done = false;
			while (!done) {
				System.out.println(current);
				Stack<int[]> moves = new Stack<>();
				moves.addAll(Arrays.asList(new int[] {1, 0, 0}, new int[] {0, 1, 0}, new int[] {0, 0, 1}, new int[] {-1, 0, 0}, new int[] {0, -1, 0}, new int[] {0, 0, -1}));
				Collections.shuffle(moves);
				while (!moves.isEmpty()) {
					int[] move = moves.pop();
					Pointer temp = current.getMoved(move[0], move[1], move[2]);
					if (piece.inBounds(temp)&&piece.get(temp).getColor()!=0) {
						if (piece.get(current).makesCollision(piece.get(temp))) break;//FIXME
						piece.get(current).setNext(piece.get(temp));
						current = temp;
						break;
					}
				}
				
				if (piece.isComplete()) {
					done = true;
				}
			}
			System.out.println(piece.layoutToString());
			System.out.println(piece.directionToString());
		}
		for (int p = 0;p<pieces.size();p++) {
			System.out.println(pieces.get(p).directionToString());
		}
	}
}
